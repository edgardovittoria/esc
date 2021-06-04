package it.univaq.esc.controller.effettuaPrenotazione;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.OrarioAppuntamento;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.costi.CatalogoPrenotabili;
import it.univaq.esc.model.costi.PrenotabileDescrizione;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.FactorySpecifichePrenotazione;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.TipoRuolo;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;
import net.bytebuddy.asm.Advice.This;

@Component
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)

public class EffettuaPrenotazioneCorsoState extends EffettuaPrenotazioneState {

	private EffettuaPrenotazioneLezioneState statoControllerLezioni;

	/**
	 * Blocco static. La prima volta che viene caricata la classe, la registra nella
	 * FactoryStatoEffettuaPrenotazione
	 */
	static {
		FactoryStatoEffettuaPrenotazione.registra(TipiPrenotazione.CORSO.toString(),
				EffettuaPrenotazioneCorsoState.class);
	}
	
	public EffettuaPrenotazioneCorsoState(RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtentiPolisportiva,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, EffettuaPrenotazioneLezioneState effettuaPrenotazioneLezioneState) {
		
		super(registroNotifiche, registroSport, registroImpianti, registroUtentiPolisportiva, registroAppuntamenti, registroPrenotazioni, catalogoPrenotabili);
		setStatoControllerLezioni(effettuaPrenotazioneLezioneState);
	}

	@Override
	public Map<String, Object> getDatiOpzioni(EffettuaPrenotazioneHandlerRest controller) {
		Map<String, Object> mappaCorsiDisponibili = new HashMap<String, Object>();
		List<Prenotazione> corsiDisponibili = this.getRegistroPrenotazioni().filtraPrenotazioniPerTipo(
				this.getRegistroPrenotazioni().getPrenotazioniRegistrate(), TipiPrenotazione.CORSO.toString());

		List<PrenotazioneDTO> listaCorsi = new ArrayList<PrenotazioneDTO>();
		for (Prenotazione prenotazione : corsiDisponibili) {
			List<Appuntamento> appuntamentiPrenotazione = this.getRegistroAppuntamenti()
					.getAppuntamentiByPrenotazioneId(prenotazione.getIdPrenotazione());
			Map<String, Object> mappaPrenotazione = new HashMap<String, Object>();
			mappaPrenotazione.put("prenotazione", prenotazione);
			mappaPrenotazione.put("appuntamentiPrenotazione", appuntamentiPrenotazione);
			Map<String, Object> infoGeneraliCorso = new HashMap<String, Object>();
			infoGeneraliCorso.put("numeroMinimoPartecipanti",
					prenotazione.getListaSpecifichePrenotazione().get(0).getSogliaPartecipantiPerConferma());
			infoGeneraliCorso.put("numeroMassimoPartecipanti",
					prenotazione.getListaSpecifichePrenotazione().get(0).getSogliaMassimaPartecipanti());
			infoGeneraliCorso.put("costoPerPartecipante",
					prenotazione.getListaSpecifichePrenotazione().get(0).getCosto());
			mappaPrenotazione.put("infoGeneraliEvento", infoGeneraliCorso);
			PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
			prenotazioneDTO.impostaValoriDTO(mappaPrenotazione);
			listaCorsi.add(prenotazioneDTO);
		}

		mappaCorsiDisponibili.put("corsiDisponibili", listaCorsi);

		return mappaCorsiDisponibili;
	}

