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
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.FactorySpecifichePrenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.costi.CatalogoPrenotabili;
import it.univaq.esc.model.costi.ModalitaPrenotazione;
import it.univaq.esc.model.costi.PrenotabileDescrizione;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.notifiche.Notifica;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
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
	public EffettuaPrenotazioneImpiantoState(
			RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtentiPolisportiva,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, RegistroSquadre registroSquadre) {
		
		super(registroNotifiche, registroSport, registroImpianti, registroUtentiPolisportiva, registroAppuntamenti, registroPrenotazioni, catalogoPrenotabili, registroSquadre);
	}

	/**
	 * Blocco static. La prima volta che viene caricata la classe, la registra nella
	 * FactoryStatoEffettuaPrenotazione
	 */
	static {
		FactoryStatoEffettuaPrenotazione.registra(TipiPrenotazione.IMPIANTO.toString(),
				EffettuaPrenotazioneImpiantoState.class);
	}

	/**
	 * Metodo che restituisce i dati per popolare le opzioni di prenotazione, in
	 * fase di avvio di una prenotazione Impianto.
	 */
	@Override
	public Map<String, Object> getDatiOpzioni(EffettuaPrenotazioneHandlerRest controller) {
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
			EffettuaPrenotazioneHandlerRest controller) {
		for (int i = 0; i < ((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti"))
				.size(); i++) {
			PrenotazioneSpecs prenotazioneSpecs = FactorySpecifichePrenotazione
					.getSpecifichePrenotazione(controller.getTipoPrenotazioneInAtto());
			controller.getPrenotazioneInAtto().aggiungiSpecifica(prenotazioneSpecs);
			prenotazioneSpecs.setPrenotazioneAssociata(controller.getPrenotazioneInAtto());

			// Creazione calcolatore che poi dovrà finire altrove
			CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
			calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());
			// ---------------------------------------------------------------------------------------

			
			
			Appuntamento appuntamento = getRegistroAppuntamenti().creaNuovoAppuntamentoPerModalitaPrenotazione(formDati.getModalitaPrenotazione());
			appuntamento.setPrenotazioneSpecsAppuntamento(prenotazioneSpecs);
			appuntamento.setCalcolatoreCosto(calcolatoreCosto);

			controller.aggiungiAppuntamento(appuntamento);
		}

		PrenotabileDescrizione descrizioneSpecifica = controller.getListinoPrezziDescrizioniPolisportiva().getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(controller.getPrenotazioneInAtto()
							.getListaSpecifichePrenotazione().get(0).getTipoPrenotazione(), getRegistroSport().getSportByNome((String)formDati.getValoriForm().get("sport")), formDati.getModalitaPrenotazione());
//		for (PrenotabileDescrizione desc : controller.getListinoPrezziDescrizioniPolisportiva()
//				.getCatalogoPrenotabili()) {
//			if (desc.getSportAssociato().getNome().equals((String) formDati.getValoriForm().get("sport"))
//					&& desc.getTipoPrenotazione().equals(controller.getPrenotazioneInAtto()
//							.getListaSpecifichePrenotazione().get(0).getTipoPrenotazione())) {
//				descrizioneSpecifica = desc;
//			}
//		}

		List<UtentePolisportivaAbstract> sportivi = new ArrayList<UtentePolisportivaAbstract>();
		for (String email : (List<String>) formDati.getValoriForm().get("invitati")) {
			sportivi.add(getRegistroUtenti().getUtenteByEmail(email));
		}

		for (PrenotazioneSpecs spec : controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione()) {
			spec.setSpecificaDescription(descrizioneSpecifica);
		}

		for (OrarioAppuntamento orario : (List<OrarioAppuntamento>) formDati.getValoriForm()
				.get("listaOrariAppuntamenti")) {
			// Calendario calendarioPrenotazione = new Calendario();
			LocalDateTime dataInizio = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraInizio());
			LocalDateTime dataFine = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraFine());

			// calendarioPrenotazione.aggiungiAppuntamento(dataInizio, dataFine ,
			// controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));
			// controller.getPrenotazioneInAtto().setCalendarioSpecifica(calendarioPrenotazione,
			// controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));

			Appuntamento appuntamentoDaImpostare = controller.getListaAppuntamentiPrenotazioneInAtto()
					.get(((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti"))
							.indexOf(orario));
			appuntamentoDaImpostare.setDataOraInizioAppuntamento(dataInizio);
			appuntamentoDaImpostare.setDataOraFineAppuntamento(dataFine);

			boolean pending = false;
			for (CheckboxPendingSelezionato checkbox : (List<CheckboxPendingSelezionato>) formDati.getValoriForm()
					.get("checkboxesPending")) {
				if (checkbox.getIdSelezione() == orario.getId()) {
					pending = checkbox.isPending();
				}
			}

			appuntamentoDaImpostare.setPending(pending);

			HashMap<String, Object> mappaValori = new HashMap<String, Object>();
			mappaValori.put("invitati", sportivi);

			Integer idImpianto = 0;
			for (ImpiantoSelezionato impianto : (List<ImpiantoSelezionato>) formDati.getValoriForm().get("impianti")) {
				if (impianto.getIdSelezione() == orario.getId()) {
					idImpianto = impianto.getIdImpianto();
				}
			}

			mappaValori.put("impianto", getRegistroImpianti().getImpiantoByID(idImpianto));

			controller.getListaAppuntamentiPrenotazioneInAtto()
					.get(((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti"))
							.indexOf(orario))
					.getPrenotazioneSpecsAppuntamento().impostaValoriSpecificheExtraPrenotazione(mappaValori);
			controller.getListaAppuntamentiPrenotazioneInAtto().get(
					((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti")).indexOf(orario))
					.calcolaCosto();
		}

		for (Appuntamento appuntamento : controller.getListaAppuntamentiPrenotazioneInAtto()) {

			this.aggiungiPartecipante(controller.getPrenotazioneInAtto().getSportivoPrenotante(), appuntamento);
		}

		PrenotazioneDTO prenDTO = new PrenotazioneDTO();
		Map<String, Object> mappa = new HashMap<String, Object>();
		mappa.put("prenotazione", controller.getPrenotazioneInAtto());
		mappa.put("appuntamentiPrenotazione", controller.getListaAppuntamentiPrenotazioneInAtto());
		prenDTO.impostaValoriDTO(mappa);

		return prenDTO;

	}

	/**
	 * Metodo che aggiorna eventuali oggetti correlati alla prenotazione in atto,
	 * dopo che questa è stata confermata. Invocato in fase di conferma della
	 * prenotazione in atto.
	 */
	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandlerRest controller) {
		for (Appuntamento app : controller.getListaAppuntamentiPrenotazioneInAtto()) {
			Calendario calendarioDaUnire = new Calendario();
			calendarioDaUnire.aggiungiAppuntamento(app);
			getRegistroImpianti().aggiornaCalendarioImpianto((Impianto) controller.getPrenotazioneInAtto()
					.getSingolaSpecificaExtra("impianto", app.getPrenotazioneSpecsAppuntamento()), calendarioDaUnire);
		}

		/*
		 * Creiamo le notifiche relative agli invitati, impostandole con tutti i dati
		 * necessari.
		 */
		for (UtentePolisportivaAbstract invitato : (List<UtentePolisportivaAbstract>) controller.getPrenotazioneInAtto()
				.getListaSpecifichePrenotazione().get(0).getValoriSpecificheExtraPrenotazione().get("invitati")) {

			NotificaService notifica = BeanUtil.getBean(NotificaService.class);
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
		
		
		Map<String, String> orario = (Map<String, String>)dati.get("orario");
		LocalDateTime oraInizio = LocalDateTime.parse(orario.get("oraInizio"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
		LocalDateTime oraFine = LocalDateTime.parse(orario.get("oraFine"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
		
		if(((String)dati.get("modalitaPrenotazione")).equals(ModalitaPrenotazione.SQUADRA.toString())){
			datiAggiornati.put("squadreInvitabili", getRegistroSquadre().getListaSquadreLiberePerOrarioAppuntamento(oraInizio, oraFine));
		}
		else {
			datiAggiornati.put("sportiviInvitabili", getSportiviLiberiInBaseAOrario(oraInizio, oraFine));
		}
		
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
		for (Appuntamento appuntamento : this.getRegistroAppuntamenti().getAppuntamentiSottoscrivibiliPerTipo(
				TipiPrenotazione.IMPIANTO.toString(), utentePerCuiCercareAppuntamentiSottoscrivibili)) {
			AppuntamentoDTO appDTO = new AppuntamentoDTO();
			appDTO.impostaValoriDTO(appuntamento);
			listaAppuntamentiDTO.add(appDTO);
		}
		return listaAppuntamentiDTO;
	}

	/**
	 * Metodo che gestisce la partecipazione dell'utente ad un appuntamento
	 * esistente associato ad una prenotazione di tipo IMPIANTO.
	 */
	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, String emailPartecipante) {
		Appuntamento appuntamento = this.getRegistroAppuntamenti().getAppuntamentoById(idEvento);
		if (appuntamento != null) {
			this.aggiungiPartecipante(this.getRegistroUtenti().getUtenteByEmail(emailPartecipante), appuntamento);
			//this.getRegistroAppuntamenti().aggiornaAppuntamento(appuntamento);
			AppuntamentoDTO appuntamentoDTO = new AppuntamentoDTO();
			appuntamentoDTO.impostaValoriDTO(appuntamento);
			return appuntamentoDTO;
		}
		return null;
	}

	@Override
	public Map<String, Object> getDatiOpzioniModalitaDirettore(EffettuaPrenotazioneHandlerRest controller) {
		// TODO Auto-generated method stub
		return null;
	}

}
