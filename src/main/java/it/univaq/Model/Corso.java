package it.univaq.Model;

import java.util.ArrayList;

import org.javamoney.moneta.Money;

public class Corso implements IPrenotabile{
	
	private int IDCorso;
	private int numMaxPartecipanti;
	private int numMinPartecipanti;
	private Money costoCorso;
	private ArrayList<Lezione> lezioni;
	private ArrayList<Istruttore> istruttori;
	
	
	
	public Corso() {
		super();
	}

	

	public int getNumMaxPartecipanti() {
		return numMaxPartecipanti;
	}



	public void setNumMaxPartecipanti(int numMaxPartecipanti) {
		this.numMaxPartecipanti = numMaxPartecipanti;
	}



	public int getNumMinPartecipanti() {
		return numMinPartecipanti;
	}



	public void setNumMinPartecipanti(int numMinPartecipanti) {
		this.numMinPartecipanti = numMinPartecipanti;
	}



	public Money getCostoCorso() {
		return costoCorso;
	}



	public void setCostoCorso(Money costoCorso) {
		this.costoCorso = costoCorso;
	}



	public ArrayList<Lezione> getLezioni() {
		return lezioni;
	}



	public void setLezioni(ArrayList<Lezione> lezioni) {
		this.lezioni = lezioni;
	}



	public ArrayList<Istruttore> getIstruttori() {
		return istruttori;
	}



	public void setIstruttori(ArrayList<Istruttore> istruttori) {
		this.istruttori = istruttori;
	}



	@Override
	public boolean confermaPrenotazione(ArrayList<Object> parametri) {
		// TODO Auto-generated method stub
		return false;
	}

}
