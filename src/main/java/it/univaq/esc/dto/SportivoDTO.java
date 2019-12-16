package it.univaq.esc.dto;

import java.util.Date;
import java.util.List;

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
	private List<Prenotazione> prenotazioni;
	private List<Prenotazione> partecipazioni;
	private List<CartaCredito> carteCredito;
	private List<Sconto> sconti;
	private List<QuotaPartecipazione> quotePartecipazione;
	
	
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
	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	public List<Prenotazione> getPartecipazioni() {
		return partecipazioni;
	}
	public void setPartecipazioni(List<Prenotazione> partecipazioni) {
		this.partecipazioni = partecipazioni;
	}
	public List<CartaCredito> getCarteCredito() {
		return carteCredito;
	}
	public void setCarteCredito(List<CartaCredito> carteCredito) {
		this.carteCredito = carteCredito;
	}
	public List<Sconto> getSconti() {
		return sconti;
	}
	public void setSconti(List<Sconto> sconti) {
		this.sconti = sconti;
	}
	public List<QuotaPartecipazione> getQuotePartecipazione() {
		return quotePartecipazione;
	}
	public void setQuotePartecipazione(List<QuotaPartecipazione> quotePartecipazione) {
		this.quotePartecipazione = quotePartecipazione;
	}
	
	
}
