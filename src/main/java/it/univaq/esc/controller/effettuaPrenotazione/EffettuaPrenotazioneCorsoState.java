package it.univaq.esc.controller.effettuaPrenotazione;

import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.OrarioAppuntamentoDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.*;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.*;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)

public class EffettuaPrenotazioneCorsoState extends EffettuaPrenotazioneState {

	private PrenotabileDescrizione prenotabileDescrizioneCorso;

	/**
	 * Stato
	 */
	private EffettuaPrenotazioneLezioneState statoControllerLezioni;

	public EffettuaPrenotazioneCorsoState(RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtentiPolisportiva,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, EffettuaPrenotazioneLezioneState effettuaPrenotazioneLezioneState,
			RegistroSquadre registroSquadre, RegistroQuotePartecipazione registroQuotePartecipazione) {

		super(registroNotifiche, registroSport, registroImpianti, registroUtentiPolisportiva, registroAppuntamenti,
				registroPrenotazioni, catalogoPrenotabili, registroSquadre, registroQuotePartecipazione);

	}

	@Override
	public Map<String, Object> getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(
			EffettuaPrenotazioneHandler controller) {
		Map<String, Object> mappaCorsiDisponibili = new HashMap<String, Object>();
		mappaCorsiDisponibili.put("corsiDisponibili", trovaCorsiDisponibili());
		return mappaCorsiDisponibili;
	}

	private List<PrenotazioneDTO> trovaCorsiDisponibili() {
		List<Prenotazione> corsiDisponibili = getRegistroPrenotazioni().filtraPrenotazioniPerTipo(
				getRegistroPrenotazioni().getPrenotazioniRegistrate(), TipoPrenotazione.CORSO.toString());
		List<PrenotazioneDTO> listaCorsiDisponibiliInFormatoDTO = convertiInDTOLaLista(corsiDisponibili);
		return listaCorsiDisponibiliInFormatoDTO;
	}

	private List<PrenotazioneDTO> convertiInDTOLaLista(List<Prenotazione> corsi) {
		List<PrenotazioneDTO> listaCorsi = new ArrayList<PrenotazioneDTO>();
		for (Prenotazione prenotazione : corsi) {
			PrenotazioneDTO prenotazioneDTO = getMapperFactory().getPrenotazioneMapper()
					.convertiCorsoInPrenotazioneDTO(prenotazione);
			listaCorsi.add(prenotazioneDTO);
		}
		return listaCorsi;
	}

	@Override
	public PrenotazioneDTO impostaPrenotazioneConDatiDellaFormPerRiepilogo(FormPrenotabile formDati,
			EffettuaPrenotazioneHandler controller) {

		setPrenotabileDescrizioneCorso(creaPrenotabileDescrizioneCorso(formDati));

		for (OrarioAppuntamentoDTO orario : formDati.getOrariSelezionati()) {
			impostaAppuntamentoCorsoRelativoAOrarioNellaPrenotazione(controller.getPrenotazioneInAtto(), orario,
					formDati);
		}
		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper()
				.convertiCorsoInPrenotazioneDTO(controller.getPrenotazioneInAtto());

		return prenDTO;

	}

	private PrenotabileDescrizione creaPrenotabileDescrizioneCorso(FormPrenotabile formDati) {
		PrenotabileDescrizione descrizioneCorso = getCatalogoPrenotabili().avviaCreazioneNuovoPrenotabile()
				.impostaSport(getRegistroSport().getSportByNome(formDati.getSportSelezionato()))
				.impostaTipoPrenotazione(TipoPrenotazione.CORSO)
				.impostaSogliaMassimaPartecipanti(formDati.getNumeroMassimoPartecipanti())
				.impostaSogliaMinimaPartecipanti(formDati.getNumeroMinimoPartecipanti())
				.impostaCostoUnaTantum(
						new Costo(formDati.getCostoPerPartecipante(), new Valuta(Valute.EUR)))
				.impostaModalitaPrenotazioneComeSingoloUtente()
				.impostaDescrizione(formDati.getNomeEvento())
				.build();
		getCatalogoPrenotabili().aggiungiPrenotabileACatalogo(descrizioneCorso);
		getCatalogoPrenotabili().salvaPrenotabileDescrizioneSulDatabase(descrizioneCorso);

		return descrizioneCorso;
	}

