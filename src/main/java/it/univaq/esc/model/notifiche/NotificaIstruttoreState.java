package it.univaq.esc.model.notifiche;

import groovy.lang.Singleton;
import org.springframework.stereotype.Component;

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
