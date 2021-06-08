package it.univaq.esc.controller.effettuaPrenotazione;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import groovy.lang.Singleton;
import it.univaq.esc.utility.BeanUtil;
import lombok.NoArgsConstructor;

/**
 * Factory per la creazione degli Stati del controller
 * EffettuaPrenotazioneHandlerRest. In base al tipo di prenotazione passato come
 * parametro.
 * 
 * @author esc
 *
 */
@Component
@Singleton
@NoArgsConstructor
public class StatoEffettuaPrenotazioneSingoloUtenteFactory extends StatoEffettuaPrenotazioneFactory {
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
