package it.univaq.esc.controller.effettuaPrenotazione;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.CheckboxPendingSelezionato;
import it.univaq.esc.dtoObjects.FormPrenotaImpianto;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.OrarioAppuntamento;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpianto;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
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
	public Map<String, Object> getDatiOpzioni(EffettuaPrenotazioneHandler controller) {
		Map<String, Object> mappaValori = new HashMap<String, Object>();
		mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
		mappaValori.put("sportiviInvitabili", this.getSportiviPolisportiva());
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
	public PrenotazioneDTO impostaDatiPrenotazione(FormPrenotabile formDati, EffettuaPrenotazioneHandler controller) {

		FormPrenotaImpianto formImpianto = (FormPrenotaImpianto)formDati;
		for (OrarioAppuntamento orario : formImpianto.getOrariSelezionati()) {
			
			

			// ---------------------------------------------------------------------------------------

			AppuntamentoImpianto appuntamento = (AppuntamentoImpianto) getElementiPrenotazioneFactory()
					.getAppuntamento(TipiPrenotazione.IMPIANTO.toString());
			impostaDatiAppuntamento(formImpianto, appuntamento, orario);

			controller.getPrenotazioneInAtto().aggiungi(appuntamento);
		}

		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper().convertiInPrenotazioneDTO(
				controller.getPrenotazioneInAtto());

		return prenDTO;

	}

	

	private void impostaDatiAppuntamento(FormPrenotaImpianto formDati,
			AppuntamentoImpianto appuntamento, OrarioAppuntamento orario) {
		PrenotabileDescrizione descrizioneSpecifica = getCatalogoPrenotabili()
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						TipiPrenotazione.IMPIANTO.toString(),
						getRegistroSport().getSportByNome(formDati.getSportSelezionato()),
						formDati.getModalitaPrenotazione());
		// Creazione calcolatore che poi dovrà finire altrove
		CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
		calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());

		appuntamento.setDescrizioneEventoPrenotato(descrizioneSpecifica);
		appuntamento.setCalcolatoreCosto(calcolatoreCosto);

		LocalDateTime dataInizio = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraInizio());
		LocalDateTime dataFine = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraFine());

		appuntamento.setDataOraInizioAppuntamento(dataInizio);
		appuntamento.setDataOraFineAppuntamento(dataFine);

		boolean pending = false;
		for (CheckboxPendingSelezionato checkbox : formDati.getCheckboxesPending()) {
			if (checkbox.getIdSelezione() == orario.getId()) {
				pending = checkbox.isPending();
			}
		}

		appuntamento.setPending(pending);

		appuntamento.calcolaCosto();

		this.aggiungiPartecipanteECreaQuotePartecipazione(dataFine, appuntamento);
	}

	/**
	 * Metodo che aggiorna eventuali oggetti correlati alla prenotazione in atto,
	 * dopo che questa è stata confermata. Invocato in fase di conferma della
	 * prenotazione in atto.
	 */
	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandler controller) {
		Calendario calendarioSportivo = new Calendario();
		for (AppuntamentoImpianto app : (List<AppuntamentoImpianto>)(List<?>)controller.getPrenotazioneInAtto().getListaAppuntamenti()) {
			Calendario calendarioDaUnire = new Calendario();
			calendarioDaUnire.aggiungiAppuntamento(app);
			calendarioSportivo.aggiungiAppuntamento(app);
			getRegistroImpianti().aggiornaCalendarioImpianto(app.getImpiantoPrenotato(), calendarioDaUnire);
			
			for (UtentePolisportiva invitato : app.getInvitati()) {

				NotificaService notifica = getElementiPrenotazioneFactory().getNotifica();
				notifica.setDestinatario(invitato);
				notifica.setEvento(controller.getPrenotazioneInAtto());
				notifica.setLetta(false);
				notifica.setMittente(controller.getSportivoPrenotante());

				getRegistroNotifiche().salvaNotifica(notifica);

			}
		}

		getRegistroUtenti().aggiornaCalendarioSportivo(calendarioSportivo, controller.getSportivoPrenotante());

		/*
		 * Creiamo le notifiche relative agli invitati, impostandole con tutti i dati
		 * necessari.
		 */
		

	}

	/**
	 * Metodo che aggiorna i dati delle opzioni di prenotazione, sulla base di
	 * quelle già impostate. Invocato in fase di compilazione della prenotazione.
	 */
	@Override
	public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
		Map<String, Object> datiAggiornati = new HashMap<String, Object>();
		datiAggiornati.put("impiantiDisponibili", this.getImpiantiPrenotabiliInBaseA(dati));

		Map<String, String> orario = (Map<String, String>) dati.get("orario");
		LocalDateTime oraInizio = LocalDateTime.parse(orario.get("oraInizio"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
		LocalDateTime oraFine = LocalDateTime.parse(orario.get("oraFine"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));

		datiAggiornati.put("sportiviInvitabili", getSportiviLiberiInBaseAOrario(oraInizio, oraFine));

		return datiAggiornati;

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
			AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper(appuntamento.getTipoPrenotazione()).convertiInAppuntamentoDTO(appuntamento);
			listaAppuntamentiDTO.add(appDTO);
		}
		return listaAppuntamentiDTO;
	}

	/**
	 * Metodo che gestisce la partecipazione dell'utente ad un appuntamento
	 * esistente associato ad una prenotazione di tipo IMPIANTO.
	 */
	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, Object identificativoPartecipante) {
		String emailPartecipante = (String) identificativoPartecipante;
		Appuntamento appuntamento = this.getRegistroAppuntamenti().getAppuntamentoById(idEvento);
		if (appuntamento != null) {
			boolean partecipanteAggiunto = this
					.aggiungiPartecipanteECreaQuotePartecipazione(this.getRegistroUtenti().getUtenteByEmail(emailPartecipante), appuntamento);

			if (partecipanteAggiunto) {
				getRegistroUtenti().aggiornaCalendarioSportivo(appuntamento,
						getRegistroUtenti().getUtenteByEmail(emailPartecipante));
			}
			// this.getRegistroAppuntamenti().aggiornaAppuntamento(appuntamento);
			AppuntamentoDTO appuntamentoDTO = getMapperFactory().getAppuntamentoMapper(appuntamento.getTipoPrenotazione())
					.convertiInAppuntamentoDTO(appuntamento);
			return appuntamentoDTO;
		}
		return null;
	}

	@Override
	public Map<String, Object> getDatiOpzioniModalitaDirettore(EffettuaPrenotazioneHandler controller) {
		// TODO Auto-generated method stub
		return null;
	}

}
