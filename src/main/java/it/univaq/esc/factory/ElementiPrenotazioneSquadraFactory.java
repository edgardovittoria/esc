package it.univaq.esc.factory;


import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import groovy.lang.Singleton;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneImpiantoSquadraState;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneState;
import it.univaq.esc.dtoObjects.PrenotazioneImpiantoSquadraSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;
import it.univaq.esc.model.notifiche.NotificaImpiantoSquadraState;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.NotificaSquadraService;
import it.univaq.esc.model.notifiche.NotificaState;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoSquadra;
import it.univaq.esc.model.prenotazioni.PrenotazioneImpiantoSquadraSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.utility.BeanUtil;

@Component(value = "ELEMENTI_PRENOTAZIONE_SQUADRA")
@Singleton
@DependsOn("beanUtil")
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

	

	@Override
	public PrenotazioneSpecs getPrenotazioneSpecs(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		default:
			return null;

		case "IMPIANTO":
			return new PrenotazioneImpiantoSquadraSpecs();

		case "LEZIONE":
			return null;
		case "CORSO":
			return null;

		}
	}

	@Override
	public Appuntamento getAppuntamento() {
		return new AppuntamentoSquadra();
	}

}
