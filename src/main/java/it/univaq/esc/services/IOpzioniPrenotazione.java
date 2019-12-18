package it.univaq.esc.services;


import org.springframework.ui.Model;

public interface IOpzioniPrenotazione {
	
	IOpzioniPrenotazione getOpzioni(ImpiantoService impiantoService, SportivoService sportivoService, SportService sportService, IstruttoreService istruttoreService, PrenotazioneService prenotazioneService);
}
