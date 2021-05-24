package it.univaq.esc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.SportivoDTO;
import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Setter;
import net.bytebuddy.asm.Advice.This;
import it.univaq.esc.model.RegistroAppuntamenti;
import it.univaq.esc.model.costi.PrenotabileDescrizione;

@RestController
@RequestMapping("/aggiornaOpzioni")
public class ControllerAggiornaOpzioniPrenotazioneREST {

	@Autowired
	private RegistroUtentiPolisportiva registroUtentiPolisportiva;

	@Autowired
	private RegistroPrenotazioni registroPrenotazioni;

	@Autowired
	private RegistroAppuntamenti registroAppuntamenti;

	/**
	 * Ricava uno sportivo registrato nel sistema a partire dalla email associata,
	 * resituendolo nel relativo formato DTO.
	 * 
	 * @param email email dello sportivo da ricavare
	 * @return sportivo associato alla email passata come parametro
	 */
	@GetMapping("/sportivo")
	@CrossOrigin
	public @ResponseBody SportivoDTO getSportivo(@RequestParam(name = "email") String email) {
		SportivoDTO sportivoDTO = new SportivoDTO();
		sportivoDTO.impostaValoriDTO(registroUtentiPolisportiva.getUtenteByEmail(email));
		return sportivoDTO;
	}

