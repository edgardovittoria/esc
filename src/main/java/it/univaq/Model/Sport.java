package it.univaq.Model;

import java.util.ArrayList;

public abstract class Sport {

	protected int IDSport;
	protected String sportDescription;
	protected ArrayList<Impianto> Impianti; 
	protected ArrayList<Istruttore> Istruttori; 
	

	/**
	 * Restituisce la descrizione dello sport.
	 */
	public String getDescription(){
		return this.sportDescription;
	}


	/**
	 * Inserisce la descrizione dello sport.
	 */
	public void setDescription(String description){
		this.sportDescription = description;
	}

	/**
	 * Restituisce l'ID dello sport.
	 */
	public int getIDSport(){
		return this.IDSport;
	}


	/**
	 * Imposta l'ID dello sport.
	 */
	public void setIDSport(int IDSport){
		this.IDSport = IDSport;
	}
	
	/**
	 * Restituisce la lista degli impianti associati allo sport.
	 */
	public ArrayList<Impianto> getImpianti(){
		return this.Impianti;
	}

	/**
	 * Restituisce la lista degli istruttori associati allo sport.
	 */
	public ArrayList<Istruttore> getIstruttori() {
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
	public boolean removeImpianto(Impianto impianto) {
		if (this.Impianti.contains(impianto)){
			this.Impianti.remove(impianto);
			return true;	
		}
		return false;
	}

	/**
	 * Rimuove un istruttore tra quelli associati allo sport.
	 */
	public boolean removeIstruttore(Istruttore istruttore) {
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
