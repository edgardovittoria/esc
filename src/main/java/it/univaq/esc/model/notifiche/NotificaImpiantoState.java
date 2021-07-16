package it.univaq.esc.model.notifiche;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor
public class NotificaImpiantoState extends NotificaState{
	
	
	

	@Override
	public String getMessaggioNotifica(NotificaService notificaDiCuiCostruireIlMessaggio) {
		String messaggio = "";
		Integer numeroIncontri = notificaDiCuiCostruireIlMessaggio.getNumeroIncontriEvento();
		String sport = notificaDiCuiCostruireIlMessaggio.getNomeSportEvento();
		if(numeroIncontri > 1) {
			messaggio = "Sei stato invitato a partecipare a una serie di " + numeroIncontri + " incontri di " + sport + ".";
		}
		else {
			messaggio = "Sei stato invitato a partecipare a un incontro di " + sport + ".";
		}
		 
		return messaggio;
	}

	

}
