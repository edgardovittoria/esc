package it.univaq.Utility;

import it.univaq.Model.Prenotazione;
import it.univaq.Model.Sportivo;

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
}
