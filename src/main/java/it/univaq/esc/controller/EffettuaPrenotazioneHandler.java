package it.univaq.esc.controller;

import org.springframework.web.bind.annotation.GetMapping;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
@RestController
@RequestMapping("/esc")
public class EffettuaPrenotazioneHandler {

	private RegistroPrenotazioni registroPrenotazioni;
	private RegistroSportivi registroSportivi;
	private RegistroSport registroSport;
	private Sportivo sportivoPrenotante;
	private Prenotazione nuovaPrenotazione;
	
	
	//costruttore
	public EffettuaPrenotazioneHandler(RegistroSport registroSport, RegistroPrenotazioni registroPrenotazioni, RegistroSportivi registroSportivi) {
		this.registroPrenotazioni = registroPrenotazioni;
		this.registroSportivi = registroSportivi;
		this.registroSport = registroSport;
	}



	@GetMapping("/prenotazione/")
	public HashMap<String, Object> avviaNuovaPrenotazione(@RequestParam("IDSportivo") int IDSportivo) {
		
		int LastIDPrenotazione = this.registroPrenotazioni.getLastIDPrenotazione();
		sportivoPrenotante = this.registroSportivi.getSportivo(IDSportivo);
		nuovaPrenotazione = sportivoPrenotante.creaNuovaPrenotazione(LastIDPrenotazione);
		
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
		return mapOptionsForPrenotazione(availableSport, availableImpianti, availableIstruttori, listaCalendari);
		
	}

	private HashMap<String, Object> mapOptionsForPrenotazione(ArrayList<Sport> sport, ArrayList<Impianto> impianti, ArrayList<Istruttore> istruttori, ArrayList<Calendar> calendari) {
		HashMap<String, Object> map = new HashMap<>();
			map.put("Sport", sport);
			map.put("Impianti", impianti);
			map.put("Istruttori", istruttori);
			map.put("Calendario", calendari);

		return map;
	}
}
