package it.univaq.esc.dto;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import it.univaq.esc.model.Corso;
import it.univaq.esc.model.Lezione;
import it.univaq.esc.model.Sport;

@Component
public class IstruttoreDTO {

	private String nome;
	private String cognome;
	private Date dataNascita;
	private Sport sportInsegnato;
	private List<Lezione> lezioni;
	private List<Corso> corsiTenuti;
	
	
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
	public List<Corso> getCorsiTenuti() {
		return corsiTenuti;
	}
	public void setCorsiTenuti(List<Corso> corsiTenuti) {
		this.corsiTenuti = corsiTenuti;
	}
	
	
	
	
}
