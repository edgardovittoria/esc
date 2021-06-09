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
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.costi.CatalogoPrenotabili;
import it.univaq.esc.model.costi.ModalitaPrenotazione;
import it.univaq.esc.model.costi.PrenotabileDescrizione;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoComposito;
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

@Component
@DependsOn("beanUtil")
public class EffettuaPrenotazioneImpiantoSquadraState extends EffettuaPrenotazioneState{

	public EffettuaPrenotazioneImpiantoSquadraState(RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtenti,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, RegistroSquadre registroSquadre) {
		super(registroNotifiche, registroSport, registroImpianti, registroUtenti, registroAppuntamenti, registroPrenotazioni,
				catalogoPrenotabili, registroSquadre);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<String, Object> getDatiOpzioni(EffettuaPrenotazioneHandlerRest controller) {
		Map<String, Object> mappaValori = new HashMap<String, Object>();
		mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
		mappaValori.put("squadreInvitabili", getRegistroSquadre().getListaSquadre());
		mappaValori.put("appuntamentiSottoscrivibiliSquadra",
				this.getAppuntamentiImpiantoSottoscrivibiliDaSquadra(getRegistroSquadre().getSquadraById(controller.getIdSquadraPrenotante())));

		return mappaValori;
	}

	@Override
	public PrenotazioneDTO impostaDatiPrenotazione(FormPrenotabile formDati,
			EffettuaPrenotazioneHandlerRest controller) {

		for (OrarioAppuntamento orario : (List<OrarioAppuntamento>) formDati.getValoriForm()
				.get("listaOrariAppuntamenti")) {
			PrenotazioneImpiantoSquadraSpecs prenotazioneSpecs = new PrenotazioneImpiantoSquadraSpecs();
			controller.getPrenotazioneInAtto().aggiungiSpecifica(prenotazioneSpecs);

			impostaDatiPrenotazioneSpecs(prenotazioneSpecs, formDati, orario, controller);

			// ---------------------------------------------------------------------------------------

			AppuntamentoSquadra appuntamento = new AppuntamentoSquadra();
			impostaDatiAppuntamento(prenotazioneSpecs, formDati, appuntamento, orario, controller);

			controller.aggiungiAppuntamento(appuntamento);
		}

		PrenotazioneDTO prenDTO = new PrenotazioneDTO();
		Map<String, Object> mappa = new HashMap<String, Object>();
		mappa.put("prenotazione", controller.getPrenotazioneInAtto());
		mappa.put("appuntamentiPrenotazione", controller.getListaAppuntamentiPrenotazioneInAtto());
		prenDTO.impostaValoriDTO(mappa);

		return prenDTO;
	}
	
	
	private void impostaDatiPrenotazioneSpecs(PrenotazioneImpiantoSquadraSpecs prenotazioneSpecs, FormPrenotabile formDati,
			OrarioAppuntamento orario, EffettuaPrenotazioneHandlerRest controller) {
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

		List<Squadra> squadreInvitate = new ArrayList<Squadra>();
		for (Integer idSquadra : (List<Integer>) formDati.getValoriForm().get("squadreInvitate")) {
			squadreInvitate.add(getRegistroSquadre().getSquadraById(idSquadra));
		}
		prenotazioneSpecs.setSquadreInvitate(squadreInvitate);
		prenotazioneSpecs.setSpecificaDescription(descrizioneSpecifica);
	}

	private void impostaDatiAppuntamento(PrenotazioneImpiantoSquadraSpecs prenotazioneSpecs, FormPrenotabile formDati,
			AppuntamentoSquadra appuntamento, OrarioAppuntamento orario,
			EffettuaPrenotazioneHandlerRest controller) {
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

		//this.aggiungiPartecipante(controller.getPrenotazioneInAtto().getSportivoPrenotante(), appuntamento);
	}
	
	
	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandlerRest controller) {
		// TODO Auto-generated method stub
		
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
					getRegistroSquadre().filtraSquadrePerSport(getRegistroSport().getSportByNome((String)dati.get("sport")), getRegistroSquadre().getListaSquadreLiberePerOrarioAppuntamento(oraInizio, oraFine)));
		

		return datiAggiornati;
	}

	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, String emailPartecipante) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getDatiOpzioniModalitaDirettore(EffettuaPrenotazioneHandlerRest controller) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private List<AppuntamentoDTO> getAppuntamentiImpiantoSottoscrivibiliDaSquadra(
			Squadra squadraPerCuiCercareAppuntamentiSottoscrivibili) {
		List<AppuntamentoDTO> listaAppuntamentiDTO = new ArrayList<AppuntamentoDTO>();
		for (Appuntamento appuntamento : this.getRegistroAppuntamenti().getAppuntamentiSottoscrivibiliSquadraPerTipo(
				TipiPrenotazione.IMPIANTO.toString(), squadraPerCuiCercareAppuntamentiSottoscrivibili)) {
			AppuntamentoDTO appDTO = new AppuntamentoDTO();
			appDTO.impostaValoriDTO(appuntamento);
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
			if (appuntamento.getPartecipantiAppuntamento().size() >= appuntamento.getSogliaMinimaPartecipantiPerConferma()) {
				appuntamento.confermaAppuntamento();
				
			}
			this.getRegistroAppuntamenti().aggiornaAppuntamento(appuntamento);
		}
		return partecipanteAggiunto;
	}

}
