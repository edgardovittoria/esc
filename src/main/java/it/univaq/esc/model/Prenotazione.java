package it.univaq.esc.model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.omg.CORBA.PRIVATE_MEMBER;

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
	private int numParteciapnti;
	@Column
	private int numPostiLiberi;
	@OneToOne
	@JoinColumn(name = "IDSportivo")
	private Sportivo sportivoPrenotante;
	@OneToOne
	private CostoPrenotazione costoPrenotazione;
	@Transient
	private IPrenotabile servizioPrenotato;
	@Column
	private Calendar calendario;
	@OneToMany(mappedBy = "IDSportivo")
	private List<Sportivo> Partecipanti;
	@OneToMany(mappedBy = "IDSportivo")
	private List<Sportivo> Invitati;

	//costruttore
	public Prenotazione(int LastIDPrenotazione, Sportivo sportivoPrenotante) {
		super();
		this.IDPrenotazione = LastIDPrenotazione;
		this.sportivoPrenotante = sportivoPrenotante;
	}
	public int getNumParteciapnti() {
		return numParteciapnti;
	}
	public int getNumPostiLiberi() {
		return numPostiLiberi;
	}
	public List<Sportivo> getPartecipanti(){
		return this.Partecipanti;
	}
	public void aggiornaNumPostiLiberi() {
		this.numPostiLiberi = this.getNumPostiLiberi() - 1;
	}
	public void aggiornaNumPartecipanti() {
		this.numParteciapnti = this.getNumParteciapnti() + 1;
	}
	public void generaQuotaPartecipazione() {
		
	}
	public boolean confermaPrenotazione(List<Object> parametri) {
		return false;
	}
	
	
}

