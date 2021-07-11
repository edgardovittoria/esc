package it.univaq.esc.model.notifiche;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class NotificaImpiantoSquadraState extends NotificaState{

	@Override
	public String getMessaggioNotifica(NotificaService notificaDiCuiCostruireIlMessaggio) {
		NotificaSquadraService notificaSquadraService = (NotificaSquadraService) notificaDiCuiCostruireIlMessaggio;
		String messaggio = "";
		Integer numeroIncontri = notificaDiCuiCostruireIlMessaggio.getNumeroIncontriEvento();
		String sport = notificaDiCuiCostruireIlMessaggio.getNomeSportEvento();
		String nomeSquadraDestinatario = notificaSquadraService.getSquadraDelDestinatario().getNome();
		
		if(numeroIncontri > 1) {
			messaggio = "La tua squadra "+ nomeSquadraDestinatario + " è stata invitata a partecipare a una serie di " + numeroIncontri + " incontri di " + sport + ".";
		}
		else {
			messaggio = "La tua squadra "+ nomeSquadraDestinatario + " è stata invitata a partecipare a un incontro di " + sport + ".";
		}
		 
		return messaggio;
	
	}

}
