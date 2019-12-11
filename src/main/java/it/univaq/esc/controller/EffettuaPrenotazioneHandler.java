package it.univaq.esc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Istruttore;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.RegistroPrenotazioni;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.RegistroSportivi;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Sportivo;
import it.univaq.esc.repository.ImpiantoRepository;
import it.univaq.esc.repository.SportRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/esc")
public class EffettuaPrenotazioneHandler {

	private RegistroPrenotazioni registroPrenotazioni; 
	private RegistroSportivi registroSportivi ;
	private RegistroSport registroSport ;
	private Sportivo sportivoPrenotante;
	private Prenotazione nuovaPrenotazione;
	
	@Autowired
	private SportRepository sportRepository;
	
	@Autowired
	private ImpiantoRepository impiantoRepository;
	
	
	//costruttore
	public EffettuaPrenotazioneHandler() {
		super();
		this.registroPrenotazioni = RegistroPrenotazioni.getInstance();
		this.registroSportivi = RegistroSportivi.getInstance();
		this.registroSport = RegistroSport.getInstance();
	}



	@GetMapping("/prenotazione/{IDSportivo}")
	public Object avviaNuovaPrenotazione(@PathVariable int IDSportivo) {
		
		
		
		int LastIDPrenotazione = this.registroPrenotazioni.getLastIDPrenotazione();
		Sportivo sportivoPrenotante = this.registroSportivi.getSportivo(IDSportivo);
		Prenotazione nuovaPrenotazione = sportivoPrenotante.creaNuovaPrenotazione(LastIDPrenotazione);
		
		/**
		 * Ricaviamo gli sport disponibili per la prenotazione.
		 */
		ArrayList<Sport> availableSport = registroSport.getAllSport();
		
		ArrayList<Impianto> availableImpianti = new ArrayList<Impianto>();
		ArrayList<Istruttore> availableIstruttori = new ArrayList<Istruttore>();
		ArrayList<Calendar> listaCalendari = new ArrayList<Calendar>();
		for(Sport sp : availableSport){
			/**
		 	* Ricaviamo gli impianti disponibili per ogni sport.
		 	*/
			for(Impianto imp : sp.getImpianti()){
				if(!availableImpianti.contains(imp)){
					availableImpianti.add(imp);
					/**
			 		* Ricaviamo il Calendar di ogni impianto, con i quali costruiremo un Calendar generale da mostrare nella vista.
			 		*/
					listaCalendari.add(imp.getCalendario());
				}
			}

			/**
		 	* Ricaviamo gli istruttori disponibili per ogni sport. Assumiamo che un istruttore, come tale, sia associato ad un solo sport.
			*/
			for(Istruttore istr : sp.getIstruttori()){
				availableIstruttori.add(istr);
			}

			
		}
		HashMap<String, Object> map = new HashMap<>();
			map.put("Sport", availableSport);
			map.put("Impianti", availableImpianti);
			map.put("Istruttori", availableIstruttori);
			map.put("Calendario", listaCalendari);

		return map;
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
		Sport sport = this.registroSport.getSportDes(etichetta);
		return sport.getIstruttori();
	}
}
