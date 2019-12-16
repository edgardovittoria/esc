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
	
	
	private Sportivo sportivoPrenotante;
	private Prenotazione nuovaPrenotazione;
	
	
	
	
	
	
	//costruttore
	public EffettuaPrenotazioneHandler() {
		//this.registroPrenotazioni = RegistroPrenotazioni.getInstance();
		//this.registroSportivi = RegistroSportivi.getInstance();
		//this.registroSport = RegistroSport.getInstance();
	}


	


	@GetMapping("/prenotazione/{IDSportivo}")
	public String avviaNuovaPrenotazione(@PathVariable int IDSportivo, Model results) throws Exception {
		// Logger loggerAvviaPren = LoggerFactory.getLogger(EffettuaPrenotazioneHandler.class);
		// loggerAvviaPren.info(""+IDSportivo);
		
		try {
			// loggerAvviaPren.info(""+registroPrenotazioni.getLastIDPrenotazione());
			int LastIDPrenotazione = prenotazioneService.getLastIDPrenotazione();
			sportivoPrenotante = sportivoService.getSportivo(IDSportivo);
			// loggerAvviaPren.info(sportivoPrenotante.getNome());
			nuovaPrenotazione = sportivoService.creaNuovaPrenotazione(LastIDPrenotazione, sportivoPrenotante);

			/**
			 * Ricaviamo gli sport disponibili per la prenotazione.
			 */
			List<SportDTO> availableSport = sportService.toDTO(sportService.getAllSport());

			List<Impianto> availableImpianti = impiantoService.getAllImpianti();
			ArrayList<Istruttore> availableIstruttori = istruttoreService.getIstruttori();
			ArrayList<Calendar> listaCalendari = new ArrayList<Calendar>();

			for (Impianto imp : availableImpianti) {
				listaCalendari.add(imp.getCalendario());
			}

			
			results.addAttribute("Sport", availableSport);
			results.addAttribute("Impianti", availableImpianti);
			results.addAttribute("Istruttori", availableIstruttori);
			results.addAttribute("Calendario", listaCalendari);			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "newPrenotazione";
	}
	
	@GetMapping("/impianti")
	public List<Impianto> aggiornaOpzioneSport(@RequestParam("etichetta") String etichetta){
		
		//sfruttando il registro Sport
		Sport sport = sportService.getSport(etichetta);
		return sport.getImpianti();
	}
	
	@GetMapping("/calendario/{etichetta}")
	public void aggiornaOpzioneCalendario(@PathVariable String etichetta) {
		
	}
	
	@GetMapping("/istruttore/{etichetta}")
	public List<Istruttore> aggiornaOpzioneIstruttore(@PathVariable String etichetta) {
		//Sport sport = this.registroSport.getSportDes(etichetta);
		Sport sport = sportService.getSport(etichetta);
		return sport.getIstruttori();
	}
}
