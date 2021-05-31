package it.univaq.esc.controller.notifiche;

import it.univaq.esc.dtoObjects.NotificabileDTO;


public abstract class DettagliNotificaStrategy {

	public abstract NotificabileDTO getDettagliNotifica(Integer idEvento);
}
