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
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamento;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoSingoliPartecipanti;
import it.univaq.esc.model.prenotazioni.PrenotazioneImpiantoSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.notifiche.Notifica;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.utility.BeanUtil;
import lombok.AllArgsConstructor;

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
	public PrenotazioneDTO impostaDatiPrenotazione(FormPrenotabile formDati,
			EffettuaPrenotazioneHandler controller) {

		for (OrarioAppuntamento orario : (List<OrarioAppuntamento>) formDati.getValoriForm()
				.get("listaOrariAppuntamenti")) {
			PrenotazioneImpiantoSpecs prenotazioneSpecs = (PrenotazioneImpiantoSpecs) getElementiPrenotazioneFactory()
					.getPrenotazioneSpecs(controller.getTipoPrenotazioneInAtto());
			controller.getPrenotazioneInAtto().aggiungiSpecifica(prenotazioneSpecs);

			impostaDatiPrenotazioneSpecs(prenotazioneSpecs, formDati, orario, controller);

			// ---------------------------------------------------------------------------------------

			AppuntamentoSingoliPartecipanti appuntamento = (AppuntamentoSingoliPartecipanti) getElementiPrenotazioneFactory()
					.getAppuntamento();
			impostaDatiAppuntamento(prenotazioneSpecs, formDati, appuntamento, orario, controller);

			controller.aggiungiAppuntamento(appuntamento);
		}

		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper().convertiInPrenotazioneDTO(
				controller.getPrenotazioneInAtto(), controller.getListaAppuntamentiPrenotazioneInAtto());

		return prenDTO;

	}

	private void impostaDatiPrenotazioneSpecs(PrenotazioneImpiantoSpecs prenotazioneSpecs, FormPrenotabile formDati,
			OrarioAppuntamento orario, EffettuaPrenotazioneHandler controller) {
		PrenotabileDescrizione descrizioneSpecifica = controller.getListinoPrezziDescrizioniPolisportiva()
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0)
								.getTipoPrenotazione(),
						getRegistroSport().getSportByNome((String) formDati.getValoriForm().get("sport")),
						formDati.getModalitaPrenotazione());

		prenotazioneSpecs.setPrenotazioneAssociata(controller.getPrenotazioneInAtto());

		Integer idImpianto = 0;
		for (ImpiantoSelezionato impianto : (List<ImpiantoSelezionato>) formDati.getValoriForm().get("impianti")) {
			if (impianto.getIdSelezione() == orario.getId()) {
				idImpianto = impianto.getIdImpianto();
			}
		}
		prenotazioneSpecs.setImpiantoPrenotato(getRegistroImpianti().getImpiantoByID(idImpianto));

		List<UtentePolisportivaAbstract> sportivi = new ArrayList<UtentePolisportivaAbstract>();
		for (String email : (List<String>) formDati.getValoriForm().get("invitati")) {
			sportivi.add(getRegistroUtenti().getUtenteByEmail(email));
		}
		prenotazioneSpecs.setInvitati(sportivi);
		prenotazioneSpecs.setSpecificaDescription(descrizioneSpecifica);
	}

	private void impostaDatiAppuntamento(PrenotazioneImpiantoSpecs prenotazioneSpecs, FormPrenotabile formDati,
			AppuntamentoSingoliPartecipanti appuntamento, OrarioAppuntamento orario,
			EffettuaPrenotazioneHandler controller) {
		// Creazione calcolatore che poi dovrà finire altrove
		CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
		calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());

		appuntamento.setPrenotazioneSpecsAppuntamento(prenotazioneSpecs);
		appuntamento.setCalcolatoreCosto(calcolatoreCosto);

		LocalDateTime dataInizio = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraInizio());
		LocalDateTime dataFine = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraFine());

		appuntamento.setDataOraInizioAppuntamento(dataInizio);
		appuntamento.setDataOraFineAppuntamento(dataFine);

		boolean pending = false;
		for (CheckboxPendingSelezionato checkbox : (List<CheckboxPendingSelezionato>) formDati.getValoriForm()
				.get("checkboxesPending")) {
			if (checkbox.getIdSelezione() == orario.getId()) {
				pending = checkbox.isPending();
			}
		}

		appuntamento.setPending(pending);

		appuntamento.calcolaCosto();

		this.aggiungiPartecipante(controller.getPrenotazioneInAtto().getSportivoPrenotante(), appuntamento);
	}

	/**
	 * Metodo che aggiorna eventuali oggetti correlati alla prenotazione in atto,
	 * dopo che questa è stata confermata. Invocato in fase di conferma della
	 * prenotazione in atto.
	 */
	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandler controller) {
		Calendario calendarioSportivo = new Calendario();
		for (Appuntamento app : controller.getListaAppuntamentiPrenotazioneInAtto()) {
			Calendario calendarioDaUnire = new Calendario();
			calendarioDaUnire.aggiungiAppuntamento(app);
			calendarioSportivo.aggiungiAppuntamento(app);
			getRegistroImpianti().aggiornaCalendarioImpianto((Impianto) controller.getPrenotazioneInAtto()
					.getSingolaSpecificaExtra("impianto", app.getPrenotazioneSpecsAppuntamento()), calendarioDaUnire);
		}
		
		
		getRegistroUtenti().aggiornaCalendarioSportivo(calendarioSportivo, controller.getSportivoPrenotante());
		
		

		/*
		 * Creiamo le notifiche relative agli invitati, impostandole con tutti i dati
		 * necessari.
		 */
		for (UtentePolisportivaAbstract invitato : (List<UtentePolisportivaAbstract>) controller.getPrenotazioneInAtto()
				.getListaSpecifichePrenotazione().get(0).getValoriSpecificheExtraPrenotazione().get("invitati")) {

			NotificaService notifica = getElementiPrenotazioneFactory().getNotifica();
			notifica.setDestinatario(invitato);
			notifica.setEvento(controller.getPrenotazioneInAtto());
			notifica.setLetta(false);
			notifica.setMittente(controller.getSportivoPrenotante());

			getRegistroNotifiche().salvaNotifica(notifica);

		}

	}

	/**
	 * Metodo che aggiorna i dati delle opzioni di prenotazione, sulla base di
	 * quelle già impostate. Invocato in fase di compilazione della prenotazione.
	 */
	@Override
	public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
		Map<String, Object> datiAggiornati = new HashMap<String, Object>();
		datiAggiornati.put("impiantiDisponibili", this.getImpiantiDTODisponibili(dati));

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
			UtentePolisportivaAbstract utentePerCuiCercareAppuntamentiSottoscrivibili) {
		List<AppuntamentoDTO> listaAppuntamentiDTO = new ArrayList<AppuntamentoDTO>();
		for (Appuntamento appuntamento : this.getRegistroAppuntamenti()
				.getAppuntamentiSottoscrivibiliSingoloUtentePerTipo(TipiPrenotazione.IMPIANTO.toString(),
						utentePerCuiCercareAppuntamentiSottoscrivibili)) {
			AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper().convertiInAppuntamentoDTO(appuntamento);
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
			boolean partecipanteAggiunto = this.aggiungiPartecipante(this.getRegistroUtenti().getUtenteByEmail(emailPartecipante), appuntamento);
			
			if(partecipanteAggiunto) {
				getRegistroUtenti().aggiornaCalendarioSportivo(appuntamento, getRegistroUtenti().getUtenteByEmail(emailPartecipante));
			}
			// this.getRegistroAppuntamenti().aggiornaAppuntamento(appuntamento);
			AppuntamentoDTO appuntamentoDTO = getMapperFactory().getAppuntamentoMapper().convertiInAppuntamentoDTO(appuntamento);
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
