package it.univaq.esc.model;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
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
	@Column(name = "nome", insertable = false, updatable = false)
	protected String nome;
	@ManyToMany
	@JoinColumn(name = "sportPraticabili")
	protected Set<Impianto> impianti;
	@OneToMany(mappedBy = "sportInsegnato")
	protected Set<Istruttore> istruttori; 
	

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
	public Set<Impianto> getImpianti(){
		return this.impianti;
	}

	public void setImpianti(Set<Impianto> impianti){
		this.impianti = impianti;
	}

	/**
	 * Restituisce la lista degli istruttori associati allo sport.
	 */
	public Set<Istruttore> getIstruttori() {
		return this.istruttori;	
	}


	public void setIstruttori(Set<Istruttore> istruttori){
		this.istruttori = istruttori;
	}

	public String getNome(){
		return this.nome;
	}
	
}
