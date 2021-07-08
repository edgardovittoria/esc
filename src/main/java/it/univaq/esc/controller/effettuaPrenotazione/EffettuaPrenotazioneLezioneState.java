package it.univaq.esc.controller.effettuaPrenotazione;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoSelezionato;
import it.univaq.esc.dtoObjects.IstruttoreSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamentoDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpianto;
import it.univaq.esc.model.prenotazioni.AppuntamentoLezione;
import it.univaq.esc.model.prenotazioni.DatiFormPerAppuntamento;
import it.univaq.esc.model.prenotazioni.OrarioAppuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.notifiche.Notifica;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.notifiche.TipoNotifica;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;

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
			CatalogoPrenotabili catalogoPrenotabili, RegistroSquadre registroSquadre) {

		super(registroNotifiche, registroSport, registroImpianti, registroUtentiPolisportiva, registroAppuntamenti,
				registroPrenotazioni, catalogoPrenotabili, registroSquadre);
	}

	/**
	 * Metodo che restituisce i dati per popolare le opzioni di prenotazione, in
	 * fase di avvio di una nuova prenotazione, nel caso di una Lezione.
	 */
	@Override
	public Map<String, Object> getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(
			EffettuaPrenotazioneHandler controller) {
		Map<String, Object> mappaValori = new HashMap<String, Object>();
		mappaValori.put("sportPraticabili", this.getSportPraticabiliNellaPolisportivaInFormatoDTO());

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
				.getAppuntamento(TipiPrenotazione.LEZIONE.toString());
		DatiFormPerAppuntamento datiFormPerAppuntamento = getMapperFactory()
				.getAppuntamentoMapper(TipiPrenotazione.LEZIONE.toString())
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
			impostaNotificaPerIstruttoreAssociatoAdAppuntamento(nuovoAppuntamentoLezione.getIstruttore(), nuovoAppuntamentoLezione, controller.getSportivoPrenotante());
		}

	}
	
	private void confermaAppuntamentoConCreazioneQuotePartecipazioneSeRaggiuntoNumeroPartecipantiNecessario(
			AppuntamentoLezione appuntamento) {
		if (appuntamento.haNumeroPartecipantiNecessarioPerConferma()) {
			appuntamento.confermaAppuntamento();
			appuntamento.creaQuotePartecipazionePerAppuntamento();
		}
	}
	
	private void aggiornaCalendariImpiantoIstruttoreSportivoPrenotanteConIl(AppuntamentoLezione nuovoAppuntamentoLezione) {
		nuovoAppuntamentoLezione.siAggiungeAlCalendarioDelRelativoImpiantoPrenotato();
		nuovoAppuntamentoLezione.siAggiungeAlCalendarioDellIstruttoreRelativo();
		nuovoAppuntamentoLezione.siAggiungeAlCalendarioDelloSportivoCheHaEffettuatoLaPrenotazioneRelativa();
	}
	
	private void impostaNotificaPerIstruttoreAssociatoAdAppuntamento(UtentePolisportiva istruttore, AppuntamentoLezione appuntamentoLezione, UtentePolisportiva sportivoPrenotante) {
		NotificaService notifica = getElementiPrenotazioneFactory().getNotifica(new Notifica());
		notifica.setTipoNotifica(TipoNotifica.ISTRUTTORE_LEZIONE);
		notifica.setStatoNotifica(TipoNotifica.ISTRUTTORE_LEZIONE.toString());
		notifica.setDestinatario(istruttore);
		notifica.setEvento(appuntamentoLezione);
		notifica.setLetta(false);
		notifica.setMittente(sportivoPrenotante);
		getRegistroNotifiche().salvaNotifica(notifica);
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