	@Override
	public PrenotazioneDTO impostaDatiPrenotazione(FormPrenotabile formDati,
			EffettuaPrenotazioneHandlerRest controller) {
		PrenotazioneSpecs prenotazioneSpecs = FactorySpecifichePrenotazione
				.getSpecifichePrenotazione(controller.getTipoPrenotazioneInAtto());
		controller.getPrenotazioneInAtto().aggiungiSpecifica(prenotazioneSpecs);
		List<PrenotazioneSpecs> listaLezioniSpecs = new ArrayList<PrenotazioneSpecs>();
		Map<String, Object> mappa = new HashMap<String, Object>();
		for (int i = 0; i < ((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti"))
				.size(); i++) {
			PrenotazioneSpecs prenotazioneLezioneSpecs = FactorySpecifichePrenotazione
					.getSpecifichePrenotazione(TipiPrenotazione.LEZIONE.toString());
			listaLezioniSpecs.add(prenotazioneLezioneSpecs);

			prenotazioneLezioneSpecs.setPrenotazioneAssociata(controller.getPrenotazioneInAtto());

			// Creazione calcolatore che poi dovrà finire altrove
			CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
			calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());
			// ---------------------------------------------------------------------------------------

			Appuntamento appuntamento = getRegistroAppuntamenti().getFactoryAppuntamenti().getAppuntamento(formDati.getModalitaPrenotazione());
			appuntamento.setPrenotazioneSpecsAppuntamento(prenotazioneLezioneSpecs);
			appuntamento.setCalcolatoreCosto(calcolatoreCosto);

			controller.aggiungiAppuntamento(appuntamento);
		}
		mappa.put("listaLezioniCorso", listaLezioniSpecs);

		List<UtentePolisportivaAbstract> listaInvitati = new ArrayList<UtentePolisportivaAbstract>();
		for (String emailInvitato : (List<String>) formDati.getValoriForm().get("invitati")) {
			UtentePolisportivaAbstract invitato = this.getRegistroUtenti().getUtenteByEmail(emailInvitato);
			listaInvitati.add(invitato);
		}
		mappa.put("invitati", listaInvitati);
		prenotazioneSpecs.impostaValoriSpecificheExtraPrenotazione(mappa);

		PrenotabileDescrizione descrizioneCorso = controller.getListinoPrezziDescrizioniPolisportiva()
				.nuovoPrenotabile_avviaCreazione(
						this.getRegistroSport().getSportByNome((String) formDati.getValoriForm().get("sport")),
						controller.getTipoPrenotazioneInAtto(),
						(Integer) formDati.getValoriForm().get("numeroMinimoPartecipanti"),
						(Integer) formDati.getValoriForm().get("numeroMassimoPartecipanti"))
				.nuovoPrenotabile_impostaCostoUnaTantum((Float) formDati.getValoriForm().get("costoPerPartecipante"))
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

		prenotazioneSpecs.setSpecificaDescription(descrizioneCorso);

		this.getStatoControllerLezioni().impostaValoriPrenotazioniSpecs(formDati, descrizioneCorso,
				TipiPrenotazione.LEZIONE.toString(), listaLezioniSpecs, controller);
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

		List<UtentePolisportivaDTO> invitatiDTO = new ArrayList<UtentePolisportivaDTO>();
		for (UtentePolisportivaAbstract invitato : listaInvitati) {
			UtentePolisportivaDTO invitatoDTO = new UtentePolisportivaDTO();
			invitatoDTO.impostaValoriDTO(invitato);
			invitatiDTO.add(invitatoDTO);
		}
		PrenotazioneDTO prenDTO = new PrenotazioneDTO();
		Map<String, Object> mappaPrenotazione = new HashMap<String, Object>();
		Map<String, Object> infoGeneraliEvento = new HashMap<String, Object>();
		infoGeneraliEvento.put("numeroMinimoPartecipanti", descrizioneCorso.getMinimoNumeroPartecipanti());
		infoGeneraliEvento.put("numeroMassimoPartecipanti", descrizioneCorso.getMassimoNumeroPartecipanti());
		infoGeneraliEvento.put("invitatiCorso", invitatiDTO);
		infoGeneraliEvento.put("costoPerPartecipante", prenotazioneSpecs.getCosto());
		mappa.put("prenotazione", controller.getPrenotazioneInAtto());
		mappa.put("appuntamentiPrenotazione", controller.getListaAppuntamentiPrenotazioneInAtto());
		mappa.put("infoGeneraliEvento", infoGeneraliEvento);
		prenDTO.impostaValoriDTO(mappa);

		return prenDTO;

	}

	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandlerRest controller) {
		this.statoControllerLezioni.aggiornaElementiDopoConfermaPrenotazione(controller);

	}

