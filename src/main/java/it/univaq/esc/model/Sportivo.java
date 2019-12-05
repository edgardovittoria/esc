package it.univaq.esc.model;

import java.util.List;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import it.univaq.utility.SimpleFactory;

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
	private List<Prenotazione> prenotazioni;
    @OneToMany(mappedBy = "IDCartaCredito")	
	private List<CartaCredito> carteCredito;
    @OneToMany(mappedBy = "IDSconto")
    private List<Sconto> sconti;
	@OneToMany(mappedBy = "IDQuotaPartecipazione")
	private List<QuotaPartecipazione> quotePartecipazione;
	
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
	

	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
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
	
	public Prenotazione creaNuovaPrenotazione(int lastIDPrenotazione) {
		Prenotazione nuovaPrenotazione = this.simpleFactory.getNuovaPrenotazione(lastIDPrenotazione, this); 
		return nuovaPrenotazione;
	}
	
	public void gestisciPagamento(Prenotazione prenotazione) {
		
	}

	public void setSimpleFactory(SimpleFactory simpleFactory){
		this.simpleFactory = simpleFactory;
	}

	public void addPrenotazione(Prenotazione prenotazione) {
		this.prenotazioni.add(prenotazione);
		
	}
	
	
}
