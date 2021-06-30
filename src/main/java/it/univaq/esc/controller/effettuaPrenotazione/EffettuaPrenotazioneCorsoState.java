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
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
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
		List<Prenotazione> corsiDisponibili = this.getRegistroPrenotazioni().filtraPrenotazioniPerTipo(
				this.getRegistroPrenotazioni().getPrenotazioniRegistrate(), TipiPrenotazione.CORSO.toString());

		List<PrenotazioneDTO> listaCorsi = new ArrayList<PrenotazioneDTO>();
		for (Prenotazione prenotazione : corsiDisponibili) {

			PrenotazioneDTO prenotazioneDTO = getMapperFactory().getPrenotazioneMapper()
					.convertiCorsoInPrenotazioneDTO(prenotazione);

			listaCorsi.add(prenotazioneDTO);
		}

		mappaCorsiDisponibili.put("corsiDisponibili", listaCorsi);

		return mappaCorsiDisponibili;
	}

	@Override
	public PrenotazioneDTO impostaDatiPrenotazione(FormPrenotabile formDati, EffettuaPrenotazioneHandler controller) {

		/*
		 * Cicliamo sugli orari impostati in fase di compilazione, per creare e
		 * impostare tante specifiche di LEZIONI con relativi appuntamenti. Aggiungiamo
		 * le specifiche così impostate alla lista inizializzata prima. Infine associamo
		 * gli appuntamenti al controller.
		 */
		for (OrarioAppuntamentoDTO orario : formDati.getOrariSelezionati()) {

			AppuntamentoCorso appuntamentoCorso = (AppuntamentoCorso) getElementiPrenotazioneFactory()
					.getAppuntamento(TipiPrenotazione.CORSO.toString());

			impostaValoriAppuntamento(formDati, controller, appuntamentoCorso, orario);

			controller.getPrenotazioneInAtto().aggiungi(appuntamentoCorso);
		}

		/*
		 * Convertiamo la lista degli invitati al corso in DTO, per passarla poi alla
		 * prenotazioneDTO che andremo a ritornare alla fine.
		 */
		List<UtentePolisportivaDTO> invitatiDTO = new ArrayList<UtentePolisportivaDTO>();
		for (UtentePolisportiva invitato : ((AppuntamentoCorso) controller.getPrenotazioneInAtto()
				.getListaAppuntamenti().get(0)).getInvitati()) {
			UtentePolisportivaDTO invitatoDTO = getMapperFactory().getUtenteMapper()
					.convertiInUtentePolisportivaDTO(invitato);
			invitatiDTO.add(invitatoDTO);
		}

		/*
		 * Creiamo e impostiamo la PrenotazioneDTO che andremo a ritornare.
		 */
		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper()
				.convertiCorsoInPrenotazioneDTO(controller.getPrenotazioneInAtto());

		return prenDTO;

	}

	public void impostaValoriAppuntamento(FormPrenotabile formDatiCorso, EffettuaPrenotazioneHandler controller,
			AppuntamentoCorso appuntamento, OrarioAppuntamentoDTO orario) {

		/*
		 * Creiamo l'oggetto descrizione del corso da passare alla specifiche delle
		 * lezioni e del corso. Nel caso dei corsi infatti avremo speciche di LEZIONI ma
		 * con descrizioni del corso anziche delle LEZIONI
		 */
		PrenotabileDescrizione descrizioneCorso = getCatalogoPrenotabili()
				.nuovoPrenotabile_avviaCreazione(
						this.getRegistroSport().getSportByNome(formDatiCorso.getSportSelezionato()),
						controller.getTipoPrenotazioneInAtto(), formDatiCorso.getNumeroMinimoPartecipanti(),
						formDatiCorso.getNumeroMassimoPartecipanti())
				.nuovoPrenotabile_impostaCostoUnaTantum(formDatiCorso.getCostoPerPartecipante())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

		ImpiantoSelezionato impiantoSelezionato = null;
		for (ImpiantoSelezionato impianto : formDatiCorso.getImpianti()) {
			if (impianto.getIdSelezione() == orario.getId()) {
				impiantoSelezionato = impianto;
			}
		}
		appuntamento.setImpiantoPrenotato(getRegistroImpianti().getImpiantoByID(impiantoSelezionato.getIdImpianto()));

		// Creazione calcolatore che poi dovrà finire altrove
		CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
		calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());

		appuntamento.setDescrizioneEventoPrenotato(descrizioneCorso);
		appuntamento.setCalcolatoreCosto(calcolatoreCosto);

		appuntamento.getOrarioAppuntamento().imposta(orario.getDataPrenotazione(), orario.getOraInizio(),
				orario.getOraFine());

		appuntamento.calcolaCosto();

		for (String emailInvitato : formDatiCorso.getSportiviInvitati()) {
			appuntamento.aggiungi(getRegistroUtenti().trovaUtenteInBaseAllaSua(emailInvitato));
		}

		IstruttoreSelezionato istruttoreSelezionato = null;
		for (IstruttoreSelezionato istruttore : formDatiCorso.getIstruttori()) {
			if (istruttore.getIdSelezione() == orario.getId()) {
				istruttoreSelezionato = istruttore;
			}
		}
		appuntamento.assegna(getRegistroUtenti().trovaUtenteInBaseAllaSua(istruttoreSelezionato.getIstruttore()));

	}

	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandler controller) {

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
	public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
		Map<String, Object> mappaAggiornata = this.getStatoControllerLezioni().aggiornaOpzioniPrenotazione(dati);

		mappaAggiornata.put("sportiviInvitabili", trovaSportiviLiberiInBaseA((Map<String, String>) dati.get("orario")));

		return mappaAggiornata;
	}

	private List<UtentePolisportivaDTO> trovaSportiviLiberiInBaseA(Map<String, String> mappaOrario) {
		OrarioAppuntamento orarioAppuntamento = creaOrarioAppuntamentoDa(mappaOrario);
		return getSportiviLiberiInBaseA(orarioAppuntamento);
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
	public Map<String, Object> getDatiOpzioniModalitaDirettore(EffettuaPrenotazioneHandler controller) {
		setStatoControllerLezioni((EffettuaPrenotazioneLezioneState) getElementiPrenotazioneFactory()
				.getStatoEffettuaPrenotazioneHandler(TipiPrenotazione.LEZIONE.toString()));
		getStatoControllerLezioni().setMapperFactory(getMapperFactory());

		Map<String, Object> mappaDati = this.getStatoControllerLezioni()
				.getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(controller);
		mappaDati.put("sportiviInvitabili", this.getSportiviPolisportiva());

		return mappaDati;
	}

	@Override
	protected boolean aggiungiPartecipanteECreaQuotePartecipazione(Object utente, Appuntamento appuntamento) {
		boolean partecipanteAggiunto = appuntamento.aggiungiPartecipante(utente);

		appuntamento.confermaAppuntamento();

		return partecipanteAggiunto;
	}

}
