package it.univaq.esc.controller.effettuaPrenotazione;

import it.univaq.esc.utility.BeanUtil;

public class StatoEffettuaPrenotazioneSquadraFactory extends StatoEffettuaPrenotazioneFactory {

	@Override
	public EffettuaPrenotazioneState getStato(String tipoPrenotazione) {
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

}
