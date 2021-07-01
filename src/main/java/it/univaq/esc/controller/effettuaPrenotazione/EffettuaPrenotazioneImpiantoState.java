package it.univaq.esc.controller.effettuaPrenotazione;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.OrarioAppuntamentoDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.notifiche.Notifica;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpianto;
import it.univaq.esc.model.prenotazioni.DatiFormPerAppuntamento;
import it.univaq.esc.model.prenotazioni.OrarioAppuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;

/**
 * Stato del controller EffettuaPrenotazioneHandler, che definisce la specifica
 * implementazione delle funzionalità correlate al tipo di prenotazione
 * IMPIANTO.
 * 
 * @author esc
 *
 */
@Component
@DependsOn("beanUtil")
public class EffettuaPrenotazioneImpiantoState extends EffettuaPrenotazioneState {

	/**
	 * Costruttore della classe EffettuaPrenotazioneImpiantoState
	 */
	public EffettuaPrenotazioneImpiantoState(RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtentiPolisportiva,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, RegistroSquadre registroSquadre) {
		super(registroNotifiche, registroSport, registroImpianti, registroUtentiPolisportiva, registroAppuntamenti,
				registroPrenotazioni, catalogoPrenotabili, registroSquadre);
	}

	/**
	 * Metodo che restituisce i dati per popolare le opzioni di prenotazione, in
	 * fase di avvio di una prenotazione Impianto.
	 */
	@Override
	public Map<String, Object> getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(
			EffettuaPrenotazioneHandler controller) {
		Map<String, Object> mappaValori = new HashMap<String, Object>();
		mappaValori.put("sportPraticabili", this.getSportPraticabiliNellaPolisportivaInFormatoDTO());
		mappaValori.put("sportiviInvitabili", this.getSportiviPolisportivaInFormatoDTO());
		mappaValori.put("appuntamentiSottoscrivibili",
				this.getAppuntamentiImpiantoSottoscrivibiliDaUtente(controller.getSportivoPrenotante()));

		return mappaValori;
	}

	/**
	 * Metodo che registra i dati impostati per la prenotazione nella prenotazione
	 * in atto del controller e nella lista di appuntamenti associata. Invocato in
	 * fase di riepilogo della prenotazione Impianto.
	 */
	@Override
	public PrenotazioneDTO impostaPrenotazioneConDatiDellaFormPerRiepilogo(FormPrenotabile formDati, EffettuaPrenotazioneHandler controller) {
		for (OrarioAppuntamentoDTO orario : formDati.getOrariSelezionati()) {
			impostaAppuntamentoRelativoAOrarioNellaPrenotazione(controller.getPrenotazioneInAtto(), orario, formDati);
		}
		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper()
				.convertiInPrenotazioneDTO(controller.getPrenotazioneInAtto());
		return prenDTO;

	}

	private void impostaAppuntamentoRelativoAOrarioNellaPrenotazione(Prenotazione prenotazioneInAtto,
			OrarioAppuntamentoDTO orario, FormPrenotabile formDati) {
		AppuntamentoImpianto appuntamentoImpianto = getAppuntamentoPerOrarioImpostatoUsandoForm(orario, formDati);
		prenotazioneInAtto.aggiungi(appuntamentoImpianto);
		appuntamentoImpianto.aggiungiPartecipante(prenotazioneInAtto.getSportivoPrenotante());
		confermaAppuntamentoConCreazioneQuoteParteciapzioneSeRaggiuntoNumeroPartecipantiNecessario(
				appuntamentoImpianto);

	}

	private AppuntamentoImpianto getAppuntamentoPerOrarioImpostatoUsandoForm(OrarioAppuntamentoDTO orario,
			FormPrenotabile formDati) {
		AppuntamentoImpianto appuntamento = (AppuntamentoImpianto) getElementiPrenotazioneFactory()
				.getAppuntamento(TipiPrenotazione.IMPIANTO.toString());

		DatiFormPerAppuntamento datiFormPerAppuntamento = getMapperFactory()
				.getAppuntamentoMapper(TipiPrenotazione.IMPIANTO.toString())
				.getDatiFormPerAppuntamentoUsando(formDati, orario);

		appuntamento.impostaDatiAppuntamentoDa(datiFormPerAppuntamento);
		appuntamento.setCalcolatoreCosto(getElementiPrenotazioneFactory().getCalcolatoreCosto());
		appuntamento.calcolaCosto();

		return appuntamento;
	}

	private void confermaAppuntamentoConCreazioneQuoteParteciapzioneSeRaggiuntoNumeroPartecipantiNecessario(
			AppuntamentoImpianto appuntamento) {
		if (appuntamento.haNumeroPartecipantiNecessarioPerConferma()) {
			appuntamento.confermaAppuntamento();
			appuntamento.creaQuotePartecipazionePerAppuntamento();
		}
	}

	/**
	 * Metodo che aggiorna eventuali oggetti correlati alla prenotazione in atto,
	 * dopo che questa è stata confermata. Invocato in fase di conferma della
	 * prenotazione in atto.
	 */
	@Override
	public void aggiornaElementiLegatiAllaPrenotazioneConfermata(EffettuaPrenotazioneHandler controller) {
		for (AppuntamentoImpianto app : (List<AppuntamentoImpianto>) (List<?>) controller.getPrenotazioneInAtto()
				.getListaAppuntamenti()) {
			app.siAggiungeAlCalendarioDelRelativoImpiantoPrenotato();
			app.siAggiungeAlCalendarioDelloSportivoCheHaEffettuatoLaPrenotazioneRelativa();
			impostaNotifichePerGliUtentiInvitatiAllAppuntamentoDellaPrenotazioneInAtto(app,
					controller.getPrenotazioneInAtto());
		}

	}