	/**
	 * Restituisce una mappa con le prenotazioni effettuate dallo sportivo e le sue
	 * partecipazioni, nei formati DTO corrispondenti. ovvero gli appuntamenti non
	 * appartenenti a sue prenotazioni dei quali però è un partecipante.
	 * 
	 * @param email email dell'utente di cui ricavare i dati.
	 * @return prenotazioni effettuate e partecipazioni dell'utente registrato con
	 *         l'email passata come parametro.
	 */
	@GetMapping("/prenotazioniEPartecipazioniSportivo")
	@CrossOrigin
	public @ResponseBody Map<String, Object> getAppuntamentiSportivo(@RequestParam(name = "email") String email) {

		/*
		 * Ricaviamo la lista delle prenotazioni effettuate dall'utente, trasformandola
		 * in DTO.
		 */
		List<PrenotazioneDTO> prenotazioni = new ArrayList<PrenotazioneDTO>();
		if (!this.registroPrenotazioni.getPrenotazioniByEmailSportivo(email).isEmpty()) {
			for (Prenotazione pren : registroPrenotazioni.escludiPrenotazioniPerTipo(registroPrenotazioni.getPrenotazioniByEmailSportivo(email), TipiPrenotazione.CORSO.toString())) {
				PrenotazioneDTO prenDTO = new PrenotazioneDTO();
				List<Appuntamento> listaAppuntamnenti = new ArrayList<Appuntamento>();
				for (PrenotazioneSpecs spec : pren.getListaSpecifichePrenotazione()) {
					listaAppuntamnenti.add(registroAppuntamenti.getAppuntamentoBySpecificaAssociata(spec));
				}
				Map<String, Object> mappa = new HashMap<String, Object>();
				mappa.put("prenotazione", pren);
				mappa.put("appuntamentiPrenotazione", listaAppuntamnenti);
				prenDTO.impostaValoriDTO(mappa);
				prenotazioni.add(prenDTO);
			}
		}

		/*
		 * Ricaviamo la lista degli appuntamenti non creati dallo sportivo, ma dei quali
		 * è un partecipante, trasformandola in DTO.
		 */
		UtentePolisportivaAbstract sportivo = this.registroUtentiPolisportiva.getUtenteByEmail(email);
		List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();
		System.out.println("APP: " + this.registroAppuntamenti.getListaAppuntamenti());
		if (!this.registroAppuntamenti.escludiAppuntamentiDiCorsi(this.registroAppuntamenti.getAppuntamentiPerPartecipanteNonCreatore(sportivo)).isEmpty()) {
			for (Appuntamento appuntamento : this.registroAppuntamenti
					.getAppuntamentiPerPartecipanteNonCreatore(sportivo)) {
				AppuntamentoDTO appDTO = new AppuntamentoDTO();
				appDTO.impostaValoriDTO(appuntamento);
				appuntamenti.add(appDTO);
			}
		}
		
		
		/*
		 * Ricaviamo la lista dei corsi a cui si partecipa, come prenotazioni DTO.
		 * Prima ricaviamo gli appuntamenti riferiti ai corsi, ai quali si partecipa.
		 * Poi ricaviamo le prenotazioni a cui gli appuntamenti si riferiscono e li mettiamo in un Set così che 
		 * vengano inserite una sola volta automaticamente.
		 * Infine trasformiamo il Set in List e la convertiamo in DTO.
		 * 
		 */
		List<Appuntamento> appuntamentiCorsiACuiSiPartecipa = this.registroAppuntamenti.filtraAppuntamentiPerPartecipante(
				this.registroAppuntamenti.filtraAppuntamentiDiCorsi(this.registroAppuntamenti.getListaAppuntamenti()), sportivo);
		Set<Prenotazione> corsiACuiSiPartecipa = new HashSet<Prenotazione>();
		appuntamentiCorsiACuiSiPartecipa.forEach((appuntamento) -> corsiACuiSiPartecipa.add(appuntamento.getPrenotazionePrincipale()));
		List<Prenotazione> listaCorsiACuiSiPartecipa = new ArrayList<Prenotazione>(corsiACuiSiPartecipa);
		
		List<PrenotazioneDTO> listaCorsiACuiSiPartecipaDTO = new ArrayList<PrenotazioneDTO>();
		for(Prenotazione prenotazione : listaCorsiACuiSiPartecipa) {
			Map<String, Object> infoGenerali = new HashMap<String, Object>();
			PrenotazioneSpecs specificaCorso = prenotazione.getListaSpecifichePrenotazione().get(0);
			infoGenerali.put("numeroMinimoParteciapanti", specificaCorso.getSogliaPartecipantiPerConferma());
			infoGenerali.put("numeroMassimoPartecipanti", specificaCorso.getSogliaMassimaPartecipanti());
			infoGenerali.put("costoPerPartecipante", specificaCorso.getCosto());
			
			Map<String, Object> mappaDatiPrenotazioneDTO = new HashMap<String, Object>();
			mappaDatiPrenotazioneDTO.put("prenotazione", prenotazione);
			mappaDatiPrenotazioneDTO.put("appuntamentiPrenotazione", this.registroAppuntamenti.getAppuntamentiByPrenotazioneId(prenotazione.getIdPrenotazione()));
			mappaDatiPrenotazioneDTO.put("infoGeneraliEvento", infoGenerali);
			
			PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
			prenotazioneDTO.impostaValoriDTO(mappaDatiPrenotazioneDTO);
			listaCorsiACuiSiPartecipaDTO.add(prenotazioneDTO);
		}
		
		
		
		
		
		/*
		 * Creiamo la mappa con tutti i dati e la ritorniamo.
		 * "prenotazioniEffettuate": lista delle prenotazioni effettuate dallo sportivo.
		 * "partecipazioni": lista degli appuntamenti non creati dall'utente, ma di cui l'utente è partecipante.
		 * "corsiAcuiSiPartecipa": lista delle prenotazioni di corsi a cui ci si è prenotati.
		 */
		Map<String, Object> mappaPrenotazioniPartecipazioni = new HashMap<String, Object>();
		mappaPrenotazioniPartecipazioni.put("prenotazioniEffettuate", prenotazioni);
		mappaPrenotazioniPartecipazioni.put("partecipazioni", appuntamenti);
		mappaPrenotazioniPartecipazioni.put("corsiACuiSiPartecipa", listaCorsiACuiSiPartecipaDTO);

		return mappaPrenotazioniPartecipazioni;

	}

}
