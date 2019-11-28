package it.univaq.Model;

import java.awt.List;
import java.util.ArrayList;
import java.util.Calendar;

import org.omg.CORBA.PRIVATE_MEMBER;


public class Prenotazione {
	
	private int IDPrenotazione;
	private Boolean confermata;
	private enum modalita{
		STANDARD, PENDING
	}
	private int numParteciapnti;
	private int numPostiLiberi;
	private int IDSportivoPrenotante;
	
	private CostoPrenotazione costoPrenotazione;
	private IPrenotabile servizioPrenotato;
	private Calendario calendario;
	private ArrayList<Sportivo> Partecipanti;
	//costruttore
	public Prenotazione(int LastIDPrenotazione, Sportivo sportivoPrenotante) {
		super();
		this.IDPrenotazione = LastIDPrenotazione;
		this.IDSportivoPrenotante = IDSportivoPrenotante;
	}
	public int getNumParteciapnti() {
		return numParteciapnti;
	}
	public int getNumPostiLiberi() {
		return numPostiLiberi;
	}
	public ArrayList<Sportivo> getPartecipanti(){
		return this.Partecipanti;
	}
	public void aggiornaNumPostiLiberi() {
		this.numPostiLiberi = this.getNumPostiLiberi() - 1;
	}
	public void aggiornaNumPartecipanti() {
		this.numParteciapnti = this.getNumParteciapnti() + 1;
	}
	public void generaQuotaPartecipazione() {
		
	}
	public boolean confermaPrenotazione(ArrayList<Object> parametri) {
		return false;
	}
	
	
}