	private void impostaNotifichePerGliUtentiInvitatiAllAppuntamentoDellaPrenotazioneInAtto(
			AppuntamentoImpianto nuovoAppuntamento, Prenotazione prenotazioneInAtto) {

		for (UtentePolisportiva invitato : nuovoAppuntamento.getInvitati()) {
			NotificaService notifica = getElementiPrenotazioneFactory().getNotifica(new Notifica());
			notifica.setStatoNotifica(TipiPrenotazione.IMPIANTO.toString());
			notifica.setDestinatario(invitato);
			notifica.setEvento(prenotazioneInAtto);
			notifica.setLetta(false);
			notifica.setMittente(prenotazioneInAtto.getSportivoPrenotante());
			getRegistroNotifiche().salvaNotifica(notifica);
		}
	}

	/**
	 * Metodo che aggiorna i dati delle opzioni di prenotazione, sulla base di
	 * quelle già impostate. Invocato in fase di compilazione della prenotazione.
	 */
	@Override
	public Map<String, Object> getDatiOpzioniPrenotazioneAggiornatiInBaseAllaMappa(Map<String, Object> dati) {
		Map<String, Object> datiAggiornati = new HashMap<String, Object>();
		datiAggiornati.put("impiantiDisponibili", this.getListaDTOImpiantiPrenotabiliInBaseAMappa(dati));
		datiAggiornati.put("sportiviInvitabili", trovaSportiviLiberiInBaseA((Map<String, String>) dati.get("orario")));

		return datiAggiornati;

	}

	private List<UtentePolisportivaDTO> trovaSportiviLiberiInBaseA(Map<String, String> mappaOrario) {
		OrarioAppuntamento orarioAppuntamento = new OrarioAppuntamento();
		orarioAppuntamento.imposta(mappaOrario.get("oraInizio"), mappaOrario.get("oraFine"));

		return getSportiviDTOLiberiNell(orarioAppuntamento);
	}

	/**
	 * Metodo di utilità. Restituisce gli appuntamenti riferiti a preotazioni di
	 * tipo IMPIANTO, ai quali l'utente può partecipare. È possibile partecipare ad
	 * appuntamenti pending, e dei quali l'utente stesso non sia il creatore, perché
	 * risulta già in automatico come partecipante degli appuntamenti associati a
	 * prenotazioni create da lui.
	 * 
	 * @param utentePerCuiCercareAppuntamentiSottoscrivibili utente autenticato e
	 *                                                       per cui ricavare la
	 *                                                       lista di appuntamenti
	 *                                                       sottoscrivibili.
	 * @return la lista degli appuntamenti associati a prenotazioni di tipo
	 *         IMPIANTO, ai quali l'utente può partecipare.
	 */
	private List<AppuntamentoDTO> getAppuntamentiImpiantoSottoscrivibiliDaUtente(
			UtentePolisportiva utentePerCuiCercareAppuntamentiSottoscrivibili) {
		List<AppuntamentoDTO> listaAppuntamentiDTO = new ArrayList<AppuntamentoDTO>();
		for (Appuntamento appuntamento : this.getRegistroAppuntamenti()
				.getAppuntamentiSottoscrivibiliSingoloUtentePerTipo(TipiPrenotazione.IMPIANTO.toString(),
						utentePerCuiCercareAppuntamentiSottoscrivibili)) {
			AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper(appuntamento.getTipoPrenotazione())
					.convertiInAppuntamentoDTO(appuntamento);
			listaAppuntamentiDTO.add(appDTO);
		}
		return listaAppuntamentiDTO;
	}

	/**
	 * Metodo che gestisce la partecipazione dell'utente ad un appuntamento
	 * esistente associato ad una prenotazione di tipo IMPIANTO.
	 */
	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, Object emailPartecipante) {
		UtentePolisportiva nuovoPartecipante = getRegistroUtenti().trovaUtenteInBaseAllaSua((String) emailPartecipante);
		Appuntamento appuntamento = this.getRegistroAppuntamenti().getAppuntamentoById(idEvento);

		if (appuntamento != null) {
			boolean partecipanteAggiunto = appuntamento.aggiungiPartecipante(nuovoPartecipante);
			if (appuntamento.haNumeroPartecipantiNecessarioPerConferma()) {
				appuntamento.confermaAppuntamento();
				appuntamento.creaQuotePartecipazionePerAppuntamento();
			}
			if (partecipanteAggiunto) {
				nuovoPartecipante.comeSportivo().segnaInAgendaIl(appuntamento);
			}

			getRegistroAppuntamenti().aggiorna(appuntamento);
			AppuntamentoDTO appuntamentoDTO = getMapperFactory()
					.getAppuntamentoMapper(appuntamento.getTipoPrenotazione()).convertiInAppuntamentoDTO(appuntamento);
			return appuntamentoDTO;

		}
		return null;
	}

	@Override
	public Map<String, Object> getDatiOpzioniPerPrenotazioneInModalitaDirettore(EffettuaPrenotazioneHandler controller) {

		return null;
	}

}
