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
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Istruttore;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroIstruttori;
import it.univaq.esc.model.RegistroPrenotazioni;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.RegistroSportivi;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Sportivo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Controller
@RequestMapping("/esc")
public class EffettuaPrenotazioneHandler {

	@Autowired
	private RegistroPrenotazioni registroPrenotazioni; 
	@Autowired
	private RegistroSportivi registroSportivi ;
	@Autowired
	private RegistroSport registroSport ;
	@Autowired
	private RegistroImpianti registroImpianti;
	@Autowired
	private RegistroIstruttori registroIstruttori;
	
	
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
			int LastIDPrenotazione = registroPrenotazioni.getLastIDPrenotazione();
			sportivoPrenotante = registroSportivi.getSportivo(IDSportivo);
			// loggerAvviaPren.info(sportivoPrenotante.getNome());
			nuovaPrenotazione = sportivoPrenotante.creaNuovaPrenotazione(LastIDPrenotazione);

			/**
			 * Ricaviamo gli sport disponibili per la prenotazione.
			 */
			List<Sport> availableSport = registroSport.getAllSport();

			List<Impianto> availableImpianti = registroImpianti.getAllImpianti();
			ArrayList<Istruttore> availableIstruttori = registroIstruttori.getIstruttori();
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
		Sport sport = this.registroSport.getSportDes(etichetta);
		return sport.getImpianti();
	}
	
	@GetMapping("/calendario/{etichetta}")
	public void aggiornaOpzioneCalendario(@PathVariable String etichetta) {
		
	}
	
	@GetMapping("/istruttore/{etichetta}")
	public List<Istruttore> aggiornaOpzioneIstruttore(@PathVariable String etichetta) {
		//Sport sport = this.registroSport.getSportDes(etichetta);
		Sport sport = registroSport.getSport(etichetta);
		return sport.getIstruttori();
	}
}
