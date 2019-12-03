package it.univaq.Model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import it.univaq.Utility.SimpleFactory;

@Entity
@Table(name = "sportivo")
public class Sportivo {

	@Id
	@GeneratedValue
	@Column(name = "IDSportivo", nullable = false)
	private Integer IDSportivo;
	@Column
	private String nome;
	@Column(name = "cognome", nullable = false)
	private String cognome;
	@Column(name = "dataNascita", nullable = false)
	private Date dataNascita;
	@OneToMany(mappedBy = "IDPrenotazione")
	private ArrayList<Prenotazione> prenotazioni;
    @OneToMany(mappedBy = "IDCartaCredito")	
	private ArrayList<CartaCredito> carteCredito;
    @OneToMany(mappedBy = "IDSconto")
    private ArrayList<Sconto> sconti;
	@OneToMany(mappedBy = "IDQuotaPartecipazione")
	private ArrayList<QuotaPartecipazione> quotePartecipazione;
	
	@Transient
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
		Prenotazione nuovaPrenotazione = this.simpleFactory.getNuovaPrenotazione(lastIDPrenotazione, this); 
		return nuovaPrenotazione;
	}
	
	public void gestisciPagamento(Prenotazione prenotazione) {
		
	}

	public void setSimpleFactory(SimpleFactory simpleFactory){
		this.simpleFactory = simpleFactory;
	}
	
	
}
