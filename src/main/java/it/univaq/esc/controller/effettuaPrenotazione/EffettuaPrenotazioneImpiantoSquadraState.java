package it.univaq.esc.controller.effettuaPrenotazione;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.CheckboxPendingSelezionato;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamentoDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.SquadraDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.notifiche.NotificaSquadraService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoSquadra;
import it.univaq.esc.model.prenotazioni.OrarioAppuntamento;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportiva;

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
	public Map<String, Object> getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(
			EffettuaPrenotazioneHandler controller) {
		Map<String, Object> mappaValori = new HashMap<String, Object>();
		mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
		mappaValori.put("squadreInvitabili", getRegistroSquadre().getListaSquadre());
		mappaValori.put("appuntamentiSottoscrivibiliSquadra", this.getAppuntamentiImpiantoSottoscrivibiliDaSquadra(
				getRegistroSquadre().getSquadraById(controller.getIdSquadraPrenotante())));

		return mappaValori;
	}

	@Override
	public PrenotazioneDTO impostaDatiPrenotazione(FormPrenotabile formDati, EffettuaPrenotazioneHandler controller) {

		for (OrarioAppuntamentoDTO orario : formDati.getOrariSelezionati()) {

			// ---------------------------------------------------------------------------------------

			AppuntamentoSquadra appuntamento = (AppuntamentoSquadra) getElementiPrenotazioneFactory()
					.getAppuntamento(TipiPrenotazione.IMPIANTO.toString());
			impostaDatiAppuntamento(formDati, appuntamento, orario, controller);

			controller.getPrenotazioneInAtto().aggiungi(appuntamento);
		}

		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper()
				.convertiInPrenotazioneDTO(controller.getPrenotazioneInAtto());

		return prenDTO;
	}

	private void impostaDatiAppuntamento(FormPrenotabile formDati, AppuntamentoSquadra appuntamento,
			OrarioAppuntamentoDTO orario, EffettuaPrenotazioneHandler controller) {

		PrenotabileDescrizione descrizioneSpecifica = getCatalogoPrenotabili()
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						TipiPrenotazione.IMPIANTO.toString(),
						getRegistroSport().getSportByNome(formDati.getSportSelezionato()),
						ModalitaPrenotazione.SQUADRA.toString());

		// Creazione calcolatore che poi dovrà finire altrove
		CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
		calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());

		ImpiantoSelezionato impiantoSelezionato = null;
		for (ImpiantoSelezionato impianto : formDati.getImpianti()) {
			if (impianto.getIdSelezione() == orario.getId()) {
				impiantoSelezionato = impianto;
			}
		}
		appuntamento.setImpiantoPrenotato(getRegistroImpianti().getImpiantoByID(impiantoSelezionato.getIdImpianto()));

		appuntamento.setDescrizioneEventoPrenotato(descrizioneSpecifica);
		appuntamento.setCalcolatoreCosto(calcolatoreCosto);

		appuntamento.getOrarioAppuntamento().imposta(orario.getDataPrenotazione(), orario.getOraInizio(),
				orario.getOraFine());

		boolean pending = false;
		for (CheckboxPendingSelezionato checkbox : formDati.getCheckboxesPending()) {
			if (checkbox.getIdSelezione() == orario.getId()) {
				pending = checkbox.isPending();
			}
		}

		appuntamento.setPending(pending);

		appuntamento.calcolaCosto();

		for (Integer idSquadraInvitata : formDati.getSquadreInvitate()) {
			appuntamento.aggiungi(getRegistroSquadre().getSquadraById(idSquadraInvitata));
		}

		Squadra partecipante = getRegistroSquadre().getSquadraById(controller.getIdSquadraPrenotante());
		this.aggiungiPartecipanteECreaQuotePartecipazione(partecipante, appuntamento);

	}

	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandler controller) {
		for (Appuntamento app : controller.getPrenotazioneInAtto().getListaAppuntamenti()) {
			Calendario calendarioDaUnire = new Calendario();
			calendarioDaUnire.aggiungiAppuntamento(app);
			getRegistroImpianti().aggiornaCalendarioImpianto(app.getImpiantoPrenotato(), calendarioDaUnire);
			getRegistroSquadre().aggiornaCalendarioSquadra(
					getRegistroSquadre().getSquadraById(controller.getIdSquadraPrenotante()), calendarioDaUnire);
		}

		/*
		 * Creiamo le notifiche relative alle squadre invitate, creandone una per ogni
		 * amministratore di ogni squadra.
		 */
		for (Squadra invitato : ((AppuntamentoSquadra) controller.getPrenotazioneInAtto().getListaAppuntamenti().get(0))
				.getSquadreInvitate()) {

			for (UtentePolisportiva amministratore : invitato.getAmministratori()) {
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
		datiAggiornati.put("impiantiDisponibili", this.getImpiantiPrenotabiliInBaseA(dati));

		datiAggiornati.put("squadreInvitabili", convertiInDTOLaLista(getSquadreInvitabiliInBaseAlla(dati)));

		return datiAggiornati;
	}

	private List<SquadraDTO> convertiInDTOLaLista(List<Squadra> squadre) {
		List<SquadraDTO> squadreDTO = new ArrayList<SquadraDTO>();
		for (Squadra squadra : squadre) {
			squadreDTO.add(getMapperFactory().getSquadraMapper().convertiInSquadraDTO(squadra));
		}
		return squadreDTO;
	}

	private List<Squadra> getSquadreInvitabiliInBaseAlla(Map<String, Object> mappaDatiCompilati) {
		Map<String, String> orario = (Map<String, String>) mappaDatiCompilati.get("orario");
		OrarioAppuntamento orarioAppuntamento = new OrarioAppuntamento();
		orarioAppuntamento.imposta(orario.get("oraInizio"), orario.get("oraFine"));
		Sport sportSquadra = getRegistroSport().getSportByNome((String) mappaDatiCompilati.get("sport"));

		List<Squadra> listaSquadreInvitabili = getRegistroSquadre().getListaSquadreLiberePer(orarioAppuntamento);
		listaSquadreInvitabili = getRegistroSquadre().filtraSquadrePerSport(sportSquadra, listaSquadreInvitabili);
		return listaSquadreInvitabili;
	}

	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, Object identificativoPartecipante) {
		Integer idSquadra = (Integer) identificativoPartecipante;
		Squadra squadraPartecipante = getRegistroSquadre().getSquadraById(idSquadra);
		Appuntamento appuntamento = this.getRegistroAppuntamenti().getAppuntamentoById(idEvento);
		if (appuntamento != null) {
			boolean partecipanteAggiunto = this.aggiungiPartecipanteECreaQuotePartecipazione(squadraPartecipante,
					appuntamento);

			if (partecipanteAggiunto) {
				getRegistroSquadre().aggiornaCalendarioSquadra(squadraPartecipante, appuntamento);
			}

			// this.getRegistroAppuntamenti().aggiornaAppuntamento(appuntamento);
			AppuntamentoDTO appuntamentoDTO = getMapperFactory()
					.getAppuntamentoMapper(appuntamento.getTipoPrenotazione()).convertiInAppuntamentoDTO(appuntamento);
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
			AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper(appuntamento.getTipoPrenotazione())
					.convertiInAppuntamentoDTO(appuntamento);
			listaAppuntamentiDTO.add(appDTO);
		}
		return listaAppuntamentiDTO;
	}

	@Override
	protected boolean aggiungiPartecipanteECreaQuotePartecipazione(Object squadra, Appuntamento appuntamento) {
		boolean partecipanteAggiunto = appuntamento.aggiungiPartecipante(squadra);

		if (partecipanteAggiunto && appuntamento.getPartecipantiAppuntamento().size() >= appuntamento
				.getSogliaMinimaPartecipantiPerConferma()) {
			appuntamento.confermaAppuntamento();

		}
		this.getRegistroAppuntamenti().aggiornaAppuntamento(appuntamento);

		return partecipanteAggiunto;
	}

}
