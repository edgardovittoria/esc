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
	private int numParteciapnti;
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
	
	

    /**
     * @return int return the IDPrenotazione
     */
    public int getIDPrenotazione() {
        return IDPrenotazione;
    }

    /**
     * @param IDPrenotazione the IDPrenotazione to set
     */
    public void setIDPrenotazione(int IDPrenotazione) {
        this.IDPrenotazione = IDPrenotazione;
    }

    /**
     * @return Boolean return the confermata
     */
    public Boolean isConfermata() {
        return confermata;
    }

    /**
     * @param confermata the confermata to set
     */
    public void setConfermata(Boolean confermata) {
        this.confermata = confermata;
    }

    /**
     * @param numParteciapnti the numParteciapnti to set
     */
    public void setNumParteciapnti(int numParteciapnti) {
        this.numParteciapnti = numParteciapnti;
    }

    /**
     * @param numPostiLiberi the numPostiLiberi to set
     */
    public void setNumPostiLiberi(int numPostiLiberi) {
        this.numPostiLiberi = numPostiLiberi;
    }

    /**
     * @return Sportivo return the sportivoPrenotante
     */
    public Sportivo getSportivoPrenotante() {
        return sportivoPrenotante;
    }

    /**
     * @param sportivoPrenotante the sportivoPrenotante to set
     */
    public void setSportivoPrenotante(Sportivo sportivoPrenotante) {
        this.sportivoPrenotante = sportivoPrenotante;
    }

    /**
     * @return CostoPrenotazione return the costoPrenotazione
     */
    public CostoPrenotazione getCostoPrenotazione() {
        return costoPrenotazione;
    }

    /**
     * @param costoPrenotazione the costoPrenotazione to set
     */
    public void setCostoPrenotazione(CostoPrenotazione costoPrenotazione) {
        this.costoPrenotazione = costoPrenotazione;
    }

    /**
     * @return IPrenotabile return the servizioPrenotato
     */
    public IPrenotabile getServizioPrenotato() {
        return servizioPrenotato;
    }

    /**
     * @param servizioPrenotato the servizioPrenotato to set
     */
    public void setServizioPrenotato(IPrenotabile servizioPrenotato) {
        this.servizioPrenotato = servizioPrenotato;
    }

    /**
     * @return Calendar return the calendario
     */
    public Calendar getCalendario() {
        return calendario;
    }

    /**
     * @param calendario the calendario to set
     */
    public void setCalendario(Calendar calendario) {
        this.calendario = calendario;
    }

    /**
     * @param Partecipanti the Partecipanti to set
     */
    public void setPartecipanti(List<Sportivo> Partecipanti) {
        this.Partecipanti = Partecipanti;
    }

    /**
     * @return List<Sportivo> return the Invitati
     */
    public List<Sportivo> getInvitati() {
        return Invitati;
    }

    /**
     * @param Invitati the Invitati to set
     */
    public void setInvitati(List<Sportivo> Invitati) {
        this.Invitati = Invitati;
    }

}

