package it.univaq.esc.model.notifiche;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.Sport;

@Component
@Singleton
public class NotificaIstruttoreState extends NotificaState{

	@Override
	public String getMessaggioNotifica(NotificaService notificaDiCuiCostruireIlMessaggio) {
		String messaggio = "";
		String sport = notificaDiCuiCostruireIlMessaggio.getNomeSportEvento();
		String tipoPrenotazione = notificaDiCuiCostruireIlMessaggio.getTipoPrenotazioneEvento();
		
		messaggio = "Sei stato assegnato come istruttore ad un appuntamento di tipo " + tipoPrenotazione + " di " + sport;
		return messaggio;
	}

}
