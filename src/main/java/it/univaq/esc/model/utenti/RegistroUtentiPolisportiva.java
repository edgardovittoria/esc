package it.univaq.esc.model.utenti;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.repository.UtentePolisportivaRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Singleton
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
public class RegistroUtentiPolisportiva {

	private UtentePolisportivaRepository utentiRepository;

	@Getter(value = AccessLevel.PUBLIC)
	private List<UtentePolisportiva> listaUtentiPolisportiva = new ArrayList<UtentePolisportiva>();

	private RegistroAppuntamenti registroAppuntamenti;

	public RegistroUtentiPolisportiva(UtentePolisportivaRepository utentePolisportivaAbstractRepository,
			RegistroAppuntamenti registroAppuntamenti) {
		this.setUtentiRepository(utentePolisportivaAbstractRepository);
		setRegistroAppuntamenti(registroAppuntamenti);
		popola();
	}

	/**
	 * Viene invocato subito dopo l'istanziazione del registro utenti, per popolare
	 * quest'ultimo con tutti gli utenti presenti nel database
	 */
	public void popola() {
		this.setListaUtentiPolisportiva(this.utentiRepository.findAll());

		/*
		 * Popoliamo il calendario degli sportivi
		 */
		for (UtentePolisportiva utente : getListaUtentiByRuolo(TipoRuolo.SPORTIVO)) {
			List<Appuntamento> listaAppuntamenti = getRegistroAppuntamenti().getAppuntamentiPerPartecipante(utente);
			if (!listaAppuntamenti.isEmpty()) {
				Calendario calendario = new Calendario();
				calendario.setListaAppuntamenti(listaAppuntamenti);
				utente.comeSportivo().segnaInAgendaGliAppuntamentiDel(calendario);

			}
		}

		/*
		 * Popoliamo il calendario degli istruttori
		 */
		for (UtentePolisportiva utente : getListaUtentiByRuolo(TipoRuolo.ISTRUTTORE)) {
			List<Appuntamento> listaLezioni = getRegistroAppuntamenti().getListaLezioniPerIstruttore(utente);
			if (!listaLezioni.isEmpty()) {
				Calendario calendarioIstruttore = new Calendario();
				calendarioIstruttore.setListaAppuntamenti(listaLezioni);
				utente.comeIstruttore().segnaInAgendaLeLezioniDel(calendarioIstruttore);
			}
		}

		/*
		 * Popoliamo il calendario dei manutentori
		 */
		for (UtentePolisportiva utente : getListaUtentiByRuolo(TipoRuolo.MANUTENTORE)) {
			List<Appuntamento> listaAppuntamenti = getRegistroAppuntamenti().getListaAppuntamentiManutentore(utente);
			if (!listaAppuntamenti.isEmpty()) {
				Calendario calendarioManutentore = new Calendario();
				calendarioManutentore.setListaAppuntamenti(listaAppuntamenti);
				utente.comeManutentore().segnaInAgendaGliAppuntamentiDel(calendarioManutentore);
			}
		}

	}

	/**
	 * Registra l'utente passato nel sistema della polisportiva (registro e
	 * database)
	 * 
	 * @param utente utente da registrare
	 */
	public void registraUtente(UtentePolisportiva utente) {
		getListaUtentiPolisportiva().add(utente);
		utentiRepository.save(utente);
	}

	/**
	 * Restituisce l'utente associato alla mail passata come parametro
	 * 
	 * @param emailUtente email dell'utente da ricercare
	 * @return l'utente associato alla email passata come parametro, se registrato
	 */
	public UtentePolisportiva getUtenteByEmail(String emailUtente) {
		for (UtentePolisportiva sportivo : getListaUtentiPolisportiva()) {
			if (sportivo.isSuaQuesta(emailUtente)) {
				return sportivo;
			}
		}
		return null;
	}

