package it.univaq.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	
}
