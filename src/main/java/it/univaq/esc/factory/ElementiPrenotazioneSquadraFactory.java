package it.univaq.esc.factory;


import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import groovy.lang.Singleton;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneImpiantoSquadraState;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneState;
import it.univaq.esc.model.notifiche.Notifica;
import it.univaq.esc.model.notifiche.NotificaImpiantoSquadraState;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.NotificaSquadraService;
import it.univaq.esc.model.notifiche.NotificaState;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpiantoSquadra;
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
	public NotificaState getStatoNotifica(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		case "INVITO_IMPIANTO":
			return BeanUtil.getBean(NotificaImpiantoSquadraState.class);
		case "INVITO_CORSO":
			return null;
		case "ISTRUTTORE_LEZIONE":
			return null;
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
			return new AppuntamentoImpiantoSquadra();

		case "LEZIONE":
			return null;
		case "CORSO":
			return null;

		}
	}



	@Override
	public NotificaService getNotifica(Notifica notifica) {
		return new NotificaSquadraService(notifica, this);
	}

}