	/**
	 * Ritorna una lista degli utenti che hanno nella polisportiva il ruolo passato
	 * come parametro
	 * 
	 * @param ruolo ruolo che devono avere gli utenti ricercati
	 * @return
	 */
	public List<UtentePolisportiva> getListaUtentiByRuolo(TipoRuolo ruolo) {
		List<UtentePolisportiva> listaUtentiByRuolo = new ArrayList<UtentePolisportiva>();
		for (UtentePolisportiva utente : this.getListaUtentiPolisportiva()) {
			if (utente.is(ruolo)) {
				listaUtentiByRuolo.add(utente);
			}
		}
		return listaUtentiByRuolo;
	}

	/**
	 * Elimina utente polisportiva passato come parametro dal registro e dal
	 * database.
	 * 
	 * @param utente utente da eliminare
	 */
	public void eliminaUtente(UtentePolisportiva utente) {
		this.getListaUtentiPolisportiva().remove(utente);
		this.getUtentiRepository().delete(utente);
	}

	/**
	 * Elimina utente polisportiva associato alla email passata come parametro
	 * 
	 * @param emailUtente email dell'utente da eliminare
	 */
	public void eliminaUtente(String emailUtente) {
		this.eliminaUtente(this.getUtenteByEmail(emailUtente));
	}

	/**
	 * Restituisce la lista di tutti gli istruttori per un determinato sport
	 * 
	 * @param sportDiCuiTrovareGliIstruttori Sport di cui trovare gli istruttori
	 * @return lista di tutti gli istruttori associati allo sport passato come
	 *         parametro
	 */
	public List<UtentePolisportiva> getIstruttoriPerSport(Sport sportDiCuiTrovareGliIstruttori) {
		return this.filtraIstruttoriPerSport(this.getListaUtentiByRuolo(TipoRuolo.ISTRUTTORE),
				sportDiCuiTrovareGliIstruttori);
	}

	/**
	 * Restituisce una lista dei soli isitruttori associati ad un determinato sport
	 * passato come parametro, a partire da una lista di istruttori anch'essa
	 * passata come parametro.
	 * 
	 * @param listaIstruttoriDaFiltrare      lista degli istruttori da filtrare.
	 * @param sportDiCuiTrovareGliIstruttori spsort in base al quale filtrare gli
	 *                                       istruttori.
	 * @return lista dei soli istruttori, appartenenti alla lista passata come
	 *         parametro, associati allo sport passato come parametro.
	 */
	public List<UtentePolisportiva> filtraIstruttoriPerSport(List<UtentePolisportiva> listaIstruttoriDaFiltrare,
			Sport sportDiCuiTrovareGliIstruttori) {
		List<UtentePolisportiva> istruttori = new ArrayList<UtentePolisportiva>();
		for (UtentePolisportiva utente : listaIstruttoriDaFiltrare) {

			if (utente.comeIstruttore().insegna(sportDiCuiTrovareGliIstruttori)) {
				istruttori.add(utente);
			}

		}
		return istruttori;
	}

	/**
	 * Filtra una lista di istruttori passata come parametro, sulla base di un
	 * calendario passato come parametro. Restituisce i soli istruttori della lista
	 * liberi nelle date del calendario passato come parametro.
	 * 
	 * @param listaIstruttoriDaFiltrare             lista degli istruttori da
	 *                                              filtrare.
	 * @param calendarioPerCuiFiltrareGliIstruttori calendario in base al quale
	 *                                              filtrare gli istruttori.
	 * @return la lista dei soli istruttori liberi nelle date del calendario passato
	 *         come parametro.
	 */
	public List<UtentePolisportiva> filtraIstruttorePerCalendario(List<UtentePolisportiva> listaIstruttoriDaFiltrare,
			Calendario calendarioPerCuiFiltrareGliIstruttori) {
		List<UtentePolisportiva> istruttori = new ArrayList<UtentePolisportiva>();
		for (UtentePolisportiva utente : listaIstruttoriDaFiltrare) {
			if (utente.comeIstruttore().isLiberoNelleDateDel(calendarioPerCuiFiltrareGliIstruttori)) {
				istruttori.add(utente);
			}
		}
		return istruttori;
	}

