package it.univaq.Model;

import java.util.ArrayList;
import java.util.Date;

import it.univaq.Utility.SimpleFactory;

public class Sportivo {

	private int IDSportivo;
	private String nome;
	private String cognome;
	private Date dataNascita;
	private ArrayList<Prenotazione> prenotazioni;
	private ArrayList<CartaCredito> carteCredito;
	private ArrayList<Sconto> sconti;
	private ArrayList<QuotaPartecipazione> quotePartecipazione;
	private SimpleFactory simpleFactory;
	
	public Sportivo() {
		
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
	

	public ArrayList<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(ArrayList<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	public ArrayList<CartaCredito> getCarteCredito() {
		return carteCredito;
	}

	public void setCarteCredito(ArrayList<CartaCredito> carteCredito) {
		this.carteCredito = carteCredito;
	}

	public ArrayList<Sconto> getSconti() {
		return sconti;
	}

	public void setSconti(ArrayList<Sconto> sconti) {
		this.sconti = sconti;
	}

	public ArrayList<QuotaPartecipazione> getQuotePartecipazione() {
		return quotePartecipazione;
	}

	public void setQuotePartecipazione(ArrayList<QuotaPartecipazione> quotePartecipazione) {
		this.quotePartecipazione = quotePartecipazione;
	}
	
	public Prenotazione creaNuovaPrenotazione(int lastIDPrenotazione) {
		Prenotazione nuovaPrenotazione = simpleFactory.getNuovaPrenotazione(lastIDPrenotazione, this);
		return nuovaPrenotazione;
	}
	
	public void gestisciPagamento(Prenotazione prenotazione) {
		
	}

	public void setSimpleFactory(SimpleFactory simpleFactory){
		this.simpleFactory = simpleFactory;
	}
	
	
}
