package it.univaq.esc.controller.effettuaPrenotazione;

import it.univaq.esc.dtoObjects.*;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.*;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@DependsOn("beanUtil")
public class EffettuaPrenotazioneImpiantoSquadraState extends EffettuaPrenotazioneState {

	public EffettuaPrenotazioneImpiantoSquadraState(RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtenti,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, RegistroSquadre registroSquadre, RegistroQuotePartecipazione registroQuotePartecipazione) {
		super(registroNotifiche, registroSport, registroImpianti, registroUtenti, registroAppuntamenti,
				registroPrenotazioni, catalogoPrenotabili, registroSquadre, registroQuotePartecipazione);

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
	
	private List<AppuntamentoDTO> getAppuntamentiImpiantoSottoscrivibiliDaSquadra(
			Squadra squadraPerCuiCercareAppuntamentiSottoscrivibili) {
		List<AppuntamentoDTO> listaAppuntamentiDTO = new ArrayList<AppuntamentoDTO>();
		for (Appuntamento appuntamento : this.getRegistroAppuntamenti().getAppuntamentiSottoscrivibiliSquadraPerTipo(
				TipoPrenotazione.IMPIANTO.toString(), squadraPerCuiCercareAppuntamentiSottoscrivibili)) {
			AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper(appuntamento.getTipoPrenotazione().toString())
					.convertiInAppuntamentoDTO(appuntamento);
			listaAppuntamentiDTO.add(appDTO);
		}
		return listaAppuntamentiDTO;
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
				.getAppuntamento(TipoPrenotazione.IMPIANTO.toString());

		DatiFormPerAppuntamento datiFormPerAppuntamento = getMapperFactory()
				.getAppuntamentoMapper(TipoPrenotazione.IMPIANTO.toString())
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
		listaSquadreInvitabili = getRegistroSquadre().filtraListaSquadrePerSport(sportSquadra, listaSquadreInvitabili);
		return listaSquadreInvitabili;
	}

	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, Object identificativoPartecipante) {
		Squadra squadraPartecipante = ricavaSquadraDall(identificativoPartecipante);
		AppuntamentoSquadra appuntamento = ricavaAppuntamentoSquadraDall(idEvento);
		if (appuntamento != null) {
			boolean partecipanteAggiunto = appuntamento.aggiungiPartecipante(squadraPartecipante);
			appuntamento.confermaSeRaggiuntoNumeroNecessarioDiPartecipanti();
			getRegistroAppuntamenti().aggiorna(appuntamento);
			if (partecipanteAggiunto) {
				appuntamento.siAggiungeAlCalendarioDella(squadraPartecipante);
			}
			AppuntamentoDTO appuntamentoDTO = getMapperFactory()
					.getAppuntamentoMapper(appuntamento.getTipoPrenotazione().toString()).convertiInAppuntamentoDTO(appuntamento);
			return appuntamentoDTO;
		}
		return null;
	}
	
	private Squadra ricavaSquadraDall(Object identificativoSquadra) {
		Integer idSquadra = (Integer) identificativoSquadra;
		Squadra squadra = getRegistroSquadre().getSquadraById(idSquadra);
		return squadra;
	}
	
	private AppuntamentoSquadra ricavaAppuntamentoSquadraDall(Integer idAppuntamento) {
		Appuntamento appuntamento = getRegistroAppuntamenti().getAppuntamentoById(idAppuntamento);
		AppuntamentoSquadra appuntamentoSquadra = (AppuntamentoSquadra) appuntamento;
		return appuntamentoSquadra;
	}

	@Override
	public Map<String, Object> getDatiOpzioniPerPrenotazioneInModalitaDirettore(EffettuaPrenotazioneHandler controller) {
		return new HashMap<String, Object>();
	}

}
