package it.univaq.esc.services;

import it.univaq.esc.dto.IOpzioniPrenotazioneDTO;

public interface IOpzioniPrenotazione {
	
	IOpzioniPrenotazione getOpzioni(ImpiantoService impiantoService, SportivoService sportivoService, SportService sportService, IstruttoreService istruttoreService, PrenotazioneService prenotazioneService);

	IOpzioniPrenotazioneDTO toDTO();
}
