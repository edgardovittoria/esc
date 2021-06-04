package it.univaq.esc.model.prenotazioni;



import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.costi.ModalitaPrenotazione;
@Component
@Singleton
public class FactoryAppuntamenti {

	
	
	public FactoryAppuntamenti() {
		
		
	}
	
	
	public Appuntamento getAppuntamento(String modalitaPrenotazione) {
		if(modalitaPrenotazione.equals(ModalitaPrenotazione.SINGOLO_UTENTE.toString())) {
			return new AppuntamentoSingoliPartecipanti();
		}
		else if(modalitaPrenotazione.equals(ModalitaPrenotazione.SQUADRA.toString())) {
			return null;
		}
		return null;
	}
}
