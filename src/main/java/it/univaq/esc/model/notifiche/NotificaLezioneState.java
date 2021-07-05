package it.univaq.esc.model.notifiche;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class NotificaLezioneState extends NotificaState{

	@Override
	public String getMessaggioNotifica(NotificaService notificaDiCuiCostruireIlMessaggio) {
		String messaggio = "";
		
		return messaggio;
	}
}