	private void impostaAppuntamentoCorsoRelativoAOrarioNellaPrenotazione(Prenotazione prenotazioneInAtto,
			OrarioAppuntamentoDTO orario, FormPrenotabile formDati) {
		AppuntamentoCorso appuntamentoLezione = getAppuntamentoPerOrarioImpostatoUsandoForm(orario, formDati);
		prenotazioneInAtto.aggiungi(appuntamentoLezione);
	}

	private AppuntamentoCorso getAppuntamentoPerOrarioImpostatoUsandoForm(OrarioAppuntamentoDTO orario,
			FormPrenotabile formDati) {
		AppuntamentoCorso appuntamento = (AppuntamentoCorso) getElementiPrenotazioneFactory()
				.getAppuntamento(TipoPrenotazione.CORSO.toString());
		DatiFormPerAppuntamento datiFormPerAppuntamento = getMapperFactory()
				.getAppuntamentoMapper(TipoPrenotazione.CORSO.toString())
				.getDatiFormPerAppuntamentoUsando(formDati, orario);
		appuntamento.impostaDatiAppuntamentoDa(datiFormPerAppuntamento);
		appuntamento.setCalcolatoreCosto(getElementiPrenotazioneFactory().getCalcolatoreCosto());
		appuntamento.calcolaCosto();
		return appuntamento;
	}

	@Override
	public void aggiornaElementiLegatiAllaPrenotazioneConfermata(EffettuaPrenotazioneHandler controller) {
		for (AppuntamentoCorso app : (List<AppuntamentoCorso>) (List<?>) controller.getPrenotazioneInAtto()
				.getListaAppuntamenti()) {
			app.siAggiungeAlCalendarioDelRelativoImpiantoPrenotato();
			app.siAggiungeAlCalendarioDellIstruttoreRelativo();
			getRegistroNotifiche().impostaNotificaPerIstruttoreAssociatoANuovaLezione(app.getIstruttore(), app);
		}
		impostaNelSistemaLeNotifichePerTuttiGliInvitatiAl(controller.getPrenotazioneInAtto());

	}

	private void impostaNelSistemaLeNotifichePerTuttiGliInvitatiAl(Prenotazione nuovoCorso) {
		for (UtentePolisportiva invitato : ((AppuntamentoCorso) nuovoCorso.getListaAppuntamenti().get(0))
				.getInvitati()) {
			getRegistroNotifiche().impostaNotificaPerUtenteInvitatoAPrenotazioneImpianto(invitato, nuovoCorso);
		}
	}

	@Override
	public Map<String, Object> getDatiOpzioniPrenotazioneAggiornatiInBaseAllaMappa(Map<String, Object> dati) {
		Map<String, Object> mappaAggiornata = this.getStatoControllerLezioni()
				.getDatiOpzioniPrenotazioneAggiornatiInBaseAllaMappa(dati);

		mappaAggiornata.put("sportiviInvitabili", trovaSportiviLiberiInBaseA((Map<String, String>) dati.get("orario")));

		return mappaAggiornata;
	}

