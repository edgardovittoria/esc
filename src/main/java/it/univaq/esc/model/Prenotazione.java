package it.univaq.esc.model;


import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "prenotazione")
public class Prenotazione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDPrenotazione;
	@Column
	private Boolean confermata;

	private enum modalita{
		STANDARD, PENDING
	}
	@Column
	private int numPartecipanti;
	@Column
	private int numPostiLiberi;
    @ManyToOne
	private Sportivo sportivoPrenotante;
	@OneToOne
	private CostoPrenotazione costoPrenotazione;
	@Transient
	private IPrenotabile servizioPrenotato;
	@Column
	private Calendar calendario;
	@ManyToMany
	private List<Sportivo> Partecipanti;
	@ManyToMany
	private List<Sportivo> Invitati;

	//costruttore
	public Prenotazione(int LastIDPrenotazione, Sportivo sportivoPrenotante) {
		this.IDPrenotazione = LastIDPrenotazione;
		this.sportivoPrenotante = sportivoPrenotante;
	}

	public int getIDPrenotazione() {
		return IDPrenotazione;
	}

	public void setIDPrenotazione(int iDPrenotazione) {
		IDPrenotazione = iDPrenotazione;
	}

	public Boolean getConfermata() {
		return confermata;
	}

	public void setConfermata(Boolean confermata) {
		this.confermata = confermata;
	}

	public int getNumPartecipanti() {
		return numPartecipanti;
	}

	public void setNumPartecipanti(int numPartecipanti) {
		this.numPartecipanti = numPartecipanti;
	}

	public int getNumPostiLiberi() {
		return numPostiLiberi;
	}

	public void setNumPostiLiberi(int numPostiLiberi) {
		this.numPostiLiberi = numPostiLiberi;
	}

	public Sportivo getSportivoPrenotante() {
		return sportivoPrenotante;
	}

	public void setSportivoPrenotante(Sportivo sportivoPrenotante) {
		this.sportivoPrenotante = sportivoPrenotante;
	}

	public CostoPrenotazione getCostoPrenotazione() {
		return costoPrenotazione;
	}

	public void setCostoPrenotazione(CostoPrenotazione costoPrenotazione) {
		this.costoPrenotazione = costoPrenotazione;
	}

	public IPrenotabile getServizioPrenotato() {
		return servizioPrenotato;
	}

	public void setServizioPrenotato(IPrenotabile servizioPrenotato) {
		this.servizioPrenotato = servizioPrenotato;
	}

	public Calendar getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendar calendario) {
		this.calendario = calendario;
	}

	public List<Sportivo> getPartecipanti() {
		return Partecipanti;
	}

	public void setPartecipanti(List<Sportivo> partecipanti) {
		Partecipanti = partecipanti;
	}

	public List<Sportivo> getInvitati() {
		return Invitati;
	}

	public void setInvitati(List<Sportivo> invitati) {
		Invitati = invitati;
	}
	
}

