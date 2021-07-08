package it.univaq.esc.controller.effettuaPrenotazione;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoSelezionato;
import it.univaq.esc.dtoObjects.IstruttoreSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamentoDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Costo;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.Valuta;
import it.univaq.esc.model.Valute;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.notifiche.Notifica;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoCorso;
import it.univaq.esc.model.prenotazioni.AppuntamentoLezione;
import it.univaq.esc.model.prenotazioni.DatiFormPerAppuntamento;
import it.univaq.esc.model.prenotazioni.OrarioAppuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;

import lombok.Getter;

import lombok.Setter;

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
			RegistroSquadre registroSquadre) {

		super(registroNotifiche, registroSport, registroImpianti, registroUtentiPolisportiva, registroAppuntamenti,
				registroPrenotazioni, catalogoPrenotabili, registroSquadre);

	}

	@Override
	public Map<String, Object> getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(
			EffettuaPrenotazioneHandler controller) {
		Map<String, Object> mappaCorsiDisponibili = new HashMap<String, Object>();
		mappaCorsiDisponibili.put("corsiDisponibili", trovaCorsiDisponibili());
		return mappaCorsiDisponibili;
	}
	
	
	private List<PrenotazioneDTO> trovaCorsiDisponibili(){
		List<Prenotazione> corsiDisponibili = getRegistroPrenotazioni().filtraPrenotazioniPerTipo(
				getRegistroPrenotazioni().getPrenotazioniRegistrate(), TipiPrenotazione.CORSO.toString());
		List<PrenotazioneDTO> listaCorsiDisponibiliInFormatoDTO = convertiInDTOLaLista(corsiDisponibili);
		return listaCorsiDisponibiliInFormatoDTO;
	}
	
	private List<PrenotazioneDTO> convertiInDTOLaLista(List<Prenotazione> corsi){
		List<PrenotazioneDTO> listaCorsi = new ArrayList<PrenotazioneDTO>();
		for (Prenotazione prenotazione : corsi) {
			PrenotazioneDTO prenotazioneDTO = getMapperFactory().getPrenotazioneMapper()
					.convertiCorsoInPrenotazioneDTO(prenotazione);
			listaCorsi.add(prenotazioneDTO);
		}
		return listaCorsi;
	}

	@Override
	public PrenotazioneDTO impostaPrenotazioneConDatiDellaFormPerRiepilogo(FormPrenotabile formDati, EffettuaPrenotazioneHandler controller) {
		
		setPrenotabileDescrizioneCorso(creaPrenotabileDescrizioneCorso(formDati));
		
		for (OrarioAppuntamentoDTO orario : formDati.getOrariSelezionati()) {
			impostaAppuntamentoCorsoRelativoAOrarioNellaPrenotazione(controller.getPrenotazioneInAtto(), orario, formDati);
		}
		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper()
				.convertiCorsoInPrenotazioneDTO(controller.getPrenotazioneInAtto());

		return prenDTO;

	}
	
	private PrenotabileDescrizione creaPrenotabileDescrizioneCorso(FormPrenotabile formDati) {
		PrenotabileDescrizione descrizioneCorso = getCatalogoPrenotabili()
				.nuovoPrenotabile_avviaCreazione(this.getRegistroSport().getSportByNome(formDati.getSportSelezionato()),
						TipiPrenotazione.CORSO.toString(), formDati.getNumeroMinimoPartecipanti(),
						formDati.getNumeroMassimoPartecipanti())
				.nuovoPrenotabile_impostaCostoUnaTantum(
						new Costo(formDati.getCostoPerPartecipante(), new Valuta(Valute.EUR)))
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_impostaDescrizione(formDati.getNomeEvento())
				.nuovoPrenotabile_salvaPrenotabileInCreazioneNelCatalogo();
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
				.getAppuntamento(TipiPrenotazione.CORSO.toString());
		DatiFormPerAppuntamento datiFormPerAppuntamento = getMapperFactory()
				.getAppuntamentoMapper(TipiPrenotazione.CORSO.toString())
				.getDatiFormPerAppuntamentoUsando(formDati, orario);
		appuntamento.impostaDatiAppuntamentoDa(datiFormPerAppuntamento);
		appuntamento.setCalcolatoreCosto(getElementiPrenotazioneFactory().getCalcolatoreCosto());
		appuntamento.calcolaCosto();
		return appuntamento;
	}

	@Override
	public void aggiornaElementiLegatiAllaPrenotazioneConfermata(EffettuaPrenotazioneHandler controller) {

		//getCatalogoPrenotabili().salvaPrenotabileDescrizioneSulDatabase(getPrenotabileDescrizioneCorso());
		for (AppuntamentoCorso app : (List<AppuntamentoCorso>) (List<?>) controller.getPrenotazioneInAtto()
				.getListaAppuntamenti()) {
			app.siAggiungeAlCalendarioDelRelativoImpiantoPrenotato();
			app.siAggiungeAlCalendarioDellIstruttoreRelativo();
		}
		impostaNelSistemaLeNotifichePerTuttiGliInvitatiAl(controller.getPrenotazioneInAtto());

	}

	private void impostaNelSistemaLeNotifichePerTuttiGliInvitatiAl(Prenotazione nuovoCorso) {
		for (UtentePolisportiva invitato : ((AppuntamentoCorso) nuovoCorso.getListaAppuntamenti().get(0))
				.getInvitati()) {
			impostaNelSistemaNotificaPerInvitatoAlCorso(invitato, nuovoCorso);
		}
	}

	private void impostaNelSistemaNotificaPerInvitatoAlCorso(UtentePolisportiva invitato, Prenotazione corso) {
		NotificaService notifica = getElementiPrenotazioneFactory().getNotifica(new Notifica());
		notifica.setStatoNotifica(TipiPrenotazione.CORSO.toString());
		notifica.setDestinatario(invitato);
		notifica.setEvento(corso);
		notifica.setLetta(false);
		notifica.setMittente(corso.getSportivoPrenotante());
		getRegistroNotifiche().salvaNotifica(notifica);
	}

	@Override
	public Map<String, Object> getDatiOpzioniPrenotazioneAggiornatiInBaseAllaMappa(Map<String, Object> dati) {
		Map<String, Object> mappaAggiornata = this.getStatoControllerLezioni().getDatiOpzioniPrenotazioneAggiornatiInBaseAllaMappa(dati);

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
		boolean partecipanteAggiunto = false;
		for (Appuntamento appuntamento : listaAppuntamentiCorso) {
			partecipanteAggiunto = this.aggiungiPartecipanteECreaQuotePartecipazione(nuovoPartecipante, appuntamento);
		}
		Appuntamento appuntamentoPerCreazioneQuota = listaAppuntamentiCorso.get(0);

		List<QuotaPartecipazione> quotePartecipazione = getRegistroAppuntamenti()
				.creaQuotePartecipazionePerCorso(appuntamentoPerCreazioneQuota);

		if (partecipanteAggiunto && appuntamentoPerCreazioneQuota.isConfermato()) {
			for (Appuntamento appuntamento : listaAppuntamentiCorso) {
				for (QuotaPartecipazione quota : quotePartecipazione) {
					appuntamento.aggiungiQuotaPartecipazione(quota);
				}
			}
		}

		salvaModificheAlla(listaAppuntamentiCorso);
		aggiornaCalendarioNuovoPartecipanteConGliAppuntamentiDelCorso(nuovoPartecipante, listaAppuntamentiCorso);

		PrenotazioneDTO prenotazioneDTO = getMapperFactory().getPrenotazioneMapper()
				.convertiCorsoInPrenotazioneDTO(corsoPrenotazione);
		return prenotazioneDTO;
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
	public Map<String, Object> getDatiOpzioniPerPrenotazioneInModalitaDirettore(EffettuaPrenotazioneHandler controller) {
		setStatoControllerLezioni((EffettuaPrenotazioneLezioneState) getElementiPrenotazioneFactory()
				.getStatoEffettuaPrenotazioneHandler(TipiPrenotazione.LEZIONE.toString()));
		getStatoControllerLezioni().setMapperFactory(getMapperFactory());

		Map<String, Object> mappaDati = this.getStatoControllerLezioni()
				.getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(controller);
		mappaDati.put("sportiviInvitabili", this.getSportiviPolisportivaInFormatoDTO());

		return mappaDati;
	}

	@Override
	protected boolean aggiungiPartecipanteECreaQuotePartecipazione(Object utente, Appuntamento appuntamento) {
		boolean partecipanteAggiunto = appuntamento.aggiungiPartecipante(utente);

		appuntamento.confermaAppuntamento();

		return partecipanteAggiunto;
	}

}
