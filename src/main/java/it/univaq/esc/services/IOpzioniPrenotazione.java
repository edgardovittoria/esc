package it.univaq.esc.services;

import org.springframework.stereotype.Service;

import it.univaq.esc.dto.IOpzioniPrenotazioneDTO;

@Service
public interface IOpzioniPrenotazione {
	
	IOpzioniPrenotazione getOpzioni(ImpiantoService impiantoService, SportivoService sportivoService, SportService sportService, IstruttoreService istruttoreService, PrenotazioneService prenotazioneService);

	IOpzioniPrenotazioneDTO toDTO();
}
