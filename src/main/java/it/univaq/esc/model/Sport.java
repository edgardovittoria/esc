package it.univaq.esc.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sport")
public abstract class Sport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int IDSport;
	@Column
	protected String sportDescription;
	@ManyToMany
	protected List<Impianto> Impianti;
	@OneToMany(mappedBy = "IDIstruttore")
	protected List<Istruttore> Istruttori; 
	

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
	public List<Impianto> getImpianti(){
		return this.Impianti;
	}

	/**
	 * Restituisce la lista degli istruttori associati allo sport.
	 */
	public List<Istruttore> getIstruttori() {
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
