package it.univaq.esc.model;

import java.util.ArrayList;

public class RegistroPrenotazioni {

	private ArrayList<Prenotazione> prenotazioni;
	private static RegistroPrenotazioni registroPrenotazioniInstance;

	private RegistroPrenotazioni(){
		this.prenotazioni = new ArrayList<Prenotazione>();
	}
	
	public static RegistroPrenotazioni getInstance() {
		if (registroPrenotazioniInstance == null){
			registroPrenotazioniInstance = new RegistroPrenotazioni();
		}

		return registroPrenotazioniInstance;
	}

	public ArrayList<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public Prenotazione getPrenotazione(int IDPrenotazione) {
		return this.prenotazioni.get(IDPrenotazione);
	}
	
	public void addPrenotazione(Prenotazione prenotazione) {
		this.prenotazioni.add(prenotazione);
	}
		
	public ArrayList<Prenotazione> getPrenotazioniAperte(Sportivo sportivoPrenotante /*, parametro???*/){
			return null;
	}
	
	public int getLastIDPrenotazione() {
		if(prenotazioni.size() != 0){
			return this.prenotazioni.get(prenotazioni.size()-1).getIDPrenotazione();
		}
		else {
			return 0 ;
		}
		
	}
	
}
