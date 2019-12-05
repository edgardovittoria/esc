package it.univaq.esc.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "istruttori")
public class Istruttore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDIstruttore;
	@Column
	private String nome;
	@Column
	private String cognome;
	@Column
	private Date dataNascita;
	@OneToOne
	private Sport sportInsegnato;
	@OneToMany(mappedBy = "IDLezione")
	private List<Lezione> lezioni;
	
	

	public Istruttore() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Sport getSportInsegnato() {
		return sportInsegnato;
	}

	public void setSportInsegnato(Sport sportInsegnato) {
		this.sportInsegnato = sportInsegnato;
	}
	
	public List<Lezione> getLezioni() {
		return lezioni;
	}

	public void setLezioni(List<Lezione> lezioni) {
		this.lezioni = lezioni;
	}
	
	
}
