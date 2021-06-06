package it.univaq.esc.model.utenti;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.repository.SquadraRepository;
import lombok.Getter;
import lombok.Setter;
@Component
@Singleton
@Getter @Setter 
public class RegistroSquadre {
	
	private SquadraRepository squadraRepository;

	private List<Squadra> listaSquadre = new ArrayList<Squadra>();
	
	public RegistroSquadre(SquadraRepository squadraRepository) {
		setSquadraRepository(squadraRepository);
		popola();
	}
	
	private void popola() {
		setListaSquadre(getSquadraRepository().findAll());
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
}
