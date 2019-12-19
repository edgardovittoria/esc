package it.univaq.esc.dto;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Component;

import it.univaq.esc.model.CartaCredito;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.QuotaPartecipazione;
import it.univaq.esc.model.Sconto;

@Component
public class SportivoDTO {

	private String nome;
	private String cognome;
	private Date dataNascita;
	private Set<Prenotazione> prenotazioni;
	private Set<Prenotazione> partecipazioni;
	private Set<CartaCredito> carteCredito;
	private Set<Sconto> sconti;
	private Set<QuotaPartecipazione> quotePartecipazione;
	
	
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
	public Set<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(Set<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	public Set<Prenotazione> getPartecipazioni() {
		return partecipazioni;
	}
	public void setPartecipazioni(Set<Prenotazione> partecipazioni) {
		this.partecipazioni = partecipazioni;
	}
	public Set<CartaCredito> getCarteCredito() {
		return carteCredito;
	}
	public void setCarteCredito(Set<CartaCredito> carteCredito) {
		this.carteCredito = carteCredito;
	}
	public Set<Sconto> getSconti() {
		return sconti;
	}
	public void setSconti(Set<Sconto> sconti) {
		this.sconti = sconti;
	}
	public Set<QuotaPartecipazione> getQuotePartecipazione() {
		return quotePartecipazione;
	}
	public void setQuotePartecipazione(Set<QuotaPartecipazione> quotePartecipazione) {
		this.quotePartecipazione = quotePartecipazione;
	}
	
	
}