	private List<UtentePolisportivaDTO> trovaSportiviLiberiInBaseA(Map<String, String> mappaOrario) {
		OrarioAppuntamento orarioAppuntamento = creaOrarioAppuntamentoDa(mappaOrario);
		return getSportiviDTOLiberiNell(orarioAppuntamento);
	}

	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, Object identificativoPartecipante) {
		String emailPartecipante = (String) identificativoPartecipante;
		Prenotazione corsoPrenotazione = getRegistroPrenotazioni().getPrenotazioneById(idEvento);
		List<Appuntamento> listaAppuntamentiCorso = corsoPrenotazione.getListaAppuntamenti();
		UtentePolisportiva nuovoPartecipante = getRegistroUtenti().trovaUtenteInBaseAllaSua(emailPartecipante);
		boolean isPartecipanteAggiunto = false;
		for (Appuntamento appuntamento : listaAppuntamentiCorso) {
			isPartecipanteAggiunto = appuntamento.aggiungiPartecipante(nuovoPartecipante);
		}
		confermaAppuntamentiConCreazioneQuotePartecipazioneSeRaggiuntoNumeroMinimoPartecipanti(listaAppuntamentiCorso,
				isPartecipanteAggiunto);
		salvaModificheAlla(listaAppuntamentiCorso);
		aggiornaCalendarioNuovoPartecipanteConGliAppuntamentiDelCorso(nuovoPartecipante, listaAppuntamentiCorso);

		PrenotazioneDTO prenotazioneDTO = getMapperFactory().getPrenotazioneMapper()
				.convertiCorsoInPrenotazioneDTO(corsoPrenotazione);
		return prenotazioneDTO;
	}

	private void confermaAppuntamentiConCreazioneQuotePartecipazioneSeRaggiuntoNumeroMinimoPartecipanti(
			List<Appuntamento> listaAppuntamentiCorso, boolean isNuovoPartecipanteAggiunto) {
		AppuntamentoCorso appuntamentoPerCreazioneQuota = (AppuntamentoCorso) listaAppuntamentiCorso.get(0);
		if (isNuovoPartecipanteAggiunto && appuntamentoPerCreazioneQuota.haNumeroPartecipantiNecessarioPerConferma()) {
			listaAppuntamentiCorso.forEach((appuntamento) -> appuntamento.confermaAppuntamento());
			appuntamentoPerCreazioneQuota.creaQuotePartecipazionePerAppuntamento(getRegistroQuotePartecipazione().getUltimoIdQuote());
			appuntamentoPerCreazioneQuota.assegnaStesseQuotePartecipazioneAgliAltriAppuntamentiDelCorso();
		}
	}

	private void salvaModificheAlla(List<Appuntamento> listaAppuntamentiDelCorso) {
		for (Appuntamento appuntamento : listaAppuntamentiDelCorso) {
			getRegistroAppuntamenti().aggiorna(appuntamento);
		}
	}

	private void aggiornaCalendarioNuovoPartecipanteConGliAppuntamentiDelCorso(UtentePolisportiva nuovoPartecipante,
			List<Appuntamento> appuntamentiCorso) {
		Calendario calendarioSportivo = new Calendario();
		for (Appuntamento appuntamento : appuntamentiCorso) {
			calendarioSportivo.aggiungiAppuntamento(appuntamento);
		}
		nuovoPartecipante.comeSportivo().segnaInAgendaGliAppuntamentiDel(calendarioSportivo);

	}

	@Override
	public Map<String, Object> getDatiOpzioniPerPrenotazioneInModalitaDirettore(
			EffettuaPrenotazioneHandler controller) {
		setStatoControllerLezioni((EffettuaPrenotazioneLezioneState) getElementiPrenotazioneFactory()
				.getStatoEffettuaPrenotazioneHandler(TipoPrenotazione.LEZIONE.toString()));
		getStatoControllerLezioni().setMapperFactory(getMapperFactory());

		Map<String, Object> mappaDati = this.getStatoControllerLezioni()
				.getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(controller);
		mappaDati.put("sportiviInvitabili", this.getSportiviPolisportivaInFormatoDTO());

		return mappaDati;
	}

	public void eliminaDescrizioneCorsoDaCatalogoEDatabase() {
		getCatalogoPrenotabili().eliminaPrenotabileDescrizione(getPrenotabileDescrizioneCorso());
	}
}
