package it.univaq.esc.factory;

import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneState;
import it.univaq.esc.model.notifiche.Notifica;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.NotificaState;

public abstract class ElementiPrenotazioneFactory {

	public abstract EffettuaPrenotazioneState getStatoEffettuaPrenotazioneHandler(String tipoPrenotazione);
		
	public abstract NotificaService getNotifica();
	
	public abstract NotificaState getStatoNotifica(String tipoPrenotazione);
	
	
}

