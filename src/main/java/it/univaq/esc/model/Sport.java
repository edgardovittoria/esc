package it.univaq.esc.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "nome")
@Table(name = "sport")
public abstract class Sport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int idSport;
	@Column
	protected String sportDescription;
	@ManyToMany(mappedBy = "sportPraticabili")
	protected List<Impianto> impianti;
	@OneToMany(mappedBy = "sportInsegnato")
	protected List<Istruttore> istruttori; 
	

	/**
	 * Restituisce la descrizione dello sport.
	 */
	public String getSportDescription(){
		return this.sportDescription;
	}


	/**
	 * Inserisce la descrizione dello sport.
	 */
	public void setSportDescription(String description){
		this.sportDescription = description;
	}

	/**
	 * Restituisce l'ID dello sport.
	 */
	public int getIdSport(){
		return this.idSport;
	}


	/**
	 * Imposta l'ID dello sport.
	 */
	public void setIDSport(int IDSport){
		this.idSport = IDSport;
	}
	
	/**
	 * Restituisce la lista degli impianti associati allo sport.
	 */
	public List<Impianto> getImpianti(){
		return this.impianti;
	}

	/**
	 * Restituisce la lista degli istruttori associati allo sport.
	 */
	public List<Istruttore> getIstruttori() {
		return this.istruttori;	
	}

	/**
	 * Associa un impianto allo sport.
	 */
	public void addImpianto( Impianto impianto) {
		this.impianti.add(impianto);
	}

	/**
	 * Associa un istruttore allo sport.
	 */
	public void addIstruttore( Istruttore istruttore) {
		this.istruttori.add(istruttore);
	}


	/**
	 * Rimuove un impianto tra quelli associati allo sport.
	 */
	public boolean removeImpianto(Impianto impianto) {
		if (this.impianti.contains(impianto)){
			this.impianti.remove(impianto);
			return true;	
		}
		return false;
	}

	/**
	 * Rimuove un istruttore tra quelli associati allo sport.
	 */
	public boolean removeIstruttore(Istruttore istruttore) {
		if (this.istruttori.contains(istruttore)){
			this.istruttori.remove(istruttore);
			return true;	
		}
		return false;
	}

	/**
	 * Restituice il numero di impianti associati allo sport.
	 */
	public int numberOfImpianti(){
		return this.impianti.size();
	}


	/**
	 * Restituice il numero di istruttori associati allo sport.
	 */
	public int numberOfIstruttori(){
		return this.istruttori.size();
	}
	
}
