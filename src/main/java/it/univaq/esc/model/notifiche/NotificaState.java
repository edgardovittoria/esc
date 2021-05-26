package it.univaq.esc.model.notifiche;

import lombok.AllArgsConstructor;

public abstract class NotificaState {


	public abstract String getMessaggioNotifica(Notifica notificaDiCuiCostruireIlMessaggio);
}