	@Override
	public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
		Map<String, Object> mappaAggiornata = this.getStatoControllerLezioni().aggiornaOpzioniPrenotazione(dati);
		
		Map<String, String> orario = (Map<String, String>)dati.get("orario");
		LocalDateTime oraInizio = LocalDateTime.parse(orario.get("oraInizio"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
		LocalDateTime oraFine = LocalDateTime.parse(orario.get("oraFine"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
		
		mappaAggiornata.put("sportiviInvitabili", getSportiviLiberiInBaseAOrario(oraInizio, oraFine));
		
		return mappaAggiornata;
	}

	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, String emailPartecipante) {
		Prenotazione corsoPrenotazione = this.getRegistroPrenotazioni().getPrenotazioneById(idEvento);
		List<Appuntamento> listaAppuntamentiCorso = this.getRegistroAppuntamenti()
				.getAppuntamentiByPrenotazioneId(idEvento);

		Appuntamento appuntamentoPerCreazioneQuota = listaAppuntamentiCorso.get(0);
		QuotaPartecipazione quotaPartecipazione = this.creaQuotaPartecipazione(appuntamentoPerCreazioneQuota,
				this.getRegistroUtenti().getUtenteByEmail(emailPartecipante));
		for (Appuntamento appuntamento : listaAppuntamentiCorso) {
			boolean partecipanteAggiunto = this
					.aggiungiPartecipante(this.getRegistroUtenti().getUtenteByEmail(emailPartecipante), appuntamento);
			if (partecipanteAggiunto) {
				appuntamento.aggiungiQuotaPartecipazione(quotaPartecipazione);
			}
			this.getRegistroAppuntamenti().aggiornaAppuntamento(appuntamento);
		}

		Map<String, Object> mappaPrenotazione = new HashMap<String, Object>();
		mappaPrenotazione.put("prenotazione", corsoPrenotazione);
		mappaPrenotazione.put("appuntamentiPrenotazione", listaAppuntamentiCorso);
		Map<String, Object> infoGeneraliCorso = new HashMap<String, Object>();
		infoGeneraliCorso.put("numeroMinimoPartecipanti",
				corsoPrenotazione.getListaSpecifichePrenotazione().get(0).getSogliaPartecipantiPerConferma());
		infoGeneraliCorso.put("numeroMassimoPartecipanti",
				corsoPrenotazione.getListaSpecifichePrenotazione().get(0).getSogliaMassimaPartecipanti());
		infoGeneraliCorso.put("costoPerPartecipante",
				corsoPrenotazione.getListaSpecifichePrenotazione().get(0).getCosto());
		mappaPrenotazione.put("infoGeneraliEvento", infoGeneraliCorso);

		PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
		prenotazioneDTO.impostaValoriDTO(mappaPrenotazione);
		return prenotazioneDTO;
	}

	@Override
	public Map<String, Object> getDatiOpzioniModalitaDirettore(EffettuaPrenotazioneHandlerRest controller) {
		Map<String, Object> mappaDati = this.getStatoControllerLezioni().getDatiOpzioni(controller);
		mappaDati.put("sportiviInvitabili", this.getSportiviPolisportiva());

		return mappaDati;
	}

	@Override
	protected boolean aggiungiPartecipante(UtentePolisportivaAbstract utente, Appuntamento appuntamento) {
		boolean partecipanteAggiunto = false;
		if (appuntamento.getPartecipantiAppuntamento().size() < appuntamento.getNumeroPartecipantiMassimo()) {
			appuntamento.aggiungiPartecipante(utente);
			partecipanteAggiunto = true;
			if (appuntamento.getPartecipantiAppuntamento().size() >= appuntamento.getSogliaMinimaPartecipantiPerConferma()) {
				appuntamento.confermaAppuntamento();

			}
		}
		return partecipanteAggiunto;
	}

}
