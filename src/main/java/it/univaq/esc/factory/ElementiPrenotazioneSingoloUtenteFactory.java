package it.univaq.esc.factory;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneCorsoState;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneImpiantoState;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneLezioneState;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneState;
import it.univaq.esc.dtoObjects.PrenotazioneCorsoSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneImpiantoSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneLezioneSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;
import it.univaq.esc.model.notifiche.NotificaCorsoState;
import it.univaq.esc.model.notifiche.NotificaImpiantoState;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.NotificaState;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoSingoliPartecipanti;
import it.univaq.esc.model.prenotazioni.PrenotazioneCorsoSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneImpiantoSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneLezioneSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.utility.BeanUtil;

@Component(value = "ELEMENTI_PRENOTAZIONE_SINGOLO_UTENTE")
@Singleton
@DependsOn("beanUtil")
public class ElementiPrenotazioneSingoloUtenteFactory extends ElementiPrenotazioneFactory{
	

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
	public NotificaService getNotifica() {
		return new NotificaService(this);
	}

	@Override
	public NotificaState getStatoNotifica(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		case "IMPIANTO":
			return BeanUtil.getBean(NotificaImpiantoState.class);
		case "LEZIONE":
			return null;
		case "CORSO":
			return BeanUtil.getBean(NotificaCorsoState.class);
		default:
			return null;
		}

		
	}

	

	@Override
	public PrenotazioneSpecs getPrenotazioneSpecs(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		default:
			return null;

		case "IMPIANTO":
			return new PrenotazioneImpiantoSpecs();

		case "LEZIONE":
			return new PrenotazioneLezioneSpecs();
		case "CORSO":
			return new PrenotazioneCorsoSpecs();

		}
	}

	@Override
	public Appuntamento getAppuntamento() {
		return new AppuntamentoSingoliPartecipanti();
	}

}
