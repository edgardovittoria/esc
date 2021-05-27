package it.univaq.esc.model.notifiche;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class NotificaState {


	public abstract String getMessaggioNotifica(Notifica notificaDiCuiCostruireIlMessaggio);
}
