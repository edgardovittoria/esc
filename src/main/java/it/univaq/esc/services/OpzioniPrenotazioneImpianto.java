package it.univaq.esc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import it.univaq.esc.dto.SportDTO;
import it.univaq.esc.dto.SportivoDTO;

public class OpzioniPrenotazioneImpianto implements IOpzioniPrenotazione{

	@Autowired
	private List<SportDTO> sport; 
	@Autowired
	private List<SportivoDTO> sportivi;

	@Override
	public IOpzioniPrenotazione getOpzioni(ImpiantoService impiantoService, SportivoService sportivoService, SportService sportService,
			IstruttoreService istruttoreService, PrenotazioneService prenotazioneService) {
		sport = sportService.toDTO(sportService.getAllSport());
		sportivi = sportivoService.toDTO(sportivoService.getSportivi());
		return this;
	}
	
}
