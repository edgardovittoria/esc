package it.univaq.esc.model.notifiche;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public abstract class NotificaState {


	public abstract String getMessaggioNotifica(NotificaService notificaDiCuiCostruireIlMessaggio);
}
