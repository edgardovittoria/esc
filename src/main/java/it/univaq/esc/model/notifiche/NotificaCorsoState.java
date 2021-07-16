package it.univaq.esc.model.notifiche;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class NotificaCorsoState extends NotificaState{

	
	@Override
	public String getMessaggioNotifica(NotificaService notificaDiCuiCostruireIlMessaggio) {
		String sport = notificaDiCuiCostruireIlMessaggio.getNomeSportEvento();
		Integer numeroIncontri = notificaDiCuiCostruireIlMessaggio.getNumeroIncontriEvento();
		String messaggio = "Partecipa al nuovo corso di " + sport + "organizzato da ESC. "
				+ "Comprende una serie di " + numeroIncontri + " lezioni.";
		
		return messaggio;
	}

}