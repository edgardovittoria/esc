package it.univaq.esc.controller.effettuaPrenotazione;

import it.univaq.esc.utility.BeanUtil;

public class StatoEffettuaPrenotazioneSquadraFactory extends StatoEffettuaPrenotazioneFactory {

	@Override
	public EffettuaPrenotazioneState getStato(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		case "IMPIANTO":
			return BeanUtil.getBean(EffettuaPrenotazioneImpiantoState.class);
		case "LEZIONE":
			return BeanUtil.getBean(EffettuaPrenotazioneLezioneState.class);
		case "CORSO":
			return BeanUtil.getBean(EffettuaPrenotazioneCorsoState.class);
		default:
			return null;
		}
	}

}