	/**
	 * Filtra una lista di istruttori passata come parametro, sulla base di un
	 * intervallo di tempo passato come parametro. Restituisce i soli istruttori
	 * della lista liberi in quel determinato intervallo di tempo.
	 * 
	 * @param listaIstruttoriDaFiltrare     lista degli istruttori da filtrare.
	 * @param oraInizioOrarioPerCuiFiltrare data e ora di inizio dell'intervallo di
	 *                                      tempo per cui filtrare.
	 * @param oraFineOrarioPerCuiFiltrare   dat e ora di fine dell'intervallo di
	 *                                      tempo per cui filtrare.
	 * @return la lista dei soli istruttori della lista di partenza, liberi
	 *         nell'intervallo di tempo passato come parametro.
	 */
	public List<UtentePolisportiva> filtraIstruttorePerOrario(List<UtentePolisportiva> listaIstruttoriDaFiltrare,
			LocalDateTime oraInizioOrarioPerCuiFiltrare, LocalDateTime oraFineOrarioPerCuiFiltrare) {
		List<UtentePolisportiva> istruttori = new ArrayList<UtentePolisportiva>();
		for (UtentePolisportiva utente : listaIstruttoriDaFiltrare) {
			if (utente.comeIstruttore().isLiberoTra(oraInizioOrarioPerCuiFiltrare, oraFineOrarioPerCuiFiltrare)) {
				istruttori.add(utente);
			}
		}
		return istruttori;
	}

	public void aggiornaCalendarioSportivo(Calendario nuovoCalendario, UtentePolisportiva utente) {
		utente.comeSportivo().segnaInAgendaGliAppuntamentiDel(nuovoCalendario);

	}

	public void aggiornaCalendarioSportivo(Appuntamento nuovoAppuntamento, UtentePolisportiva utente) {
		utente.comeSportivo().segnaInAgendaIl(nuovoAppuntamento);
	}

	public void aggiornaCalendarioIstruttore(Calendario nuovoCalendarioLezioni, UtentePolisportiva utente) {
		utente.comeIstruttore().segnaInAgendaLeLezioniDel(nuovoCalendarioLezioni);
	}

	public void aggiornaCalendarioManutentore(Calendario nuovoCalendario, UtentePolisportiva utente) {
		utente.comeManutentore().segnaInAgendaGliAppuntamentiDel(nuovoCalendario);
	}

	public UtentePolisportiva getManutentoreLibero(Calendario calendarioAppuntamento) {
		for (UtentePolisportiva utente : getListaUtentiByRuolo(TipoRuolo.MANUTENTORE)) {

			if (utente.comeManutentore().isLiberoNegliOrariDel(calendarioAppuntamento)) {
				return utente;
			}
		}
		return null;
	}

	public UtentePolisportiva getManutentoreLibero(Appuntamento appuntamento) {
		for (UtentePolisportiva utente : getListaUtentiByRuolo(TipoRuolo.MANUTENTORE)) {

			if (utente.comeManutentore().isLiberoPer(appuntamento)) {
				return utente;
			}
		}
		return null;
	}

	public List<UtentePolisportiva> filtraUtentiLiberiInBaseACalendarioSportivo(
			List<UtentePolisportiva> listaDaFiltrare, Calendario calendario) {
		List<UtentePolisportiva> listaFiltrata = new ArrayList<UtentePolisportiva>();
		for (UtentePolisportiva utente : listaDaFiltrare) {
			if (utente.is(TipoRuolo.SPORTIVO) && utente.comeSportivo().isLiberoNegliOrariDel(calendario)) {
				listaFiltrata.add(utente);
			}
		}
		return listaFiltrata;
	}

	public List<UtentePolisportiva> filtraUtentiLiberiInBaseACalendarioSportivo(
			List<UtentePolisportiva> listaDaFiltrare, LocalDateTime oraInizio, LocalDateTime oraFine) {
		List<UtentePolisportiva> listaFiltrata = new ArrayList<UtentePolisportiva>();
		for (UtentePolisportiva utente : listaDaFiltrare) {
			if (utente.is(TipoRuolo.SPORTIVO) && utente.comeSportivo().isLiberoTra(oraInizio, oraFine)) {
				listaFiltrata.add(utente);
			}
		}
		return listaFiltrata;
	}

}