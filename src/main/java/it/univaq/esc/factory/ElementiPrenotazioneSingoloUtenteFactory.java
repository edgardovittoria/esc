package it.univaq.esc.factory;

import groovy.lang.Singleton;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneCorsoState;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneImpiantoState;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneLezioneState;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneState;
import it.univaq.esc.model.notifiche.*;
import it.univaq.esc.model.prenotazioni.*;
import it.univaq.esc.utility.BeanUtil;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component(value = "ELEMENTI_PRENOTAZIONE_SINGOLO_UTENTE")
@Singleton
@DependsOn("beanUtil")
public class ElementiPrenotazioneSingoloUtenteFactory extends ElementiPrenotazioneFactory {

	@Override
	public EffettuaPrenotazioneState getStatoEffettuaPrenotazioneHandler(String tipoPrenotazione) {
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

	@Override
	public NotificaService getNotifica(Notifica notifica) {
		return new NotificaService(notifica, this);
	}

	@Override
	public NotificaState getStatoNotifica(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		case "INVITO_IMPIANTO":
			return BeanUtil.getBean(NotificaImpiantoState.class);
		case "INVITO_CORSO":
			return BeanUtil.getBean(NotificaCorsoState.class);
		case "ISTRUTTORE_LEZIONE":
			return BeanUtil.getBean(NotificaIstruttoreState.class);
			case "CREAZIONE_IMPIANTO":
			return BeanUtil.getBean(NotificaCreazioneNuovoImpiantoState.class);
		default:
			return null;
		}

	}

	@Override
	public Appuntamento getAppuntamento(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		default:
			return null;

		case "IMPIANTO":
			return new AppuntamentoImpianto();

		case "LEZIONE":
			return new AppuntamentoLezione();
		case "CORSO":
			return new AppuntamentoCorso();

		}
	}

	@Override
	public Prenotazione getPrenotazione() {
		return new Prenotazione();
	}

}
