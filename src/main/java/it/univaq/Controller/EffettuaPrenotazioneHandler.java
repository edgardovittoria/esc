package it.univaq.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;

import it.univaq.Model.Prenotazione;
import it.univaq.Model.RegistroPrenotazioni;
import it.univaq.Model.RegistroSportivi;
import it.univaq.Model.Sportivo;
import it.univaq.Model.RegistroSport;
import it.univaq.Model.Calendario;
import java.util.ArrayList;
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
		super();
		this.registroPrenotazioni = registroPrenotazioni;
		this.registroSportivi = registroSportivi;
		this.registroSport = registroSport;
	}



	@GetMapping("/prenotazione/")
	public View avviaNuovaPrenotazione(@RequestParam("IDSportivo") int IDSportivo) {
		
		int LastIDPrenotazione = this.registroPrenotazioni.getLastIDPrenotazione();
		Sportivo sportivoPrenotante = this.registroSportivi.getSportivo(IDSportivo);
		nuovaPrenotazione = sportivoPrenotante.creaNuovaPrenotazione(LastIDPrenotazione);
		
		/**
		 * Ricaviamo gli sport disponibili per la prenotazione.
		 */
		ArrayList<Sport> availableSport = registroSport.getAllSport();
		
		ArrayList<Impianto> availableImpianti = new ArrayList<Impianto>();
		ArrayList<Istruttore> availableIstruttori = new ArrayList<Istruttore>();
		ArrayList<Calendario> listaCalendari = new ArrayList<Calendario>();
		for(Sport sp : availableSport){
			/**
		 	* Ricaviamo gli impianti disponibili per ogni sport.
		 	*/
			for(Impianto imp : sp.getImpianti()){
				if(!availableImpianti.contains(imp)){
					availableImpianti.add(imp);
					/**
			 		* Ricaviamo il calendario di ogni impianto, con i quali costruiremo un calendario generale da mostrare nella vista.
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

		return null;
	}
}
