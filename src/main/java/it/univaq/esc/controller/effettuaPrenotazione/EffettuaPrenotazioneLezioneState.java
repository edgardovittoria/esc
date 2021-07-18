package it.univaq.esc.controller.effettuaPrenotazione;

import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.OrarioAppuntamentoDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.*;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stato del controller EffettuaPrenotazioneHandler che gestisce la specifica
 * implementazione relativa al tipo di prenotazione LEZIONE. Fornisce la
 * specifica implementazione per ricavare i dati per popolare, aggiornare e
 * registrare le opzioni di compilazione nel caso della prenotazione di una
 * nuova Lezione, o della partecipazione ad una già esistente.
 * 
 * @author esc
 *
 */
@Component
public class EffettuaPrenotazioneLezioneState extends EffettuaPrenotazioneState {

	public EffettuaPrenotazioneLezioneState(RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtentiPolisportiva,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, RegistroSquadre registroSquadre, RegistroQuotePartecipazione registroQuotePartecipazione) {

		super(registroNotifiche, registroSport, registroImpianti, registroUtentiPolisportiva, registroAppuntamenti,
				registroPrenotazioni, catalogoPrenotabili, registroSquadre, registroQuotePartecipazione);
	}

	/**
	 * Metodo che restituisce i dati per popolare le opzioni di prenotazione, in
	 * fase di avvio di una nuova prenotazione, nel caso di una Lezione.
	 */
	@Override
	public Map<String, Object> getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(
			EffettuaPrenotazioneHandler controller) {
		Map<String, Object> mappaValori = new HashMap<String, Object>();
		mappaValori.put("sportPraticabili", getListaInFormatoDTODegliSportCheHannoIstruttori());

		return mappaValori;
	}

	/**
	 * Metodo che imposta i dati della prenotazione, passati tramite una form DTO,
	 * nella controparte software lato server.
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
		AppuntamentoLezione appuntamentoLezione = getAppuntamentoPerOrarioImpostatoUsandoForm(orario, formDati);
		prenotazioneInAtto.aggiungi(appuntamentoLezione);
		appuntamentoLezione.aggiungiPartecipante(prenotazioneInAtto.getSportivoPrenotante());
	}

	private AppuntamentoLezione getAppuntamentoPerOrarioImpostatoUsandoForm(OrarioAppuntamentoDTO orario,
			FormPrenotabile formDati) {
		AppuntamentoLezione appuntamento = (AppuntamentoLezione) getElementiPrenotazioneFactory()
				.getAppuntamento(TipoPrenotazione.LEZIONE.toString());
		DatiFormPerAppuntamento datiFormPerAppuntamento = getMapperFactory()
				.getAppuntamentoMapper(TipoPrenotazione.LEZIONE.toString())
				.getDatiFormPerAppuntamentoUsando(formDati, orario);
		appuntamento.impostaDatiAppuntamentoDa(datiFormPerAppuntamento);
		appuntamento.setCalcolatoreCosto(getElementiPrenotazioneFactory().getCalcolatoreCosto());
		appuntamento.calcolaCosto();
		return appuntamento;
	}
	
	
	

	/**
	 * Metodo che aggiorna eventuali oggetti correlati con la prenotazione che si
	 * sta gestendo, u na volta che questa è stata confermata.
	 */
	@Override
	public void aggiornaElementiLegatiAllaPrenotazioneConfermata(EffettuaPrenotazioneHandler controller) {
		List<AppuntamentoLezione> listaAppuntamentiLezioni = (List<AppuntamentoLezione>) (List<?>) controller.getPrenotazioneInAtto().getListaAppuntamenti();
		for (AppuntamentoLezione nuovoAppuntamentoLezione : listaAppuntamentiLezioni) {
			confermaAppuntamentoConCreazioneQuotePartecipazioneSeRaggiuntoNumeroPartecipantiNecessario(nuovoAppuntamentoLezione);
			aggiornaCalendariImpiantoIstruttoreSportivoPrenotanteConIl(nuovoAppuntamentoLezione);
			getRegistroNotifiche().impostaNotificaPerIstruttoreAssociatoANuovaLezione(nuovoAppuntamentoLezione.getIstruttore(), nuovoAppuntamentoLezione);
		}

	}
	
	private void confermaAppuntamentoConCreazioneQuotePartecipazioneSeRaggiuntoNumeroPartecipantiNecessario(
			AppuntamentoLezione appuntamento) {
		if (appuntamento.haNumeroPartecipantiNecessarioPerConferma()) {
			appuntamento.confermaAppuntamento();
			appuntamento.creaQuotePartecipazionePerAppuntamento(getRegistroQuotePartecipazione().getUltimoIdQuote());
		}
	}
	
	private void aggiornaCalendariImpiantoIstruttoreSportivoPrenotanteConIl(AppuntamentoLezione nuovoAppuntamentoLezione) {
		nuovoAppuntamentoLezione.siAggiungeAlCalendarioDelRelativoImpiantoPrenotato();
		nuovoAppuntamentoLezione.siAggiungeAlCalendarioDellIstruttoreRelativo();
		nuovoAppuntamentoLezione.siAggiungeAlCalendarioDelloSportivoCheHaEffettuatoLaPrenotazioneRelativa();
	}
	
	/**
	 * Metodo che aggiorna i dati per popolare le opzioni di prenotazione, nel caso
	 * della prenotazione di una Lezione.
	 */
	@Override
	public Map<String, Object> getDatiOpzioniPrenotazioneAggiornatiInBaseAllaMappa(Map<String, Object> dati) {
		Map<String, Object> mappaDatiAggiornati = new HashMap<String, Object>();
		mappaDatiAggiornati.put("impiantiDisponibili", this.getListaDTOImpiantiPrenotabiliInBaseAMappa(dati));
		mappaDatiAggiornati.put("istruttoriDisponibili", this.getIstruttoriDTODisponibili(dati));
		return mappaDatiAggiornati;
	}

	/**
	 * Metodo che gestisce la partecipazione di un utente ad una lezione esistente.
	 */
	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, Object identificativoPartecipante) {

		return null;
	}

	@Override
	public Map<String, Object> getDatiOpzioniPerPrenotazioneInModalitaDirettore(
			EffettuaPrenotazioneHandler controller) {

		return null;
	}

}
