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
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.NotificaSquadraService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoSingoliPartecipanti;
import it.univaq.esc.model.prenotazioni.AppuntamentoSquadra;
import it.univaq.esc.model.prenotazioni.PrenotazioneImpiantoSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneImpiantoSquadraSpecs;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.utility.BeanUtil;

@Component
@DependsOn("beanUtil")
public class EffettuaPrenotazioneImpiantoSquadraState extends EffettuaPrenotazioneState {

	public EffettuaPrenotazioneImpiantoSquadraState(RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtenti,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, RegistroSquadre registroSquadre) {
		super(registroNotifiche, registroSport, registroImpianti, registroUtenti, registroAppuntamenti,
				registroPrenotazioni, catalogoPrenotabili, registroSquadre);

	}

	@Override
	public Map<String, Object> getDatiOpzioni(EffettuaPrenotazioneHandler controller) {
		Map<String, Object> mappaValori = new HashMap<String, Object>();
		mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
		mappaValori.put("squadreInvitabili", getRegistroSquadre().getListaSquadre());
		mappaValori.put("appuntamentiSottoscrivibiliSquadra", this.getAppuntamentiImpiantoSottoscrivibiliDaSquadra(
				getRegistroSquadre().getSquadraById(controller.getIdSquadraPrenotante())));

		return mappaValori;
	}

	@Override
	public PrenotazioneDTO impostaDatiPrenotazione(FormPrenotabile formDati,
			EffettuaPrenotazioneHandler controller) {

		for (OrarioAppuntamento orario : (List<OrarioAppuntamento>) formDati.getValoriForm()
				.get("listaOrariAppuntamenti")) {
			PrenotazioneImpiantoSquadraSpecs prenotazioneSpecs = (PrenotazioneImpiantoSquadraSpecs) getElementiPrenotazioneFactory()
					.getPrenotazioneSpecs(controller.getTipoPrenotazioneInAtto());
			controller.getPrenotazioneInAtto().aggiungiSpecifica(prenotazioneSpecs);

			impostaDatiPrenotazioneSpecs(prenotazioneSpecs, formDati, orario, controller);

			// ---------------------------------------------------------------------------------------

			AppuntamentoSquadra appuntamento = (AppuntamentoSquadra) getElementiPrenotazioneFactory().getAppuntamento();
			impostaDatiAppuntamento(prenotazioneSpecs, formDati, appuntamento, orario, controller);

			controller.aggiungiAppuntamento(appuntamento);
		}

		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper().convertiInPrenotazioneDTO(
				controller.getPrenotazioneInAtto(), controller.getListaAppuntamentiPrenotazioneInAtto());

		return prenDTO;
	}

	private void impostaDatiPrenotazioneSpecs(PrenotazioneImpiantoSquadraSpecs prenotazioneSpecs,
			FormPrenotabile formDati, OrarioAppuntamento orario, EffettuaPrenotazioneHandler controller) {
		PrenotabileDescrizione descrizioneSpecifica = getCatalogoPrenotabili()
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

		List<Squadra> squadreInvitate = new ArrayList<Squadra>();
		for (Integer idSquadra : (List<Integer>) formDati.getValoriForm().get("squadreInvitate")) {
			squadreInvitate.add(getRegistroSquadre().getSquadraById(idSquadra));
		}
		prenotazioneSpecs.setSquadreInvitate(squadreInvitate);
		prenotazioneSpecs.setSpecificaDescription(descrizioneSpecifica);
	}

