package it.univaq.esc.model.notifiche;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class NotificaImpiantoSquadraState extends NotificaState{

	@Override
	public String getMessaggioNotifica(NotificaService notificaDiCuiCostruireIlMessaggio) {
		String messaggio = "";
		Integer numeroIncontri = (Integer)notificaDiCuiCostruireIlMessaggio.getEvento().getInfo().get("numeroIncontri");
		String sport = (String)notificaDiCuiCostruireIlMessaggio.getEvento().getInfo().get("sportNome");
		String nomeSquadraDestinatario = ((NotificaSquadraService)notificaDiCuiCostruireIlMessaggio).getSquadraDelDestinatario().getNome();
		if(numeroIncontri > 1) {
			messaggio = "La tua squadra "+ nomeSquadraDestinatario + " è stata invitata a partecipare a una serie di " + numeroIncontri + " incontri di " + sport + ".";
		}
		else {
			messaggio = "La tua squadra "+ nomeSquadraDestinatario + " è stata invitata a partecipare a un incontro di " + sport + ".";
		}
		 
		return messaggio;
	
	}

}
