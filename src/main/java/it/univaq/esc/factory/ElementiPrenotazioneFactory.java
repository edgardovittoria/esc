package it.univaq.esc.factory;

import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneState;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.notifiche.Notifica;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.NotificaState;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;

public abstract class ElementiPrenotazioneFactory {

	public abstract EffettuaPrenotazioneState getStatoEffettuaPrenotazioneHandler(String tipoPrenotazione);
		
	public abstract NotificaService getNotifica(Notifica notifica);
	
	public abstract NotificaState getStatoNotifica(String tipoPrenotazione);
	
	public abstract Appuntamento getAppuntamento(String tipoPrenotazione);
	
	public abstract Prenotazione getPrenotazione();
	
	public CalcolatoreCosto getCalcolatoreCosto() {
		CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
		calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());
		
		return calcolatoreCosto;
	}
	
	
}

