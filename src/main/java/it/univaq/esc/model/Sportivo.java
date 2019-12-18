package it.univaq.esc.model;

import java.util.List;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import it.univaq.esc.utility.SimpleFactory;

@Entity
@Table(name = "sportivo")
public class Sportivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDSportivo", nullable = false)
	private Integer IDSportivo;
	@Column
	private String nome;
	@Column(name = "cognome", nullable = false)
	private String cognome;
	@Column(name = "dataNascita", nullable = true)
	private Date dataNascita;
	
	@OneToMany(mappedBy = "sportivoPrenotante")
	private List<Prenotazione> prenotazioni;
	@ManyToMany(mappedBy = "Partecipanti")
	private List<Prenotazione> partecipazioni;
	@ManyToMany(mappedBy = "Invitati")
	private List<Prenotazione> inviti;
	
	@OneToMany(mappedBy = "proprietario")	
	private List<CartaCredito> carteCredito;
    @OneToMany(mappedBy = "possessore")
    private List<Sconto> sconti;
	@OneToMany(mappedBy = "sportivoAssociato")
	private List<QuotaPartecipazione> quotePartecipazione;
	
	
	public Sportivo() {}


	public Integer getIDSportivo() {
		return IDSportivo;
	}


	public void setIDSportivo(Integer iDSportivo) {
		IDSportivo = iDSportivo;
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


	public List<Prenotazione> getPartecipazioni() {
		return partecipazioni;
	}


	public void setPartecipazioni(List<Prenotazione> partecipazioni) {
		this.partecipazioni = partecipazioni;
	}


	public List<Prenotazione> getInviti() {
		return inviti;
	}


	public void setInviti(List<Prenotazione> inviti) {
		this.inviti = inviti;
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
