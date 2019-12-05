package it.univaq.utility;

import it.univaq.esc.model.IPrenotabile;
import it.univaq.esc.model.Lezione;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.Sportivo;

public class SimpleFactory {

	private static SimpleFactory factoryInstance;
	
	private SimpleFactory() {}
	
	public static SimpleFactory getInstance() {
		if(factoryInstance == null) {
			factoryInstance = new SimpleFactory();
		}
		
		return factoryInstance;
	}
	
	public Prenotazione getNuovaPrenotazione(int lastIDPrenotazione, Sportivo sportivoPrenotante) {
		Prenotazione prenotazione = new Prenotazione(lastIDPrenotazione, sportivoPrenotante);
		return prenotazione;
	}
	
	public IPrenotabile getIPrenotabile(String etich) {
		IPrenotabile servizioPrenotato = null;
		switch (etich) {
		case "Lezione":
			IPrenotabile lezione = new Lezione();
			servizioPrenotato = lezione;
			break;
			
		default:
			break;
		}
		return servizioPrenotato;
	}
	
}
