package it.univaq.Model;

import java.util.ArrayList;

public abstract class Sport {

	private String sportDescription;
	private ArrayList<Impianto> Impianti; 
	private ArrayList<Istruttore> Istruttori; 
	


	/**
	 * Restituisce la lista degli impianti associati allo sport.
	 */
	public ArrayList<Impianto> getImpianti(){
		return this.Impianti;
	}

	/**
	 * Restituisce la lista degli istruttori associati allo sport.
	 */
	public ArrayList<Impianto> getIstruttori() {
		return this.Istruttori;
	}

	/**
	 * Associa un impianto allo sport.
	 */
	public void addImpianto( Impianto impianto) {
		this.Impianti.add(impianto);
	}

	/**
	 * Associa un istruttore allo sport.
	 */
	public void addIstruttore( Istruttore istruttore) {
		this.Istruttori.add(istruttore);
	}


	/**
	 * Rimuove un impianto tra quelli associati allo sport.
	 */
	public bool removeImpianto(Impianto impianto) {
		if (this.Impianti.contains(impianto)){
			this.Impianti.remove(impianto);
			return true;	
		}
		return false;
	}

	/**
	 * Rimuove un istruttore tra quelli associati allo sport.
	 */
	public bool removeIstruttore(Istruttore istruttore) {
		if (this.Istruttori.contains(istruttore)){
			this.Istruttori.remove(istruttore);
			return true;	
		}
		return false;
	}

	/**
	 * Restituice il numero di impianti associati allo sport.
	 */
	public int numberOfImpianti(){
		return this.Impianti.size();
	}


	/**
	 * Restituice il numero di istruttori associati allo sport.
	 */
	public int numberOfIstruttori(){
		return this.Istruttori.size();
	}
	
}
