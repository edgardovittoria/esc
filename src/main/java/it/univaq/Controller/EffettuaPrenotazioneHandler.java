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

@RestController
@RequestMapping("/esc")
public class EffettuaPrenotazioneHandler {

	private RegistroPrenotazioni registroPrenotazioni;
	private RegistroSportivi registroSportivi;
	private Sportivo sportivoPrenotante;
	private Prenotazione nuovaPrenotazione;
	
	
	//costruttore
	public EffettuaPrenotazioneHandler(RegistroPrenotazioni registroPrenotazioni, RegistroSportivi registroSportivi) {
		super();
		this.registroPrenotazioni = registroPrenotazioni;
		this.registroSportivi = registroSportivi;
	}



	@GetMapping("/prenotazione/")
	public View avviaNuovaPrenotazione(@RequestParam("IDSportivo") int IDSportivo) {
		
		int LastIDPrenotazione = this.registroPrenotazioni.getLastIDPrenotazione();
		Sportivo sportivoPrenotante = this.registroSportivi.getSportivo(IDSportivo);
		nuovaPrenotazione = sportivoPrenotante.creaNuovaPrenotazione(LastIDPrenotazione);
		
		return null;
	}
}
