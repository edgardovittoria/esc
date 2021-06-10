package it.univaq.esc.factory;

import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneCorsoState;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneImpiantoSquadraState;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneImpiantoState;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneLezioneState;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneState;
import it.univaq.esc.model.notifiche.NotificaImpiantoSquadraState;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.NotificaSquadraService;
import it.univaq.esc.model.notifiche.NotificaState;
import it.univaq.esc.utility.BeanUtil;

public class ElementiPrenotazioneSquadraFactory extends ElementiPrenotazioneFactory{

	@Override
	public EffettuaPrenotazioneState getStatoEffettuaPrenotazioneHandler(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		case "IMPIANTO":
			return BeanUtil.getBean(EffettuaPrenotazioneImpiantoSquadraState.class);
		case "LEZIONE":
			return null;
		case "CORSO":
			return null;
		default:
			return null;
		}
	}

	@Override
	public NotificaService getNotifica() {
		return new NotificaSquadraService(this);
	}

	@Override
	public NotificaState getStatoNotifica(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		case "IMPIANTO":
			return BeanUtil.getBean(NotificaImpiantoSquadraState.class);
		case "LEZIONE":
			return null;
		case "CORSO":
			return null;
		default:
			return null;
		}
	}

}
