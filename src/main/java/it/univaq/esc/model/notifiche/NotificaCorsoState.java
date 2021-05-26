package it.univaq.esc.model.notifiche;


import org.springframework.stereotype.Component;

import it.univaq.esc.model.prenotazioni.TipiPrenotazione;

@Component
public class NotificaCorsoState extends NotificaState{

	static {
		FactoryStatiNotifiche.registra(TipiPrenotazione.CORSO.toString(),
				NotificaCorsoState.class);
	}
	
	@Override
	public String getMessaggioNotifica(Notifica notificaDiCuiCostruireIlMessaggio) {
		String sport = (String)notificaDiCuiCostruireIlMessaggio.getEvento().getInfo().get("sport");
		Integer numeroIncontri = (Integer)notificaDiCuiCostruireIlMessaggio.getEvento().getInfo().get("numeroIncontri");
		String messaggio = "Partecipa al nuovo corso di " + sport + "organizzato da ESC. "
				+ "Comprende una serie di " + numeroIncontri + " lezioni.";
		
		return messaggio;
	}

}
