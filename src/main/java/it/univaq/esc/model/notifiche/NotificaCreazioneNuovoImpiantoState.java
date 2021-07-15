package it.univaq.esc.model.notifiche;

import java.util.List;
import org.springframework.stereotype.Component;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class NotificaCreazioneNuovoImpiantoState extends NotificaState {

	@Override
	public String getMessaggioNotifica(NotificaService notificaDiCuiCostruireIlMessaggio) {
		String messaggio = "";
		List<String> nomiSport = (List<String>) notificaDiCuiCostruireIlMessaggio.getEvento().getInfo()
				.get("sportPraticabili");
		String pavimentazione = (String) notificaDiCuiCostruireIlMessaggio.getEvento().getInfo().get("pavimentazione");

		String sports = "";
		for (String sport : nomiSport) {
			sports = sports + ", " + sport;
		}

		messaggio = "È stata allestita una nuova struttura in cui è possibile praticare " + sports + " su "
				+ pavimentazione + ". Clicca e scopri tutte le sue caratteristiche!";

		return messaggio;
	}
}
