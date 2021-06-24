package it.univaq.esc.controller.effettuaPrenotazione;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.FormCreaCorso;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoSelezionato;
import it.univaq.esc.dtoObjects.IstruttoreSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamento;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoCorso;

import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;

import lombok.Getter;

import lombok.Setter;

@Component
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)

public class EffettuaPrenotazioneCorsoState extends EffettuaPrenotazioneState {

	/**
	 * Stato
	 */
	private EffettuaPrenotazioneLezioneState statoControllerLezioni;

	public EffettuaPrenotazioneCorsoState(RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtentiPolisportiva,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, EffettuaPrenotazioneLezioneState effettuaPrenotazioneLezioneState,
			RegistroSquadre registroSquadre) {

		super(registroNotifiche, registroSport, registroImpianti, registroUtentiPolisportiva, registroAppuntamenti,
				registroPrenotazioni, catalogoPrenotabili, registroSquadre);

	}

	@Override
	public Map<String, Object> getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(EffettuaPrenotazioneHandler controller) {
		Map<String, Object> mappaCorsiDisponibili = new HashMap<String, Object>();
		List<Prenotazione> corsiDisponibili = this.getRegistroPrenotazioni().filtraPrenotazioniPerTipo(
				this.getRegistroPrenotazioni().getPrenotazioniRegistrate(), TipiPrenotazione.CORSO.toString());

		List<PrenotazioneDTO> listaCorsi = new ArrayList<PrenotazioneDTO>();
		for (Prenotazione prenotazione : corsiDisponibili) {
			List<Appuntamento> appuntamentiPrenotazione = prenotazione.getListaAppuntamenti();

			PrenotazioneDTO prenotazioneDTO = getMapperFactory().getPrenotazioneMapper()
					.convertiCorsoInPrenotazioneDTO(prenotazione);

			listaCorsi.add(prenotazioneDTO);
		}

		mappaCorsiDisponibili.put("corsiDisponibili", listaCorsi);

		return mappaCorsiDisponibili;
	}

	@Override
	public PrenotazioneDTO impostaDatiPrenotazione(FormPrenotabile formDati, EffettuaPrenotazioneHandler controller) {

		FormCreaCorso formDatiCorso = (FormCreaCorso) formDati;

		/*
		 * Cicliamo sugli orari impostati in fase di compilazione, per creare e
		 * impostare tante specifiche di LEZIONI con relativi appuntamenti. Aggiungiamo
		 * le specifiche così impostate alla lista inizializzata prima. Infine associamo
		 * gli appuntamenti al controller.
		 */
		for (OrarioAppuntamento orario : formDatiCorso.getFormLezione().getOrariSelezionati()) {

			AppuntamentoCorso appuntamentoCorso = (AppuntamentoCorso) getElementiPrenotazioneFactory()
					.getAppuntamento(TipiPrenotazione.CORSO.toString());

			impostaValoriAppuntamento(formDatiCorso, controller, appuntamentoCorso, orario);

			controller.getPrenotazioneInAtto().aggiungi(appuntamentoCorso);
		}

		/*
		 * Convertiamo la lista degli invitati al corso in DTO, per passarla poi alla
		 * prenotazioneDTO che andremo a ritornare alla fine.
		 */
		List<UtentePolisportivaDTO> invitatiDTO = new ArrayList<UtentePolisportivaDTO>();
		for (UtentePolisportiva invitato : ((AppuntamentoCorso) controller.getPrenotazioneInAtto()
				.getListaAppuntamenti().get(0)).getInvitati()) {
			UtentePolisportivaDTO invitatoDTO = getMapperFactory().getUtenteMapper()
					.convertiInUtentePolisportivaDTO(invitato);
			invitatiDTO.add(invitatoDTO);
		}

		/*
		 * Creiamo e impostiamo la PrenotazioneDTO che andremo a ritornare.
		 */
		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper()
				.convertiCorsoInPrenotazioneDTO(controller.getPrenotazioneInAtto());

		return prenDTO;

	}

	public void impostaValoriAppuntamento(FormCreaCorso formDatiCorso, EffettuaPrenotazioneHandler controller,
			AppuntamentoCorso appuntamento, OrarioAppuntamento orario) {

		/*
		 * Creiamo l'oggetto descrizione del corso da passare alla specifiche delle
		 * lezioni e del corso. Nel caso dei corsi infatti avremo speciche di LEZIONI ma
		 * con descrizioni del corso anziche delle LEZIONI
		 */
		PrenotabileDescrizione descrizioneCorso = getCatalogoPrenotabili()
				.nuovoPrenotabile_avviaCreazione(
						this.getRegistroSport().getSportByNome(formDatiCorso.getSportSelezionato()),
						controller.getTipoPrenotazioneInAtto(), formDatiCorso.getNumeroMinimoPartecipanti(),
						formDatiCorso.getNumeroMassimoPartecipanti())
				.nuovoPrenotabile_impostaCostoUnaTantum(formDatiCorso.getCostoPerPartecipante())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

		
		ImpiantoSelezionato impiantoSelezionato = null;
		for(ImpiantoSelezionato impianto : formDatiCorso.getFormLezione().getImpianti()) {
			if(impianto.getIdSelezione() == orario.getId()) {
				impiantoSelezionato = impianto;
			}
		}
		appuntamento.setImpiantoPrenotato(getRegistroImpianti().getImpiantoByID(impiantoSelezionato.getIdImpianto()));
		
		
		
		// Creazione calcolatore che poi dovrà finire altrove
		CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
		calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());

		appuntamento.setDescrizioneEventoPrenotato(descrizioneCorso);
		appuntamento.setCalcolatoreCosto(calcolatoreCosto);

