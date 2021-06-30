package it.univaq.esc.model.notifiche;


import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class NotificaCorsoState extends NotificaState{

	
	@Override
	public String getMessaggioNotifica(NotificaService notificaDiCuiCostruireIlMessaggio) {
		String sport = (String)notificaDiCuiCostruireIlMessaggio.getEvento().getInfo().get("sportNome");
		Integer numeroIncontri = (Integer)notificaDiCuiCostruireIlMessaggio.getEvento().getInfo().get("numeroIncontri");
		String messaggio = "Partecipa al nuovo corso di " + sport + "organizzato da ESC. "
				+ "Comprende una serie di " + numeroIncontri + " lezioni.";
		
		return messaggio;
	}

}
