package it.univaq.esc.controller;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.univaq.esc.dto.SportDTO;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Istruttore;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Sportivo;
import it.univaq.esc.services.ImpiantoService;
import it.univaq.esc.services.IstruttoreService;
import it.univaq.esc.services.PrenotazioneService;
import it.univaq.esc.services.SportService;
import it.univaq.esc.services.SportivoService;
import it.univaq.esc.utility.SimpleFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Controller
@RequestMapping("/esc")
public class EffettuaPrenotazioneHandler {

	@Autowired
	private SportService sportService;
	@Autowired
	private ImpiantoService impiantoService;
	@Autowired
	private PrenotazioneService prenotazioneService;
	@Autowired
	private SportivoService sportivoService;
	@Autowired
	private IstruttoreService istruttoreService;
	@Autowired
	private SimpleFactory simpleFactory;
	
	
	private Sportivo sportivoPrenotante;
	private Prenotazione nuovaPrenotazione;
	
	
	
	
	
	
	
	//costruttore
	public EffettuaPrenotazioneHandler() {	}


	


	@GetMapping("/prenotazione/{IDSportivo}")
	public Model avviaNuovaPrenotazione(@PathVariable int IDSportivo, Model results) throws Exception {		
		try {
			
			int LastIDPrenotazione = prenotazioneService.getLastIDPrenotazione();
			sportivoPrenotante = sportivoService.getSportivo(IDSportivo);
			nuovaPrenotazione = this.simpleFactory.getNuovaPrenotazione(LastIDPrenotazione, sportivoPrenotante);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.simpleFactory.getOpzioni(impiantoService, sportivoService, sportService, istruttoreService, prenotazioneService, results);
	}
	
	
}
