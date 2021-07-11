package it.univaq.esc.controller.effettuaPrenotazione;

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
import it.univaq.esc.model.notifiche.Notifica;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.NotificaSquadra;
import it.univaq.esc.model.notifiche.NotificaSquadraService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.notifiche.TipoNotifica;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpianto;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpiantoSquadra;
import it.univaq.esc.model.prenotazioni.AppuntamentoSquadra;
import it.univaq.esc.model.prenotazioni.DatiFormPerAppuntamento;
import it.univaq.esc.model.prenotazioni.OrarioAppuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneSquadra;
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
		mappaValori.put("sportPraticabili", this.getSportPraticabiliNellaPolisportivaInFormatoDTO());
		mappaValori.put("squadreInvitabili", getRegistroSquadre().getListaSquadre());
		mappaValori.put("appuntamentiSottoscrivibiliSquadra", this.getAppuntamentiImpiantoSottoscrivibiliDaSquadra(
				((PrenotazioneSquadra)controller.getPrenotazioneInAtto()).getSquadraPrenotante()));

		return mappaValori;
	}

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
		PrenotazioneSquadra prenotazioneSquadraInAtto = (PrenotazioneSquadra) prenotazioneInAtto;
		AppuntamentoImpiantoSquadra appuntamentoImpianto = getAppuntamentoPerOrarioImpostatoUsandoForm(orario, formDati);
		prenotazioneSquadraInAtto.aggiungi(appuntamentoImpianto);
		appuntamentoImpianto.aggiungiPartecipante(prenotazioneSquadraInAtto.getSquadraPrenotante());
	}
	
	private AppuntamentoImpiantoSquadra getAppuntamentoPerOrarioImpostatoUsandoForm(OrarioAppuntamentoDTO orario,
			FormPrenotabile formDati) {
		AppuntamentoImpiantoSquadra appuntamento = (AppuntamentoImpiantoSquadra) getElementiPrenotazioneFactory()
				.getAppuntamento(TipiPrenotazione.IMPIANTO.toString());

		DatiFormPerAppuntamento datiFormPerAppuntamento = getMapperFactory()
				.getAppuntamentoMapper(TipiPrenotazione.IMPIANTO.toString())
				.getDatiFormPerAppuntamentoUsando(formDati, orario);

		appuntamento.impostaDatiAppuntamentoDa(datiFormPerAppuntamento);
		appuntamento.setCalcolatoreCosto(getElementiPrenotazioneFactory().getCalcolatoreCosto());
		appuntamento.calcolaCosto();

		return appuntamento;
	}

	
	@Override
	public void aggiornaElementiLegatiAllaPrenotazioneConfermata(EffettuaPrenotazioneHandler controller) {
		List<AppuntamentoImpiantoSquadra> appuntamentiImpiantoSquadra = (List<AppuntamentoImpiantoSquadra>)(List<?>)controller.getPrenotazioneInAtto().getListaAppuntamenti();
		for (Appuntamento app : appuntamentiImpiantoSquadra) {
			aggiornaCalendariImpiantoESquadraPrenotanteConIl(app);
		}
		impostaNotifichePerLeSquadreInvitateAllaPrenotazioneInAtto(appuntamentiImpiantoSquadra.get(0).getSquadreInvitate(), controller.getPrenotazioneInAtto());
	}
	
	private void aggiornaCalendariImpiantoESquadraPrenotanteConIl(Appuntamento nuovoAppuntamento) {
		AppuntamentoImpiantoSquadra nuovoAppuntamentoSquadra = (AppuntamentoImpiantoSquadra) nuovoAppuntamento;
		nuovoAppuntamentoSquadra.siAggiungeAlCalendarioDelRelativoImpiantoPrenotato();
		nuovoAppuntamentoSquadra.siAggiungeAlCalendarioDellaSquadraPrenotante();
	}
	
	private void impostaNotifichePerLeSquadreInvitateAllaPrenotazioneInAtto(List<Squadra> squadreInvitate, Prenotazione prenotazioneInAtto) {
		for(Squadra squadraInvitata : squadreInvitate) {
			impostaNotifichePerGliAmministratoriSquadraInvitataAllAppuntamentoDellaPrenotazioneInAtto(squadraInvitata.getAmministratori(), squadraInvitata, prenotazioneInAtto);
		}
	}
	
	private void impostaNotifichePerGliAmministratoriSquadraInvitataAllAppuntamentoDellaPrenotazioneInAtto(List<UtentePolisportiva> amministratoriSquadra,  Squadra squadraInvitata, Prenotazione prenotazioneInAtto) {
		for (UtentePolisportiva amministratore : amministratoriSquadra) {
			getRegistroNotifiche().impostaNotificaPerAmministratoreSquadraInvitataAPrenotazioneImpianto(amministratore, squadraInvitata, prenotazioneInAtto);
		}
	}
	

	@Override
	public Map<String, Object> getDatiOpzioniPrenotazioneAggiornatiInBaseAllaMappa(Map<String, Object> dati) {
		Map<String, Object> datiAggiornati = new HashMap<String, Object>();
		datiAggiornati.put("impiantiDisponibili", this.getListaDTOImpiantiPrenotabiliInBaseAMappa(dati));

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
		this.getRegistroAppuntamenti().aggiorna(appuntamento);

		return partecipanteAggiunto;
	}

}
