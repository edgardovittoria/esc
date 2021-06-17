package it.univaq.esc.model.notifiche;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
public interface NotificaState {


	public abstract String getMessaggioNotifica(NotificaService notificaDiCuiCostruireIlMessaggio);
}
