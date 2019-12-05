package it.univaq.esc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
<<<<<<< HEAD
import org.springframework.web.servlet.View;

import it.univaq.esc.model.Calcetto;
=======
>>>>>>> d521e5a751ea00a76bac002838e1782fa16f193c
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Impianto1;
import it.univaq.esc.model.Istruttore;
import it.univaq.esc.model.Lezione;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.RegistroPrenotazioni;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.RegistroSportivi;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Sportivo;
import it.univaq.esc.model.Tennis;
import it.univaq.utility.SimpleFactory;

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
<<<<<<< HEAD
	/*public EffettuaPrenotazioneHandler(RegistroSport registroSport, RegistroPrenotazioni registroPrenotazioni, RegistroSportivi registroSportivi) {
		super();
=======
	public EffettuaPrenotazioneHandler(RegistroSport registroSport, RegistroPrenotazioni registroPrenotazioni, RegistroSportivi registroSportivi) {
>>>>>>> d521e5a751ea00a76bac002838e1782fa16f193c
		this.registroPrenotazioni = registroPrenotazioni;
		this.registroSportivi = registroSportivi;
		this.registroSport = registroSport;
	}*/



	@GetMapping("/prenotazione/{IDSportivo}")
	public Object avviaNuovaPrenotazione(@PathVariable int IDSportivo) {
		
		//creazione degli ogetti
				Sportivo sportivo = new Sportivo();
				Sport tennis = Tennis.getInstance();
				Sport calcetto = Calcetto.getInstance();
				Impianto impianto = Impianto1.getInstance();
				Istruttore istruttore = new Istruttore();
				Istruttore istruttore2 = new Istruttore();

<<<<<<< HEAD
				this.registroPrenotazioni = RegistroPrenotazioni.getInstance();
				
				this.registroSport = RegistroSport.getInstance();
				
			    this.registroSportivi = RegistroSportivi.getInstance();
				
				SimpleFactory simpleFactory = SimpleFactory.getInstance();
				
				//settaggio degli attributi
				sportivo.setNome("gino");
				istruttore.setNome("pippo");
				istruttore2.setNome("giovanni");
				//impianto.addSport(tennis);//crea grandi problemi sempre
				tennis.addImpianto(impianto); //crea grandi problemi se usato insieme  a impianto.addSport
				tennis.addIstruttore(istruttore);
				tennis.setDescription("tennis");
				calcetto.addIstruttore(istruttore2);
				calcetto.setDescription("calcetto");
				this.registroSport.addSport(tennis);
				this.registroSport.addSport(calcetto);
				sportivo.setIDSportivo(1);
				this.registroSportivi.addSportivo(sportivo);
				
				//creazione e settaggio della lezione
				Lezione lezione = new Lezione();
				lezione.setImpianto(impianto);
				//lezione.setIstruttore(istruttore); //crea grandi problemi...sembra che l'istruttore all'interno di lezione e delle lezioni all'interno dell'istruttore crei conflitti				
				istruttore.addLezione(lezione);
		
		
		//int LastIDPrenotazione = this.registroPrenotazioni.getLastIDPrenotazione();
		Sportivo sportivoPrenotante = this.registroSportivi.getSportivo(IDSportivo);
		nuovaPrenotazione = sportivoPrenotante.creaNuovaPrenotazione(1);
=======
	@GetMapping("/prenotazione/")
	public HashMap<String, Object> avviaNuovaPrenotazione(@RequestParam("IDSportivo") int IDSportivo) {
		
		int LastIDPrenotazione = this.registroPrenotazioni.getLastIDPrenotazione();
		sportivoPrenotante = this.registroSportivi.getSportivo(IDSportivo);
		nuovaPrenotazione = sportivoPrenotante.creaNuovaPrenotazione(LastIDPrenotazione);
>>>>>>> d521e5a751ea00a76bac002838e1782fa16f193c
		
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
	@GetMapping("/lezione")
	public Sportivo getLezione() {
		//creazione degli ogetti
		Sportivo sportivo = new Sportivo();
		Sport tennis = Tennis.getInstance();
		Sport calcetto = Calcetto.getInstance();
		Impianto impianto = Impianto1.getInstance();
		Istruttore istruttore = new Istruttore();
		RegistroPrenotazioni registroPrenotazioni = RegistroPrenotazioni.getInstance();
		RegistroSport registroSport = RegistroSport.getInstance();
		RegistroSportivi registroSportivi = RegistroSportivi.getInstance();
		SimpleFactory simpleFactory = SimpleFactory.getInstance();
		
		//settaggio degli attributi
		sportivo.setNome("gino");
		sportivo.setIDSportivo(1);
		istruttore.setNome("pippo");
		impianto.addSport(tennis);
		//tennis.addImpianto(impianto); crea grandi problemi
		tennis.addIstruttore(istruttore);
		tennis.setDescription("tennis");
		calcetto.addIstruttore(istruttore);
		calcetto.setDescription("calcetto");
		registroSport.addSport(tennis);
		registroSport.addSport(calcetto);
		registroSportivi.addSportivo(sportivo);
		
		
		
		
		Lezione lezione = new Lezione();
		lezione.setImpianto(impianto);
		lezione.setIstruttore(istruttore);
		return registroSportivi.getSportivo(1);
	}
	
}
