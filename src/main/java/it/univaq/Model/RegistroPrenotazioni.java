package it.univaq.Model;

import java.util.ArrayList;

public class RegistroPrenotazioni {

	private ArrayList<Prenotazione> prenotazioni;

	public RegistroPrenotazioni() {
		super();
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
	
	//public RegistroPrenotazione getIstance(){};
	
	public ArrayList<Prenotazione> getPrenotazioniAperte(Sportivo sportivoPrenotante /*, parametro???*/){
			return null;
	}
	
	public int getLastIDPrenotazione() {
		return this.prenotazioni.lastIndexOf(prenotazioni);
	}
	
}
