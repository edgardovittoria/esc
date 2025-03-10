package it.univaq.esc.model.prenotazioni;

import it.univaq.esc.model.Costo;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.StrutturaPolisportiva;
import it.univaq.esc.model.TipoEventoNotificabile;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public abstract class Appuntamento extends Notificabile {

	@ManyToMany()
	@JoinColumn()
	@LazyCollection(LazyCollectionOption.FALSE)
	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private List<UtentePolisportiva> partecipanti = new ArrayList<UtentePolisportiva>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "orario_id")
	private OrarioAppuntamento orarioAppuntamento = new OrarioAppuntamento();

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

	@ManyToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<QuotaPartecipazione> quotePartecipazione = new ArrayList<QuotaPartecipazione>();

	@ManyToOne
	@JoinColumn
	private StrutturaPolisportiva strutturaPrenotata;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Costo costoAppuntamento;

	@Transient
	private CalcolatoreCosto calcolatoreCosto;

	

	public LocalDate getDataAppuntamento() {
		return getOrarioAppuntamento().getDataOrarioAppuntamento();
	}

	public LocalTime getOraInizioAppuntamento() {
		return getOrarioAppuntamento().getOraInizio();
	}

	public LocalTime getOraFineAppuntamento() {
		return getOrarioAppuntamento().getOraFine();
	}

	public Map<String, Costo> getMappaCostiAppuntamento() {
		return getDescrizioneEventoPrenotato().getMappaCosti();
	}

	public String getNomeEvento() {
		return getDescrizioneEventoPrenotato().getNomeEvento();
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

		return getOrarioAppuntamento().sovrapponeA(appuntamentoDaVerificareSovrapposizioneOrari.getDataAppuntamento(),
				appuntamentoDaVerificareSovrapposizioneOrari.getOraInizioAppuntamento(),
				appuntamentoDaVerificareSovrapposizioneOrari.getOraFineAppuntamento());

	}

	public boolean sovrapponeA(LocalDate dataAppuntamento, LocalTime oraInizio, LocalTime oraFine) {
		return getOrarioAppuntamento().sovrapponeA(dataAppuntamento, oraInizio, oraFine);
	}

	public TipoPrenotazione getTipoPrenotazione() {
		return getDescrizioneEventoPrenotato().getTipoPrenotazione();
	}

	public void confermaAppuntamento() {
		if (!isConfermato()) {
			setConfermato(true);
		}
	}

	public void calcolaCosto() {
		setCostoAppuntamento(getCalcolatoreCosto().calcolaCosto(this));
	}
	
	public void assegna(QuotaPartecipazione quota) {
		if (quota != null && !esisteGiaQuotaAssociataAllo(quota.getSportivoAssociato())) {
			this.getQuotePartecipazione().add(quota);
		}
	}

	public void assegna(List<QuotaPartecipazione> listaQuote) {
		listaQuote.forEach((quota) -> assegna(quota));
	}
	
	public ModalitaPrenotazione getModalitaPrenotazione() {
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

	public UtentePolisportiva getUtenteCheHaEffettuatoLaPrenotazioneRelativa() {
		return getPrenotazione().getSportivoPrenotante();
	}

	public boolean accettaNuoviPartecipantiOltre(Integer numeroPartecipantiAttuali) {
		return getDescrizioneEventoPrenotato().accettaNuoviPartecipantiOltre(numeroPartecipantiAttuali);
	}

	public Integer getSogliaMinimaPartecipantiPerConferma() {
		return getDescrizioneEventoPrenotato().getMinimoNumeroPartecipanti();
	}

	public boolean isEqual(Appuntamento appuntamento) {
		return this.getIdNotificabile() == appuntamento.getIdNotificabile();
	}

	public void impostaDatiAppuntamentoDa(DatiFormPerAppuntamento datiCompilatiInPrenotazione) {
		impostaOrario(datiCompilatiInPrenotazione.getDataAppuntamento(), datiCompilatiInPrenotazione.getOraInizio(),
				datiCompilatiInPrenotazione.getOraFine());
		setPending(datiCompilatiInPrenotazione.isPending());
		setDescrizioneEventoPrenotato(datiCompilatiInPrenotazione.getDescrizioneEvento());
		setStrutturaPrenotata(datiCompilatiInPrenotazione.getImpiantoPrenotato());
	}

	public abstract boolean aggiungiPartecipante(Object sportivoOSquadraPartecipante);

	public abstract boolean haComePartecipante(Object sportivoOSquadra);

	public List<UtentePolisportiva> getUtentiPartecipanti() {
		return getPartecipanti();
	};

	public abstract List<Object> getPartecipantiAppuntamento();

	public String getNominativoManutentore() {
		return getManutentore().getNome() + " " + getManutentore().getCognome();
	}

	public boolean haNumeroPartecipantiNecessarioPerConferma() {
		return getPartecipantiAppuntamento().size() >= getSogliaMinimaPartecipantiPerConferma();
	}

	public void creaQuotePartecipazionePerAppuntamento(Integer ultimoIdQuote) {
		for(UtentePolisportiva partecipante : getUtentiPartecipanti()) {
			assegna(creaQuotaPartecipazione(partecipante, ultimoIdQuote));
			ultimoIdQuote+=1;
		}
	}
	
	public boolean confermaSeRaggiuntoNumeroNecessarioDiPartecipanti() {
		if(haNumeroPartecipantiNecessarioPerConferma()) {
			confermaAppuntamento();
			return true;
		}
		return false;
	}

	private QuotaPartecipazione creaQuotaPartecipazione(UtentePolisportiva sportivo, Integer ultimoIdQuote) {

		if (!esisteGiaQuotaAssociataAllo(sportivo)) {
			QuotaPartecipazione quota = new QuotaPartecipazione(ultimoIdQuote);
			quota.impostaDati(sportivo, getCalcolatoreCosto().calcolaQuotaPartecipazione(this));
			return quota;
		}
		return null;
	}

	private boolean esisteGiaQuotaAssociataAllo(UtentePolisportiva sportivo) {
		for (QuotaPartecipazione quotaPartecipazione : getQuotePartecipazione()) {
			if (quotaPartecipazione.getSportivoAssociato().isEqual(sportivo)) {
				return true;
			}
		}
		return false;
	}

	public Sport getSportEvento() {
		return getDescrizioneEventoPrenotato().getSportAssociato();
	}

	public void siAggiungeAlCalendarioDelProprioManutentore() {
		getManutentore().comeManutentore().segnaInAgendaIl(this);
	}

	public void siAggiungeAlCalendarioDelRelativoImpiantoPrenotato() {
		getStrutturaPrenotata().segnaInCalendarioIl(this);
	}

	public void siAggiungeAlCalendarioDelloSportivoCheHaEffettuatoLaPrenotazioneRelativa() {
		getUtenteCheHaEffettuatoLaPrenotazioneRelativa().comeSportivo().segnaInAgendaIl(this);
	}

	public void impostaOrario(LocalDate dataAppuntamento, LocalTime oraInizioAppuntamento,
			LocalTime oraFineAppuntamento) {
		getOrarioAppuntamento().imposta(dataAppuntamento, oraInizioAppuntamento, oraFineAppuntamento);
	}

	public boolean haManutentoreAssegnato() {
		if (getManutentore() != null) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> getInfo() {
		Map<String, Object> mappaDati = new HashMap<String, Object>();
		mappaDati.put("sportNome", getSportEvento().getNome());
		mappaDati.put("tipoPrenotazione", getTipoPrenotazione());
		mappaDati.put("modalitaPrenotazione", getModalitaPrenotazione());
		mappaDati.put("identificativo", getIdNotificabile());

		return mappaDati;
	}

	@Override
	public String getTipoEventoNotificabile() {

		return TipoEventoNotificabile.APPUNTAMENTO.toString();
	}

}