		LocalDateTime dataInizio = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraInizio());
		LocalDateTime dataFine = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraFine());

		appuntamento.setDataOraInizioAppuntamento(dataInizio);
		appuntamento.setDataOraFineAppuntamento(dataFine);

		appuntamento.calcolaCosto();

		for (String emailInvitato : formDatiCorso.getInvitatiCorso()) {
			appuntamento.aggiungi(getRegistroUtenti().trovaUtenteInBaseAllaSua(emailInvitato));
		}
		
		IstruttoreSelezionato istruttoreSelezionato = null;
		for(IstruttoreSelezionato istruttore : formDatiCorso.getFormLezione().getIstruttori()) {
			if(istruttore.getIdSelezione() == orario.getId()) {
				istruttoreSelezionato = istruttore;
			}
		}
		appuntamento.assegna(getRegistroUtenti().trovaUtenteInBaseAllaSua(istruttoreSelezionato.getIstruttore()));

	}

	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandler controller) {

		for (AppuntamentoCorso app : (List<AppuntamentoCorso>) (List<?>) controller.getPrenotazioneInAtto()
				.getListaAppuntamenti()) {
			Calendario calendarioDaUnire = new Calendario();
			calendarioDaUnire.aggiungiAppuntamento(app);
			getRegistroImpianti().aggiornaCalendarioImpianto(app.getImpiantoPrenotato(), calendarioDaUnire);
			getRegistroUtenti().aggiornaCalendarioIstruttore(calendarioDaUnire, app.getIstruttore());

		}
		/*
		 * Creiamo le notifiche relative agli invitati, impostandole con tutti i dati
		 * necessari.
		 */
		for (UtentePolisportiva invitato : ((AppuntamentoCorso) controller.getPrenotazioneInAtto()
				.getListaAppuntamenti().get(0)).getInvitati()) {

			NotificaService notifica = getElementiPrenotazioneFactory().getNotifica();
			notifica.setDestinatario(invitato);
			notifica.setEvento(controller.getPrenotazioneInAtto());
			notifica.setLetta(false);
			notifica.setMittente(controller.getSportivoPrenotante());

			getRegistroNotifiche().salvaNotifica(notifica);

		}

	}

	@Override
	public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
		Map<String, Object> mappaAggiornata = this.getStatoControllerLezioni().aggiornaOpzioniPrenotazione(dati);

		Map<String, String> orario = (Map<String, String>) dati.get("orario");
		LocalDateTime oraInizio = LocalDateTime.parse(orario.get("oraInizio"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
		LocalDateTime oraFine = LocalDateTime.parse(orario.get("oraFine"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));

		mappaAggiornata.put("sportiviInvitabili", getSportiviLiberiInBaseAOrario(oraInizio, oraFine));

		return mappaAggiornata;
	}

	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, Object identificativoPartecipante) {
		String emailPartecipante = (String) identificativoPartecipante;
		Prenotazione corsoPrenotazione = this.getRegistroPrenotazioni().getPrenotazioneById(idEvento);
		List<Appuntamento> listaAppuntamentiCorso = corsoPrenotazione.getListaAppuntamenti();

		UtentePolisportiva nuovoPartecipante = getRegistroUtenti().trovaUtenteInBaseAllaSua(emailPartecipante);
		boolean partecipanteAggiunto = false;
		for (Appuntamento appuntamento : listaAppuntamentiCorso) {
			partecipanteAggiunto = this.aggiungiPartecipanteECreaQuotePartecipazione(nuovoPartecipante, appuntamento);
		}
		Appuntamento appuntamentoPerCreazioneQuota = listaAppuntamentiCorso.get(0);

		List<QuotaPartecipazione> quotePartecipazione = getRegistroAppuntamenti()
				.creaQuotePartecipazionePerCorso(appuntamentoPerCreazioneQuota);

		if (partecipanteAggiunto && appuntamentoPerCreazioneQuota.isConfermato()) {
			for (Appuntamento appuntamento : listaAppuntamentiCorso) {
				for (QuotaPartecipazione quota : quotePartecipazione) {
					appuntamento.aggiungiQuotaPartecipazione(quota);
				}
			}
		}

		for (Appuntamento appuntamento : listaAppuntamentiCorso) {
			getRegistroAppuntamenti().aggiornaAppuntamento(appuntamento);
		}

		Calendario calendarioSportivo = new Calendario();
		for (Appuntamento appuntamento : listaAppuntamentiCorso) {
			calendarioSportivo.aggiungiAppuntamento(appuntamento);
		}
		getRegistroUtenti().aggiornaCalendarioSportivo(calendarioSportivo, nuovoPartecipante);

		PrenotazioneDTO prenotazioneDTO = getMapperFactory().getPrenotazioneMapper()
				.convertiCorsoInPrenotazioneDTO(corsoPrenotazione);
		return prenotazioneDTO;
	}

	@Override
	public Map<String, Object> getDatiOpzioniModalitaDirettore(EffettuaPrenotazioneHandler controller) {
		setStatoControllerLezioni((EffettuaPrenotazioneLezioneState) getElementiPrenotazioneFactory()
				.getStatoEffettuaPrenotazioneHandler(TipiPrenotazione.LEZIONE.toString()));
		getStatoControllerLezioni().setMapperFactory(getMapperFactory());

		Map<String, Object> mappaDati = this.getStatoControllerLezioni().getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(controller);
		mappaDati.put("sportiviInvitabili", this.getSportiviPolisportiva());

		return mappaDati;
	}

	@Override
	protected boolean aggiungiPartecipanteECreaQuotePartecipazione(Object utente, Appuntamento appuntamento) {
		boolean partecipanteAggiunto = appuntamento.aggiungiPartecipante(utente);

		appuntamento.confermaAppuntamento();

		return partecipanteAggiunto;
	}

}
