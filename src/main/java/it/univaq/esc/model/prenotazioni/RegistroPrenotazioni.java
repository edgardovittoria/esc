package it.univaq.esc.model.prenotazioni;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import groovy.lang.Singleton;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.repository.PrenotazioneRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


@Component
@Singleton
@Getter
@Setter
public class RegistroPrenotazioni {

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private PrenotazioneRepository prenotazioneRepository;
	@Setter(value = AccessLevel.PRIVATE)
	private List<Prenotazione> prenotazioniRegistrate = new ArrayList<Prenotazione>();

	public RegistroPrenotazioni(PrenotazioneRepository prenotazioneRepository) {
		this.setPrenotazioneRepository(prenotazioneRepository);
		popola();
	}

	
	private void popola() {
		setPrenotazioniRegistrate(prenotazioneRepository.findAll());
	}

	public void aggiungiPrenotazione(Prenotazione prenotazioneDaAggiungere) {
		getPrenotazioniRegistrate().add(prenotazioneDaAggiungere);
		this.getPrenotazioneRepository().save(prenotazioneDaAggiungere);

	}

	public List<Prenotazione> getPrenotazioniByEmailSportivo(String email) {
		List<Prenotazione> prenotazioniSportivo = new ArrayList<Prenotazione>();
		for (Prenotazione prenotazione : this.getPrenotazioniRegistrate()) {
			if (prenotazione.getSportivoPrenotante().isSuaQuesta(email)) {
				prenotazioniSportivo.add(prenotazione);
			}
		}
		return prenotazioniSportivo;
	}

	public void cancellaPrenotazione(Prenotazione prenotazioneDaCancellare) {
		// appuntamentoRepository.deleteAll(prenotazioneDaCancellare.getListaAppuntamenti());
		getPrenotazioniRegistrate().remove(prenotazioneDaCancellare);
		getPrenotazioneRepository().delete(prenotazioneDaCancellare);

	}

	public Integer getLastIdPrenotazione() {
		Integer lastID = 0;
		if (getPrenotazioniRegistrate().isEmpty()) {
			return lastID;
		} else {
			for (Prenotazione prenotazione : this.getPrenotazioniRegistrate()) {
				if (prenotazione.getIdPrenotazione() > lastID) {
					lastID = prenotazione.getIdPrenotazione();
				}

			}
			return lastID;
		}
	}

	public List<Prenotazione> filtraPrenotazioniPerTipo(List<Prenotazione> listaPrenotazioniDaFiltrare,
			String tipoPrenotazione) {

		List<Prenotazione> prenotazioniFiltrate = new ArrayList<Prenotazione>();
		for (Prenotazione prenotazione : listaPrenotazioniDaFiltrare) {
			if (prenotazione.getTipoPrenotazione().equals(tipoPrenotazione)) {
				prenotazioniFiltrate.add(prenotazione);
			}
		}
		return prenotazioniFiltrate;
	}

	public List<Prenotazione> escludiPrenotazioniPerTipo(List<Prenotazione> listaPrenotazioniDaFiltrare,
			String tipoPrenotazione) {

		List<Prenotazione> prenotazioniFiltrate = new ArrayList<Prenotazione>();
		for (Prenotazione prenotazione : listaPrenotazioniDaFiltrare) {
			if (!prenotazione.getTipoPrenotazione().equals(tipoPrenotazione)) {
				prenotazioniFiltrate.add(prenotazione);
			}
		}
		return prenotazioniFiltrate;
	}

	public Prenotazione getPrenotazioneById(Integer idPrenotazione) {
		for (Prenotazione prenotazione : this.getPrenotazioniRegistrate()) {
			if (prenotazione.getIdPrenotazione() == idPrenotazione) {
				return prenotazione;
			}
		}
		return null;
	}
	
	public List<Appuntamento> getAppuntamentiAssociatiAdUna(List<Prenotazione> listaDiPrenotazioni){
		List<Appuntamento> listaAppuntamenti = new ArrayList<Appuntamento>();
		listaDiPrenotazioni.forEach((prenotazione) -> listaAppuntamenti.addAll(prenotazione.getListaAppuntamenti()));
		
		return listaAppuntamenti;
	}
	
	
	public Prenotazione trovaPrenotazioneAssociataA(Appuntamento appuntamento) {
		for(Prenotazione prenotazione : getPrenotazioniRegistrate()) {
			if(prenotazione.isAssociataA(appuntamento)) {
				return prenotazione;
			}
		}
		return null;
	}

}