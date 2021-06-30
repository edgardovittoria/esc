package it.univaq.esc.model.notifiche;



import org.springframework.stereotype.Component;
import lombok.NoArgsConstructor;


@Component
@NoArgsConstructor
public class NotificaImpiantoState extends NotificaState{
	
	
	

	@Override
	public String getMessaggioNotifica(NotificaService notificaDiCuiCostruireIlMessaggio) {
		String messaggio = "";
		Integer numeroIncontri = (Integer)notificaDiCuiCostruireIlMessaggio.getEvento().getInfo().get("numeroIncontri");
		String sport = (String)notificaDiCuiCostruireIlMessaggio.getEvento().getInfo().get("sportNome");
		if(numeroIncontri > 1) {
			messaggio = "Sei stato invitato a partecipare a una serie di " + numeroIncontri + " incontri di " + sport + ".";
		}
		else {
			messaggio = "Sei stato invitato a partecipare a un incontro di " + sport + ".";
		}
		 
		return messaggio;
	}

	

}
