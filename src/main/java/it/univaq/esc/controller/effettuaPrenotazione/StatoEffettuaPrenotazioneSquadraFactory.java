package it.univaq.esc.controller.effettuaPrenotazione;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.utility.BeanUtil;

@Component
@Singleton
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
