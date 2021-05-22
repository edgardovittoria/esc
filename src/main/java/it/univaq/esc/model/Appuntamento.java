package it.univaq.esc.model;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder.In;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.costi.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "appuntamenti")
@Getter
@Setter
@NoArgsConstructor
public class Appuntamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private Integer idAppuntamento;
	@Column
	private LocalDateTime dataOraInizioAppuntamento;
	@Column
	private LocalDateTime dataOraFineAppuntamento;

	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "PrenotazioneSpecs_ID", nullable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private PrenotazioneSpecs prenotazioneSpecsAppuntamento;

	@Transient
	private CalcolatoreCosto calcolatoreCosto;

	@ManyToMany()
	@JoinColumn()
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UtentePolisportivaAbstract> partecipanti = new ArrayList<UtentePolisportivaAbstract>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<QuotaPartecipazione> quotePartecipazione = new ArrayList<QuotaPartecipazione>();

	public Appuntamento(LocalDateTime dataOraInizioAppuntamento, LocalDateTime dataOraFineAppuntamento,
			PrenotazioneSpecs specificaPrenotazione) {
		setDataOraInizioAppuntamento(dataOraInizioAppuntamento);
		setDataOraFineAppuntamento(dataOraFineAppuntamento);
		setPrenotazioneSpecsAppuntamento(specificaPrenotazione);
	}

	public void aggiungiQuotaPartecipazione(QuotaPartecipazione quota) {
		if (quota != null) {
			this.getQuotePartecipazione().add(quota);
		}
	}

	public Integer getIdPrenotazione() {
		return this.getPrenotazioneSpecsAppuntamento().getIdPrenotazioneAssociata();
	}

	public Impianto getImpiantoPrenotato() {
		return (Impianto) this.getPrenotazioneSpecsAppuntamento().getValoriSpecificheExtraPrenotazione()
				.get("impianto");
	}

	public void calcolaCosto() {
		this.getPrenotazioneSpecsAppuntamento().setCosto(getCalcolatoreCosto().calcolaCosto(this));
	}

	public void aggiungiPartecipante(UtentePolisportivaAbstract sportivoPartecipante) {
		if (!this.utenteIsPartecipante(sportivoPartecipante)) {
			this.getPartecipanti().add(sportivoPartecipante);
		}
	}

	public void confermaAppuntamento() {
		this.getPrenotazioneSpecsAppuntamento().setConfermata(true);
	}

	public LocalDate getDataAppuntamento() {
		return this.dataOraFineAppuntamento.toLocalDate();
	}

	public LocalTime getOraInizioAppuntamento() {
		return this.dataOraInizioAppuntamento.toLocalTime();
	}

	public LocalTime getOraFineAppuntamento() {
		return this.dataOraFineAppuntamento.toLocalTime();
	}

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

	public Integer getNumeroPartecipantiMassimo() {
		return this.getPrenotazioneSpecsAppuntamento().getSogliaMassimaPartecipanti();
	}

	public Integer getSogliaMinimaPartecipantiPerConferma() {
		return this.getPrenotazioneSpecsAppuntamento().getSogliaPartecipantiPerConferma();
	}

	public String getTipoPrenotazione() {
		return this.getPrenotazioneSpecsAppuntamento().getTipoPrenotazione();
	}

	public boolean isPending() {
		return this.getPrenotazioneSpecsAppuntamento().isPending();
	}

	public void setPending(boolean pending) {
		this.prenotazioneSpecsAppuntamento.setPending(pending);
	}

	/**
	 * Restituisce l'utente che ha creato la Prenotazione da cui è scaturito questo
	 * appuntamento.
	 * 
	 * @return Utente che ha creato la prenotazione per questo appuntamento.
	 */
	public UtentePolisportivaAbstract creatoDa() {
		return this.getPrenotazioneSpecsAppuntamento().getSportivoPrenotante();
	}

	/**
	 * Indica se un utente passato come parametro partecipa o meno all'appuntamento.
	 * 
	 * @param utenteDaVerificarePartecipazione utente di cui verificare la
	 *                                         partecipazione all'appuntamento
	 * @return true se l'utente è un partecipante, false altrimenti
	 */
	public boolean utenteIsPartecipante(UtentePolisportivaAbstract utenteDaVerificarePartecipazione) {
		for (UtentePolisportivaAbstract partecipante : this.getPartecipanti()) {
			if (partecipante.isEqual(utenteDaVerificarePartecipazione)) {
				return true;
			}
		}
		return false;
	}
	
	public Float getCostoAppuntamento() {
		return this.prenotazioneSpecsAppuntamento.getCosto();
	}
	
	public Map<String, Float> getMappaCostiAppuntamento(){
		return this.getPrenotazioneSpecsAppuntamento().getMappaCosti();
	}

}