	private void impostaDatiAppuntamento(PrenotazioneImpiantoSquadraSpecs prenotazioneSpecs, FormPrenotabile formDati,
			AppuntamentoSquadra appuntamento, OrarioAppuntamento orario, EffettuaPrenotazioneHandler controller) {
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

		Squadra partecipante = getRegistroSquadre().getSquadraById(controller.getIdSquadraPrenotante());
		this.aggiungiPartecipante(partecipante, appuntamento);

	}

	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandler controller) {
		for (Appuntamento app : controller.getListaAppuntamentiPrenotazioneInAtto()) {
			Calendario calendarioDaUnire = new Calendario();
			calendarioDaUnire.aggiungiAppuntamento(app);
			getRegistroImpianti().aggiornaCalendarioImpianto((Impianto) controller.getPrenotazioneInAtto()
					.getSingolaSpecificaExtra("impianto", app.getPrenotazioneSpecsAppuntamento()), calendarioDaUnire);
			getRegistroSquadre().aggiornaCalendarioSquadra(getRegistroSquadre().getSquadraById(controller.getIdSquadraPrenotante()), calendarioDaUnire);
		}
		
		

		/*
		 * Creiamo le notifiche relative alle squadre invitate, creandone una per ogni
		 * membro di ogni squadra.
		 */
		for (Squadra invitato : (List<Squadra>) controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione()
				.get(0).getValoriSpecificheExtraPrenotazione().get("invitati")) {

			for (UtentePolisportivaAbstract amministratore : invitato.getAmministratori()) {
				NotificaSquadraService notifica = (NotificaSquadraService) getElementiPrenotazioneFactory()
						.getNotifica();
				notifica.setDestinatario(amministratore);
				notifica.setEvento(controller.getPrenotazioneInAtto());
				notifica.setLetta(false);
				notifica.setMittente(controller.getSportivoPrenotante());
				notifica.setSquadraDelDestinatario(invitato);
				notifica.setSquadraDelMittente(
						getRegistroSquadre().getSquadraById(controller.getIdSquadraPrenotante()));

				getRegistroNotifiche().salvaNotifica(notifica);

			}
		}

	}

	@Override
	public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
		Map<String, Object> datiAggiornati = new HashMap<String, Object>();
		datiAggiornati.put("impiantiDisponibili", this.getImpiantiDTODisponibili(dati));

		Map<String, String> orario = (Map<String, String>) dati.get("orario");
		LocalDateTime oraInizio = LocalDateTime.parse(orario.get("oraInizio"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
		LocalDateTime oraFine = LocalDateTime.parse(orario.get("oraFine"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));

		datiAggiornati.put("squadreInvitabili",
				getRegistroSquadre().filtraSquadrePerSport(
						getRegistroSport().getSportByNome((String) dati.get("sport")),
						getRegistroSquadre().getListaSquadreLiberePerOrarioAppuntamento(oraInizio, oraFine)));

		return datiAggiornati;
	}

	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, Object identificativoPartecipante) {
		Integer idSquadra = (Integer) identificativoPartecipante;
		Squadra squadraPartecipante = getRegistroSquadre().getSquadraById(idSquadra);
		Appuntamento appuntamento = this.getRegistroAppuntamenti().getAppuntamentoById(idEvento);
		if (appuntamento != null) {
			boolean partecipanteAggiunto = this.aggiungiPartecipante(squadraPartecipante, appuntamento);
			
			if(partecipanteAggiunto) {
				getRegistroSquadre().aggiornaCalendarioSquadra(squadraPartecipante, appuntamento);
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

	private List<AppuntamentoDTO> getAppuntamentiImpiantoSottoscrivibiliDaSquadra(
			Squadra squadraPerCuiCercareAppuntamentiSottoscrivibili) {
		List<AppuntamentoDTO> listaAppuntamentiDTO = new ArrayList<AppuntamentoDTO>();
		for (Appuntamento appuntamento : this.getRegistroAppuntamenti().getAppuntamentiSottoscrivibiliSquadraPerTipo(
				TipiPrenotazione.IMPIANTO.toString(), squadraPerCuiCercareAppuntamentiSottoscrivibili)) {
			AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper().convertiInAppuntamentoDTO(appuntamento);
			listaAppuntamentiDTO.add(appDTO);
		}
		return listaAppuntamentiDTO;
	}

	@Override
	protected boolean aggiungiPartecipante(Object squadra, Appuntamento appuntamento) {
		boolean partecipanteAggiunto = false;
		if (appuntamento.getPartecipantiAppuntamento().size() < appuntamento.getNumeroPartecipantiMassimo()) {
			appuntamento.aggiungiPartecipante(squadra);
			partecipanteAggiunto = true;
			if (appuntamento.getPartecipantiAppuntamento().size() >= appuntamento
					.getSogliaMinimaPartecipantiPerConferma()) {
				appuntamento.confermaAppuntamento();

			}
			this.getRegistroAppuntamenti().aggiornaAppuntamento(appuntamento);
		}
		return partecipanteAggiunto;
	}

}
