package it.univaq.Model;

import java.awt.List;
import java.util.ArrayList;
import java.util.Calendar;

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
	@OneToOne
	private IPrenotabile servizioPrenotato;
	@OneToOne
	private Calendar calendario;
	@OneToMany(mappedBy = "IDSportivo")
	private ArrayList<Sportivo> Partecipanti;
	@OneToMany(mappedBy = "IDSportivo")
	private ArrayList<Sportivo> Invitati;

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
	public ArrayList<Sportivo> getPartecipanti(){
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
	public boolean confermaPrenotazione(ArrayList<Object> parametri) {
		return false;
	}
	
	
}

