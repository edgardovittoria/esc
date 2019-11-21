package it.univaq.Model;

import java.util.Date;

public class Istruttore {
	
	private int IDIstruttore;
	private String nome;
	private String cognome;
	private Date dataNascita;
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
