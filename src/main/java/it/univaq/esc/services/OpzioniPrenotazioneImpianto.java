package it.univaq.esc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import it.univaq.esc.dto.SportDTO;
import it.univaq.esc.dto.SportivoDTO;

public class OpzioniPrenotazioneImpianto implements IOpzioniPrenotazione{

	@Autowired
	private List<SportDTO> Sport; 
	@Autowired
	private List<SportivoDTO> Sportivi;

	@Override
	public IOpzioniPrenotazione getOpzioni(ImpiantoService impiantoService, SportivoService sportivoService, SportService sportService,
			IstruttoreService istruttoreService, PrenotazioneService prenotazioneService) {
		Sport = sportService.toDTO(sportService.getAllSport());
		Sportivi = sportivoService.toDTO(sportivoService.getSportivi());
		return this;
	}
	
}
