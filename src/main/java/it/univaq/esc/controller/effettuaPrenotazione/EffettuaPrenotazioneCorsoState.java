package it.univaq.esc.controller.effettuaPrenotazione;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.OrarioAppuntamento;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoSingoliPartecipanti;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneCorsoSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneLezioneSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
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
	public Map<String, Object> getDatiOpzioni(EffettuaPrenotazioneHandler controller) {
		Map<String, Object> mappaCorsiDisponibili = new HashMap<String, Object>();
		List<Prenotazione> corsiDisponibili = this.getRegistroPrenotazioni().filtraPrenotazioniPerTipo(
				this.getRegistroPrenotazioni().getPrenotazioniRegistrate(), TipiPrenotazione.CORSO.toString());

		List<PrenotazioneDTO> listaCorsi = new ArrayList<PrenotazioneDTO>();
		for (Prenotazione prenotazione : corsiDisponibili) {
			List<Appuntamento> appuntamentiPrenotazione = this.getRegistroAppuntamenti()
					.getAppuntamentiByPrenotazioneId(prenotazione.getIdPrenotazione());

			Map<String, Object> infoGeneraliCorso = new HashMap<String, Object>();
			infoGeneraliCorso.put("numeroMinimoPartecipanti",
					prenotazione.getListaSpecifichePrenotazione().get(0).getSogliaPartecipantiPerConferma());
			infoGeneraliCorso.put("numeroMassimoPartecipanti",
					prenotazione.getListaSpecifichePrenotazione().get(0).getSogliaMassimaPartecipanti());
			infoGeneraliCorso.put("costoPerPartecipante",
					prenotazione.getListaSpecifichePrenotazione().get(0).getCosto());

			PrenotazioneDTO prenotazioneDTO = getMapperFactory().getPrenotazioneMapper()
					.convertiInPrenotazioneDTO(prenotazione, appuntamentiPrenotazione, infoGeneraliCorso);

			listaCorsi.add(prenotazioneDTO);
		}

		mappaCorsiDisponibili.put("corsiDisponibili", listaCorsi);

		return mappaCorsiDisponibili;
	}

	@Override
	public PrenotazioneDTO impostaDatiPrenotazione(FormPrenotabile formDati, EffettuaPrenotazioneHandler controller) {

		/*
		 * Creriamo la specifica del corso e la associamo alla prenotazione in atto
		 * tramite il controller.
		 */
		PrenotazioneCorsoSpecs prenotazioneSpecs = new PrenotazioneCorsoSpecs();
		controller.getPrenotazioneInAtto().aggiungiSpecifica(prenotazioneSpecs);

		/*
		 * Creiamo l'oggetto descrizione del corso da passare alla specifiche delle
		 * lezioni e del corso. Nel caso dei corsi infatti avremo speciche di LEZIONI ma
		 * con descrizioni del corso anziche delle LEZIONI
		 */
		PrenotabileDescrizione descrizioneCorso = getCatalogoPrenotabili()
				.nuovoPrenotabile_avviaCreazione(
						this.getRegistroSport().getSportByNome((String) formDati.getValoriForm().get("sport")),
						controller.getTipoPrenotazioneInAtto(),
						(Integer) formDati.getValoriForm().get("numeroMinimoPartecipanti"),
						(Integer) formDati.getValoriForm().get("numeroMassimoPartecipanti"))
				.nuovoPrenotabile_impostaCostoUnaTantum((Float) formDati.getValoriForm().get("costoPerPartecipante"))
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

		/*
		 * Inizializziamo una lista di specifiche che useremo per passare le specifiche
		 * delle LEZIONI alla specifica del CORSO.
		 */
		List<PrenotazioneSpecs> listaLezioniSpecs = new ArrayList<PrenotazioneSpecs>();

		/*
		 * Cicliamo sugli orari impostati in fase di compilazione, per creare e
		 * impostare tante specifiche di LEZIONI con relativi appuntamenti. Aggiungiamo
		 * le specifiche così impostate alla lista inizializzata prima. Infine associamo
		 * gli appuntamenti al controller.
		 */
		for (OrarioAppuntamento orario : (List<OrarioAppuntamento>) formDati.getValoriForm()
				.get("listaOrariAppuntamenti")) {

			PrenotazioneLezioneSpecs prenotazioneLezioneSpecs = (PrenotazioneLezioneSpecs) getElementiPrenotazioneFactory()
					.getPrenotazioneSpecs(TipiPrenotazione.LEZIONE.toString());
			getStatoControllerLezioni().impostaValoriPrenotazioniSpecs(formDati, prenotazioneLezioneSpecs,
					descrizioneCorso, controller, orario);

			listaLezioniSpecs.add(prenotazioneLezioneSpecs);

			AppuntamentoSingoliPartecipanti appuntamento = (AppuntamentoSingoliPartecipanti) getElementiPrenotazioneFactory()
					.getAppuntamento();
			getStatoControllerLezioni().impostaValoriAppuntamento(formDati, controller, appuntamento,
					prenotazioneLezioneSpecs, orario);

			controller.aggiungiAppuntamento(appuntamento);
		}

		/*
		 * Impostiamo i valori della specifica del corso
		 */
		impostaValoriPrenotazioneCorsoSpecs(formDati, prenotazioneSpecs, descrizioneCorso, listaLezioniSpecs,
				controller);

		/*
		 * Convertiamo la lista degli invitati al corso in DTO, per passarla poi alla
		 * prenotazioneDTO che andremo a ritornare alla fine.
		 */
		List<UtentePolisportivaDTO> invitatiDTO = new ArrayList<UtentePolisportivaDTO>();
		for (UtentePolisportivaAbstract invitato : prenotazioneSpecs.getInvitati()) {
			UtentePolisportivaDTO invitatoDTO = getMapperFactory().getUtenteMapper()
					.convertiInUtentePolisportivaDTO(invitato);
			invitatiDTO.add(invitatoDTO);
		}

		/*
		 * Creiamo e impostiamo la PrenotazioneDTO che andremo a ritornare.
		 */
		Map<String, Object> infoGeneraliEvento = new HashMap<String, Object>();
		infoGeneraliEvento.put("numeroMinimoPartecipanti", descrizioneCorso.getMinimoNumeroPartecipanti());
		infoGeneraliEvento.put("numeroMassimoPartecipanti", descrizioneCorso.getMassimoNumeroPartecipanti());
		infoGeneraliEvento.put("invitatiCorso", invitatiDTO);
		infoGeneraliEvento.put("costoPerPartecipante", prenotazioneSpecs.getCosto());

		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper().convertiInPrenotazioneDTO(
				controller.getPrenotazioneInAtto(), controller.getListaAppuntamentiPrenotazioneInAtto(),
				infoGeneraliEvento);

		return prenDTO;

	}

	/**
	 * Impostiamo i dati della specifica del corso.
	 * 
	 * @param formDati          form su cui sono mappati i dati ricevuti dal client.
	 * @param prenotazioneSpecs la specifica del corso da impostare.
	 * @param descrizioneCorso  descrizione del corso da passare alla specificia del
	 *                          corso.
	 * @param listaLezioniSpecs lista delle specifiche delle lezioni del corso, già
	 *                          impostate, da inserire nella specifica del corso.
	 * @param controller        riferimento al controller prinicipale, necessario
	 *                          per rivcavare la prenotazione in atto da associare
	 *                          alla specifica del corso.
	 */
	private void impostaValoriPrenotazioneCorsoSpecs(FormPrenotabile formDati, PrenotazioneCorsoSpecs prenotazioneSpecs,
			PrenotabileDescrizione descrizioneCorso, List<PrenotazioneSpecs> listaLezioniSpecs,
			EffettuaPrenotazioneHandler controller) {
		prenotazioneSpecs.setListaLezioni(listaLezioniSpecs);

		List<UtentePolisportivaAbstract> listaInvitati = new ArrayList<UtentePolisportivaAbstract>();
		for (String emailInvitato : (List<String>) formDati.getValoriForm().get("invitati")) {
			UtentePolisportivaAbstract invitato = this.getRegistroUtenti().getUtenteByEmail(emailInvitato);
			listaInvitati.add(invitato);
		}
		prenotazioneSpecs.setInvitati(listaInvitati);

		prenotazioneSpecs.setSpecificaDescription(descrizioneCorso);

		/*
		 * Impostiamo il costo della specifica che rappresenta il corso con il costo per
		 * partecipante impostato nella form, dopodiché sovrascriviamo i costi delle
		 * singole lezioni impostandoli a zero. Quando verrà aggiunto un partecipante
		 * verrà creata una quota di partecipazione, passata poi a tutti gli
		 * appuntamenti, con il costo della specifica del corso.
		 */
		prenotazioneSpecs.setCosto((Float) formDati.getValoriForm().get("costoPerPartecipante"));
		for (PrenotazioneSpecs specs : listaLezioniSpecs) {
			specs.setCosto(prenotazioneSpecs.getCosto());
		}
		prenotazioneSpecs.setPrenotazioneAssociata(controller.getPrenotazioneInAtto());
		prenotazioneSpecs.setPending(true);
	}

	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandler controller) {
		this.statoControllerLezioni.aggiornaElementiDopoConfermaPrenotazione(controller);

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

	@Override
	public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
		Map<String, Object> mappaAggiornata = this.getStatoControllerLezioni().aggiornaOpzioniPrenotazione(dati);

		Map<String, String> orario = (Map<String, String>) dati.get("orario");
		LocalDateTime oraInizio = LocalDateTime.parse(orario.get("oraInizio"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
		LocalDateTime oraFine = LocalDateTime.parse(orario.get("oraFine"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));

		mappaAggiornata.put("sportiviInvitabili", getSportiviLiberiInBaseAOrario(oraInizio, oraFine));

		return mappaAggiornata;
	}

	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, Object identificativoPartecipante) {
		String emailPartecipante = (String) identificativoPartecipante;
		Prenotazione corsoPrenotazione = this.getRegistroPrenotazioni().getPrenotazioneById(idEvento);
		List<Appuntamento> listaAppuntamentiCorso = this.getRegistroAppuntamenti()
				.getAppuntamentiByPrenotazioneId(idEvento);

		UtentePolisportivaAbstract nuovoPartecipante = this.getRegistroUtenti().getUtenteByEmail(emailPartecipante);
		Appuntamento appuntamentoPerCreazioneQuota = listaAppuntamentiCorso.get(0);
		List<UtentePolisportivaAbstract> partecipantiAttuali = appuntamentoPerCreazioneQuota.getUtentiPartecipanti();
		partecipantiAttuali.add(nuovoPartecipante);
		List<QuotaPartecipazione> quotePartecipazione = new ArrayList<QuotaPartecipazione>();
		for (UtentePolisportivaAbstract partecipante : partecipantiAttuali) {
			QuotaPartecipazione quotaPartecipazione = this.creaQuotaPartecipazione(appuntamentoPerCreazioneQuota,
					partecipante);
			quotePartecipazione.add(quotaPartecipazione);
		}
		boolean partecipanteAggiunto = false;
		for (Appuntamento appuntamento : listaAppuntamentiCorso) {
			partecipanteAggiunto = this
					.aggiungiPartecipante(this.getRegistroUtenti().getUtenteByEmail(emailPartecipante), appuntamento);
		}

		if (partecipanteAggiunto && appuntamentoPerCreazioneQuota.isConfermato()) {
			for (Appuntamento appuntamento : listaAppuntamentiCorso) {
				for (QuotaPartecipazione quota : quotePartecipazione) {
					appuntamento.aggiungiQuotaPartecipazione(quota);
				}
			}
		}
		
		for(Appuntamento appuntamento : listaAppuntamentiCorso) {
			getRegistroAppuntamenti().aggiornaAppuntamento(appuntamento);
		}

		Calendario calendarioSportivo = new Calendario();
		for(Appuntamento appuntamento : listaAppuntamentiCorso) {
			calendarioSportivo.aggiungiAppuntamento(appuntamento);
		}
		getRegistroUtenti().aggiornaCalendarioSportivo(calendarioSportivo, nuovoPartecipante);
		
		Map<String, Object> infoGeneraliCorso = new HashMap<String, Object>();
		infoGeneraliCorso.put("numeroMinimoPartecipanti",
				corsoPrenotazione.getListaSpecifichePrenotazione().get(0).getSogliaPartecipantiPerConferma());
		infoGeneraliCorso.put("numeroMassimoPartecipanti",
				corsoPrenotazione.getListaSpecifichePrenotazione().get(0).getSogliaMassimaPartecipanti());
		infoGeneraliCorso.put("costoPerPartecipante",
				corsoPrenotazione.getListaSpecifichePrenotazione().get(0).getCosto());

		PrenotazioneDTO prenotazioneDTO = getMapperFactory().getPrenotazioneMapper()
				.convertiInPrenotazioneDTO(corsoPrenotazione, listaAppuntamentiCorso, infoGeneraliCorso);
		return prenotazioneDTO;
	}

	@Override
	public Map<String, Object> getDatiOpzioniModalitaDirettore(EffettuaPrenotazioneHandler controller) {
		setStatoControllerLezioni((EffettuaPrenotazioneLezioneState) getElementiPrenotazioneFactory()
				.getStatoEffettuaPrenotazioneHandler(TipiPrenotazione.LEZIONE.toString()));
		getStatoControllerLezioni().setMapperFactory(getMapperFactory());

		Map<String, Object> mappaDati = this.getStatoControllerLezioni().getDatiOpzioni(controller);
		mappaDati.put("sportiviInvitabili", this.getSportiviPolisportiva());

		return mappaDati;
	}

	@Override
	protected boolean aggiungiPartecipante(Object utente, Appuntamento appuntamento) {
		boolean partecipanteAggiunto = false;
		if (appuntamento.getPartecipantiAppuntamento().size() < appuntamento.getNumeroPartecipantiMassimo()) {
			appuntamento.aggiungiPartecipante(utente);
			partecipanteAggiunto = true;
			if (!appuntamento.isConfermato() && appuntamento.getPartecipantiAppuntamento().size() >= appuntamento
					.getSogliaMinimaPartecipantiPerConferma()) {
				appuntamento.confermaAppuntamento();

			}
		}
		return partecipanteAggiunto;
	}

}
