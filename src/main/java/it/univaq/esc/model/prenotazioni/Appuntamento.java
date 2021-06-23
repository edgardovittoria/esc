package it.univaq.esc.model.prenotazioni;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @NoArgsConstructor
public abstract class Appuntamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.PRIVATE)
	private Integer idAppuntamento;
	
	@ManyToMany()
	@JoinColumn()
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private List<UtentePolisportiva> partecipanti = new ArrayList<UtentePolisportiva>();
	
	@Column
	private LocalDateTime dataOraInizioAppuntamento;
	@Column
	private LocalDateTime dataOraFineAppuntamento;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "prenotazione_id")
	private Prenotazione prenotazione;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()
	private UtentePolisportiva manutentore;
	
	@Column
	private boolean confermato = false;
	
	@Column
	private boolean pending = false;
	
	
	@ManyToOne
	@JoinColumn
	private PrenotabileDescrizione descrizioneEventoPrenotato;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<QuotaPartecipazione> quotePartecipazione = new ArrayList<QuotaPartecipazione>();
	
	@ManyToOne
	@JoinColumn
	private Impianto impiantoPrenotato;
	
	@Column
	private float costoAppuntamento;

	@Transient
	private CalcolatoreCosto calcolatoreCosto;
	
	
	
	
	public LocalDate getDataAppuntamento() {
		return this.dataOraFineAppuntamento.toLocalDate();
	}

	public LocalTime getOraInizioAppuntamento() {
		return this.dataOraInizioAppuntamento.toLocalTime();
	}

	public LocalTime getOraFineAppuntamento() {
		return this.dataOraFineAppuntamento.toLocalTime();
	}
		
	
	public Map<String, Float> getMappaCostiAppuntamento(){
		return getDescrizioneEventoPrenotato().getMappaCosti();
	}
	
	
	public String appartieneA() {
		return getDescrizioneEventoPrenotato().getTipoPrenotazione();
	}
	
	/**
	 * Restituisce l'utente che ha creato la Prenotazione da cui è scaturito questo
	 * appuntamento.
	 * 
	 * @return Utente che ha creato la prenotazione per questo appuntamento.
	 */
//	public UtentePolisportivaAbstract creatoDa() {
//		return this.getPrenotazioneSpecsAppuntamento().getSportivoPrenotante();
//	}
	
	/**
	 * Restituisce la prenotazione principale a cui l'appuntamento fa capo
	 * @return la prenotazione principale.
	 */
//	public Prenotazione getPrenotazionePrincipale() {
//		return this.getPrenotazioneSpecsAppuntamento().getPrenotazioneAssociata();
//	}
	
	/**
	 * Verifica se l'appuntamento passato come parametro si sovrappone a quello sul
	 * quale è richiamato.
	 * 
	 * @param appuntamentoDaVerificareSovrapposizioneOrari appuntamento di cui
	 *                                                     verificare la
	 *                                                     sovrapposizione
	 * @return true se i due appuntamenti si sovrappongono, false altrimenti
	 */
	public boolean sovrapponeA(Appuntamento appuntamentoDaVerificareSovrapposizioneOrari) {
		if (this.getDataOraInizioAppuntamento()
				.isBefore(appuntamentoDaVerificareSovrapposizioneOrari.getDataOraInizioAppuntamento())
				&& this.getDataOraFineAppuntamento()
						.isAfter(appuntamentoDaVerificareSovrapposizioneOrari.getDataOraInizioAppuntamento())) {
			return true;
		} else if (this.getDataOraInizioAppuntamento()
				.isBefore(appuntamentoDaVerificareSovrapposizioneOrari.getDataOraFineAppuntamento())
				&& this.getDataOraFineAppuntamento()
						.isAfter(appuntamentoDaVerificareSovrapposizioneOrari.getDataOraFineAppuntamento())) {
			return true;
		} else if (this.getDataOraInizioAppuntamento()
				.equals(appuntamentoDaVerificareSovrapposizioneOrari.getDataOraInizioAppuntamento())) {
			return true;
		}
		return false;
	}

	public boolean sovrapponeA(LocalDateTime oraInizio, LocalDateTime oraFine) {
		if (this.getDataOraInizioAppuntamento().isBefore(oraInizio)
				&& this.getDataOraFineAppuntamento().isAfter(oraInizio)) {
			return true;
		} else if (this.getDataOraInizioAppuntamento().isBefore(oraFine)
				&& this.getDataOraFineAppuntamento().isAfter(oraFine)) {
			return true;
		} else if (this.getDataOraInizioAppuntamento().equals(oraInizio)) {
			return true;
		}
		return false;
	}
	
	public String getTipoPrenotazione() {
		return this.getDescrizioneEventoPrenotato().getTipoPrenotazione();
	}
	
	
	public void confermaAppuntamento() {
		if (!isConfermato() && getPartecipantiAppuntamento().size() >= getSogliaMinimaPartecipantiPerConferma()) {
			setConfermato(true);

		}
	}
	
	public void calcolaCosto() {
		setCostoAppuntamento(getCalcolatoreCosto().calcolaCosto(this));
	}
	
//	public Integer getIdPrenotazione() {
//		return this.getPrenotazioneSpecsAppuntamento().getIdPrenotazioneAssociata();
//	}

	
	
	public void aggiungiQuotaPartecipazione(QuotaPartecipazione quota) {
		if (quota != null) {
			this.getQuotePartecipazione().add(quota);
		}
	}
	
	public String getModalitaPrenotazione() {
		return getDescrizioneEventoPrenotato().getModalitaPrenotazione();
	}
	
	
	public boolean utenteIsPartecipante(UtentePolisportiva utente) {
		for (UtentePolisportiva partecipante : this.getUtentiPartecipanti()) {
			if (partecipante.isEqual(utente)) {
				return true;
			}
		}
		return false;
	};
	
//	public Integer getNumeroPartecipantiMassimo() {
//		return this.getPrenotazioneSpecsAppuntamento().getSogliaMassimaPartecipanti();
//	};
	
	public Sport getSportAssociato() {
		return getDescrizioneEventoPrenotato().getSportAssociato();
	}
	
	public boolean accettaNuoviPartecipantiOltre(Integer numeroPartecipantiAttuali) {
		return getDescrizioneEventoPrenotato().accettaNuoviPartecipantiOltre(numeroPartecipantiAttuali);
	}
	
	public Integer getSogliaMinimaPartecipantiPerConferma() {
		return getDescrizioneEventoPrenotato().getMinimoNumeroPartecipanti();
	}
	
	public boolean isEqual(Appuntamento appuntamento) {
		return this.getIdAppuntamento() == appuntamento.getIdAppuntamento();
	}
	
	public abstract boolean aggiungiPartecipante(Object sportivoOSquadraPartecipante);
	
	public abstract boolean haComePartecipante(Object sportivoOSquadra);
	
	public List<UtentePolisportiva> getUtentiPartecipanti(){
		return getPartecipanti();
	};
	public abstract List<Object>  getPartecipantiAppuntamento();
	
	public String getNominativoManutentore() {
		return getManutentore().getNome() + " " + getManutentore().getCognome();
	}
}
