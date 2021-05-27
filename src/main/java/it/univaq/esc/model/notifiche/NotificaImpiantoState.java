package it.univaq.esc.model.notifiche;



import org.springframework.stereotype.Component;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;

import lombok.NoArgsConstructor;


@Component
@NoArgsConstructor
public class NotificaImpiantoState extends NotificaState{
	
	static {
		FactoryStatiNotifiche.registra(TipiPrenotazione.IMPIANTO.toString(),
				NotificaImpiantoState.class);
	}

	
	

	@Override
	public String getMessaggioNotifica(Notifica notificaDiCuiCostruireIlMessaggio) {
		String messaggio = "";
		Integer numeroIncontri = (Integer)notificaDiCuiCostruireIlMessaggio.getEvento().getInfo().get("numeroIncontri");
		String sport = (String)notificaDiCuiCostruireIlMessaggio.getEvento().getInfo().get("sport");
		if(numeroIncontri > 1) {
			messaggio = "Sei stato invitato a partecipare a una serie di " + numeroIncontri + " incontri di " + sport + ".";
		}
		else {
			messaggio = "Sei stato invitato a partecipare a un incontro di " + sport + ".";
		}
		 
		return messaggio;
	}

	

}
