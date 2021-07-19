package it.univaq.esc.controller.effettuaPrenotazione;

import it.univaq.esc.dtoObjects.*;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.*;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			CatalogoPrenotabili catalogoPrenotabili, RegistroSquadre registroSquadre, RegistroQuotePartecipazione registroQuotePartecipazione) {
		super(registroNotifiche, registroSport, registroImpianti, registroUtentiPolisportiva, registroAppuntamenti,
				registroPrenotazioni, catalogoPrenotabili, registroSquadre, registroQuotePartecipazione);
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
	public PrenotazioneDTO impostaPrenotazioneConDatiDellaFormPerRiepilogo(FormPrenotabile formDati,
			EffettuaPrenotazioneHandler controller) {
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
	}

	private AppuntamentoImpianto getAppuntamentoPerOrarioImpostatoUsandoForm(OrarioAppuntamentoDTO orario,
			FormPrenotabile formDati) {
		AppuntamentoImpianto appuntamento = (AppuntamentoImpianto) getElementiPrenotazioneFactory()
				.getAppuntamento(TipoPrenotazione.IMPIANTO.toString());

		DatiFormPerAppuntamento datiFormPerAppuntamento = getMapperFactory()
				.getAppuntamentoMapper(TipoPrenotazione.IMPIANTO.toString())
				.getDatiFormPerAppuntamentoUsando(formDati, orario);

		appuntamento.impostaDatiAppuntamentoDa(datiFormPerAppuntamento);
		appuntamento.setCalcolatoreCosto(getElementiPrenotazioneFactory().getCalcolatoreCosto());
		appuntamento.calcolaCosto();

		return appuntamento;
	}

	

	/**
	 * Metodo che aggiorna eventuali oggetti correlati alla prenotazione in atto,
	 * dopo che questa è stata confermata. Invocato in fase di conferma della
	 * prenotazione in atto.
	 */
	@Override
	public void aggiornaElementiLegatiAllaPrenotazioneConfermata(EffettuaPrenotazioneHandler controller) {
		List<AppuntamentoImpianto> appuntamentiPrenotazioneInAtto = (List<AppuntamentoImpianto>) (List<?>) controller
				.getPrenotazioneInAtto().getListaAppuntamenti();
		for (AppuntamentoImpianto nuovoAppuntamento : appuntamentiPrenotazioneInAtto) {
			confermaAppuntamentoConCreazioneQuotePartecipazioneSeRaggiuntoNumeroPartecipantiNecessario(nuovoAppuntamento);
			getRegistroAppuntamenti().aggiorna(nuovoAppuntamento);
			getRegistroQuotePartecipazione().salva(nuovoAppuntamento.getQuotePartecipazione());
			aggiornaCalendariImpiantoEUtentePrenotanteConIl(nuovoAppuntamento);
		}
		impostaNotifichePerGliUtentiInvitatiAllaPrenotazioneInAtto(appuntamentiPrenotazioneInAtto.get(0).getInvitati(),
				controller.getPrenotazioneInAtto());
	}
	
	private void confermaAppuntamentoConCreazioneQuotePartecipazioneSeRaggiuntoNumeroPartecipantiNecessario(
			AppuntamentoImpianto appuntamento) {
		if (appuntamento.haNumeroPartecipantiNecessarioPerConferma()) {
			appuntamento.confermaAppuntamento();
			appuntamento.creaQuotePartecipazionePerAppuntamento(getRegistroQuotePartecipazione().getUltimoIdQuote());
		}
	}

	private void aggiornaCalendariImpiantoEUtentePrenotanteConIl(AppuntamentoImpianto nuovoAppuntamento) {
		nuovoAppuntamento.siAggiungeAlCalendarioDelRelativoImpiantoPrenotato();
		nuovoAppuntamento.siAggiungeAlCalendarioDelloSportivoCheHaEffettuatoLaPrenotazioneRelativa();
	}

	private void impostaNotifichePerGliUtentiInvitatiAllaPrenotazioneInAtto(List<UtentePolisportiva> invitati,
			Prenotazione prenotazioneInAtto) {
		for (UtentePolisportiva invitato : invitati) {
			getRegistroNotifiche().impostaNotificaPerUtenteInvitatoAPrenotazioneImpianto(invitato, prenotazioneInAtto);
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
				.getAppuntamentiSottoscrivibiliSingoloUtentePerTipo(TipoPrenotazione.IMPIANTO,
						utentePerCuiCercareAppuntamentiSottoscrivibili)) {
			AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper(appuntamento.getTipoPrenotazione().toString())
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
				appuntamento.creaQuotePartecipazionePerAppuntamento(getRegistroQuotePartecipazione().getUltimoIdQuote());
			}
			if (partecipanteAggiunto) {
				nuovoPartecipante.comeSportivo().segnaInAgendaIl(appuntamento);
			}

			getRegistroAppuntamenti().aggiorna(appuntamento);
			getRegistroQuotePartecipazione().salva(appuntamento.getQuotePartecipazione());
			AppuntamentoDTO appuntamentoDTO = getMapperFactory()
					.getAppuntamentoMapper(appuntamento.getTipoPrenotazione().toString()).convertiInAppuntamentoDTO(appuntamento);
			return appuntamentoDTO;

		}
		return null;
	}

	@Override
	public Map<String, Object> getDatiOpzioniPerPrenotazioneInModalitaDirettore(
			EffettuaPrenotazioneHandler controller) {

		return null;
	}

}
