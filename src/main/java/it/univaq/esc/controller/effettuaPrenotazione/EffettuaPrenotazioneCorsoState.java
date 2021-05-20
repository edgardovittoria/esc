package it.univaq.esc.controller.effettuaPrenotazione;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.IFormPrenotabile;
import it.univaq.esc.dtoObjects.OrarioAppuntamento;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.costi.PrenotabileDescrizione;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.prenotazioni.FactorySpecifichePrenotazione;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.TipoRuolo;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;
import net.bytebuddy.asm.Advice.This;


@Component
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE) @AllArgsConstructor
public class EffettuaPrenotazioneCorsoState extends EffettuaPrenotazioneState{
	
	private EffettuaPrenotazioneLezioneState statoControllerLezioni;
	
	
	/**
     * Blocco static.
     * La prima volta che viene caricata la classe, la registra nella FactoryStatoEffettuaPrenotazione
     */
    static {
    	FactoryStatoEffettuaPrenotazione.registra(TipiPrenotazione.CORSO.toString(), EffettuaPrenotazioneCorsoState.class);
    }

	@Override
	public Map<String, Object> getDatiOpzioni(EffettuaPrenotazioneHandlerRest controller) {
		Map<String, Object> mappaCorsiDisponibili = new HashMap<String, Object>();
		List<Prenotazione> corsiDisponibili = this.getRegistroPrenotazioni().filtraPrenotazioniPerTipo(
				this.getRegistroPrenotazioni().getPrenotazioniRegistrate(), 
				TipiPrenotazione.CORSO.toString());
		List<PrenotazioneDTO> listaCorsi = new ArrayList<PrenotazioneDTO>();
		for(Prenotazione prenotazione : corsiDisponibili) {
			List<Appuntamento> appuntamentiPrenotazione = this.getRegistroAppuntamenti().getAppuntamentiByPrenotazioneId(prenotazione.getIdPrenotazione());
			Map<String, Object> mappaPrenotazione = new HashMap<String, Object>();
			mappaPrenotazione.put("prenotazione", prenotazione);
			mappaPrenotazione.put("appuntamentiPrenotazione", appuntamentiPrenotazione);
			PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
			prenotazioneDTO.impostaValoriDTO(mappaPrenotazione);
			listaCorsi.add(prenotazioneDTO);
		}
		
		mappaCorsiDisponibili.put("corsiDisponibili", listaCorsi);
		
		return mappaCorsiDisponibili;
	}

	@Override
	public void impostaDatiPrenotazione(IFormPrenotabile formDati, EffettuaPrenotazioneHandlerRest controller) {
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

			Appuntamento appuntamento = new Appuntamento();
			appuntamento.setPrenotazioneSpecsAppuntamento(prenotazioneLezioneSpecs);
			appuntamento.setCalcolatoreCosto(calcolatoreCosto);

			controller.aggiungiAppuntamento(appuntamento);
		}
		mappa.put("listaLezioniCorso", listaLezioniSpecs);
		
		List<UtentePolisportivaAbstract> listaInvitati = new ArrayList<UtentePolisportivaAbstract>();
		for(String emailInvitato : (List<String>)formDati.getValoriForm().get("invitati")) {
			UtentePolisportivaAbstract invitato = this.getRegistroUtenti().getUtenteByEmail(emailInvitato);
			listaInvitati.add(invitato);
		}
		mappa.put("invitati", listaInvitati);
		prenotazioneSpecs.impostaValoriSpecificheExtraPrenotazione(mappa);
	
		PrenotabileDescrizione descrizioneCorso = controller.getListinoPrezziDescrizioniPolisportiva().avviaCreazionePrenotabile(
				this.getRegistroSport().getSportByNome((String)formDati.getValoriForm().get("sport")),
				controller.getTipoPrenotazioneInAtto(), (Integer)formDati.getValoriForm().get("sogliaMinimaPartecipanti"),
				(Integer)formDati.getValoriForm().get("sogliaMassimaPartecipanti"))
				.impostaCostoUnaTantum((Float)formDati.getValoriForm().get("costoPerPartecipante"))
				.salvaPrenotabileInCreazione();
		
		prenotazioneSpecs.setSpecificaDescription(descrizioneCorso);
		
		
		this.getStatoControllerLezioni().impostaValoriPrenotazioniSpecs(formDati, TipiPrenotazione.LEZIONE.toString(), listaLezioniSpecs, controller);
		/*
		 * Impostiamo il costo della specifica che rappresenta il corso con il costo per partecipante impostato nella form, 
		 * dopodiché sovrascriviamo i costi delle singole lezioni impostandoli a zero.
		 * Quando verrà aggiunto un partecipante verrà creata una quota di partecipazione, passata poi a tutti gli appuntamenti, con
		 * il costo della specifica del corso.
		 */
		prenotazioneSpecs.setCosto((Float)formDati.getValoriForm().get("costoPerPartecipante"));
		for(PrenotazioneSpecs specs : listaLezioniSpecs) {
			specs.setCosto(prenotazioneSpecs.getCosto());
		}
		
		
		
	}

	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandlerRest controller) {
		this.statoControllerLezioni.aggiornaElementiDopoConfermaPrenotazione(controller);
		
	}

	@Override
	public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
		return this.getStatoControllerLezioni().aggiornaOpzioniPrenotazione(dati);
	}

	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, String emailPartecipante) {
		List<Appuntamento> listaAppuntamentiCorso = this.getRegistroAppuntamenti().getAppuntamentiByPrenotazioneId(idEvento);
		for(Appuntamento appuntamento : listaAppuntamentiCorso) {
			this.aggiungiPartecipante(this.getRegistroUtenti().getUtenteByEmail(emailPartecipante), appuntamento);
		}
		
		PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
		prenotazioneDTO.impostaValoriDTO(this.getRegistroPrenotazioni().getPrenotazioneById(idEvento));
		return prenotazioneDTO;
	}

	@Override
	public Map<String, Object> getDatiOpzioniModalitaDirettore(EffettuaPrenotazioneHandlerRest controller) {
		Map<String, Object> mappaDati = this.getStatoControllerLezioni().getDatiOpzioni(controller);
		mappaDati.put("sportiviInvitabili", this.getSportiviPolisportiva());
		
		return mappaDati;
	}

}
