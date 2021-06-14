package it.univaq.esc.factory;

import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneState;
import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.NotificaState;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;

public abstract class ElementiPrenotazioneFactory {

	public abstract EffettuaPrenotazioneState getStatoEffettuaPrenotazioneHandler(String tipoPrenotazione);
		
	public abstract NotificaService getNotifica();
	
	public abstract NotificaState getStatoNotifica(String tipoPrenotazione);
	
	public abstract PrenotazioneSpecs getPrenotazioneSpecs(String tipoPrenotazione);
	
	public abstract Appuntamento getAppuntamento();
	
	
}

