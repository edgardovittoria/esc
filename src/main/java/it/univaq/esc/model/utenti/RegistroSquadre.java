package it.univaq.esc.model.utenti;

import java.sql.SQLInvalidAuthorizationSpecException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.repository.SquadraRepository;
import lombok.Getter;
import lombok.Setter;
@Component
@Singleton
@Getter @Setter 
public class RegistroSquadre {
	
	private SquadraRepository squadraRepository;
	
	private RegistroAppuntamenti registroAppuntamenti;

	private List<Squadra> listaSquadre = new ArrayList<Squadra>();
	
	public RegistroSquadre(SquadraRepository squadraRepository, RegistroAppuntamenti registroAppuntamenti) {
		setSquadraRepository(squadraRepository);
		setRegistroAppuntamenti(registroAppuntamenti);
		popola();
	}
	
	private void popola() {
		setListaSquadre(getSquadraRepository().findAll());
		for(Squadra squadra : getListaSquadre()) {
			List<Appuntamento> appuntamentiSquadra = getRegistroAppuntamenti().getAppuntamentiPerSquadraPartecipante(squadra);
			Calendario calendarioSquadra = new Calendario();
			calendarioSquadra.setListaAppuntamenti(appuntamentiSquadra);
			squadra.setCalendarioSquadra(calendarioSquadra);
		}
	}
	
	public void aggiungiSquadra(Squadra nuovaSquadra) {
		getListaSquadre().add(nuovaSquadra);
		getSquadraRepository().save(nuovaSquadra);
	}
	
	public void rimuoviSquadra(Squadra squadraDaRimuovere) {
		getListaSquadre().remove(squadraDaRimuovere);
		getSquadraRepository().delete(squadraDaRimuovere);
	}
	
	public boolean isSquadraEnabled(Squadra squadraDaVerificare) {
		if(squadraDaVerificare.getNumeroMinimoMembri() <= squadraDaVerificare.getMembri().size()) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Restituisce la lista delle squadre di cui un utente è membro.
	 * @param membro utente di cui riciavare le squadre associate.
	 * @return lista di squadre di cui l'utente passato come parametro è membro.
	 */
	public List<Squadra> getSquadrePerMembro(UtentePolisportivaAbstract membro){
		List<Squadra> listaSquadreDiCuiMembro = new ArrayList<Squadra>();
		for(Squadra squadra : getListaSquadre()) {
			if(squadra.isMembro(membro)) {
				listaSquadreDiCuiMembro.add(squadra);
			}
		}
		return listaSquadreDiCuiMembro;
	}
	
	private List<Squadra> filtraSquadreLiberePerCalendario(List<Squadra> listaSquadreDaFiltrare, Calendario calendarioDaConfrontare){
		List<Squadra> listaFiltrata = new ArrayList<Squadra>();
		for(Squadra squadra : listaSquadreDaFiltrare) {
			if(!squadra.getCalendarioSquadra().sovrapponeA(calendarioDaConfrontare)) {
				listaFiltrata.add(squadra);
			}
		}
		return listaFiltrata;
		
	}
	
	private List<Squadra> filtraSquadreLiberePerOrarioAppuntamento(List<Squadra> listaDaFiltrare, LocalDateTime orarioInizio, LocalDateTime orarioFine){
		List<Squadra> listaFiltrata = new  ArrayList<Squadra>();
		for(Squadra squadra : listaDaFiltrare) {
			if(!squadra.getCalendarioSquadra().sovrapponeA(orarioInizio, orarioFine)) {
				listaFiltrata.add(squadra);
			}
		}
		return listaFiltrata;
	}
	
	/**
	 * Restituisce le squadre libere sulla base di un certo calendario.
	 * @param calendarioDaConfrontare calendario da confrontare con quello delle squadre
	 * @return lista di squadre libere nel calendario passato come parametro.
	 */
	public List<Squadra> getListaSquadreLiberePerCalendario(Calendario calendarioDaConfrontare){
		return filtraSquadreLiberePerCalendario(getListaSquadre(), calendarioDaConfrontare);
	}
	
	public List<Squadra> getListaSquadreLiberePerOrarioAppuntamento(LocalDateTime orarioInizio, LocalDateTime orarioFine){
		return filtraSquadreLiberePerOrarioAppuntamento(getListaSquadre(), orarioInizio, orarioFine);
	}
	
}
