package it.univaq.esc.model.prenotazioni;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;

import it.univaq.esc.model.Sport;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
public abstract class PrenotazioneSpecs {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private Integer id;
	@Column
	private boolean confermata = false;
	@Column
	private boolean pending = false;
	@Column
	private float costo;

	@ManyToOne
	@JoinColumn
	private PrenotabileDescrizione specificaDescription;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "prenotazione_ID", nullable = false)
	private Prenotazione prenotazioneAssociata;

	public Sport getSportAssociato() {
		return this.getSpecificaDescription().getSportAssociato();
	}

	public abstract void impostaValoriSpecificheExtraPrenotazione(Map<String, Object> mappaValori);

	public abstract Map<String, Object> getValoriSpecificheExtraPrenotazione();
	
	/**
	 * Ritorna il tipo della prenotazione principale.
	 * @return tipo della prenotazione principale.
	 */
	public String appartieneA() {
		return this.getPrenotazioneAssociata().getTipoPrenotazione();
	}

	public abstract String getTipoPrenotazione();
	
	public Integer getIdPrenotazioneAssociata() {
		return this.getPrenotazioneAssociata().getIdPrenotazione();
	}
	
	public Map<String, Float> getMappaCosti(){
		return this.getSpecificaDescription().getMappaCosti();
	}

	public Integer getNumeroGiocatori() {
		return this.getSportAssociato().getNumeroGiocatoriPerIncontro();
	}
	
	public Integer getSogliaMassimaPartecipanti() {
		return this.getSpecificaDescription().getMassimoNumeroPartecipanti();
	}
	
	public Integer getSogliaPartecipantiPerConferma() {
		return this.getSpecificaDescription().getMinimoNumeroPartecipanti();
	};

	public UtentePolisportivaAbstract getSportivoPrenotante() {
		return this.getPrenotazioneAssociata().getSportivoPrenotante();
	}
	
	public List<PrenotazioneSpecs> getPrenotazioniSpecsFiglie(){
		return null;
	}
	
	public String getModalitaPrenotazione() {
		return getSpecificaDescription().getModalitaPrenotazione();
	}
	

}