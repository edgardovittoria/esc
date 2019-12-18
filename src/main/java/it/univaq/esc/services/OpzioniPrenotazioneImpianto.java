package it.univaq.esc.services;

import java.util.List;

import org.springframework.ui.Model;

import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Sportivo;

public class OpzioniPrenotazioneImpianto implements IOpzioniPrenotazione{

	@Override
	public Model getOpzioni(ImpiantoService impiantoService, SportivoService sportivoService, SportService sportService,
			IstruttoreService istruttoreService, PrenotazioneService prenotazioneService, Model opzioni) {
		List<Sport> availableSports = sportService.getAllSport();
		List<Sportivo> availableSportivi = sportivoService.getSportivi();
		opzioni.addAttribute("Sport", availableSports);
		opzioni.addAttribute("Sportivi", availableSportivi);
		return opzioni;
	}
	
}
