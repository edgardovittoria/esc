package it.univaq.esc.model;

import java.util.Set;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;




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
	
	@OneToMany(mappedBy = "sportivoPrenotante", fetch = FetchType.EAGER)
	private Set<Prenotazione> prenotazioni;
	@ManyToMany(mappedBy = "Partecipanti", fetch = FetchType.EAGER)
	private Set<Prenotazione> partecipazioni;
	@ManyToMany(mappedBy = "Invitati", fetch = FetchType.EAGER)
	private Set<Prenotazione> inviti;
	
	@OneToMany(mappedBy = "proprietario", fetch = FetchType.EAGER)	
	private Set<CartaCredito> carteCredito;
    @OneToMany(mappedBy = "possessore", fetch = FetchType.EAGER)
    private Set<Sconto> sconti;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "sportivoAssociato")
	private Set<QuotaPartecipazione> quotePartecipazione;
	
	
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


	public Set<Prenotazione> getInviti() {
		return inviti;
	}


	public void setInviti(Set<Prenotazione> inviti) {
		this.inviti = inviti;
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
