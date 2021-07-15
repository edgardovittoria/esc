package it.univaq.esc.controller.promuoviPolisportiva;

import java.util.Map;

import org.springframework.context.annotation.DependsOn;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.StrutturaPolisportiva;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.notifiche.Notifica;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.notifiche.TipoNotifica;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PROTECTED) @Setter(value = AccessLevel.PROTECTED)
@DependsOn("beanUtil")
public abstract class CreazioneNuovaStrutturaState {

	private MapperFactory mapperFactory;
	private RegistroSport registroSport;
	private RegistroImpianti registroImpianti;
	private RegistroNotifiche registroNotifiche;
	private RegistroUtentiPolisportiva registroUtentiPolisportiva;
	private ElementiPrenotazioneFactory elementiPrenotazioneFactory;
	
	public CreazioneNuovaStrutturaState(RegistroSport registroSport, RegistroImpianti registroImpianti, RegistroNotifiche registroNotifiche, RegistroUtentiPolisportiva registroUtentiPolisportiva) {
		impostaAttributiControllerDipendentiDa(ModalitaPrenotazione.SINGOLO_UTENTE.toString());
		setRegistroSport(registroSport);
		setRegistroImpianti(registroImpianti);
		setRegistroNotifiche(registroNotifiche);
		setRegistroUtentiPolisportiva(registroUtentiPolisportiva);
	}
	
	
	public abstract Map<String, Object> getDatiPerCreazioneNuovaStruttura();
	public abstract void creaNuovaStrutturaConfermata(StrutturaPolisportiva struttraDaInserireNellaPolisportiva);
	
	private void impostaAttributiControllerDipendentiDa(String modalitaPrenotazione) {
		setElementiPrenotazioneFactory(this.chiedeAlContainerLaFactoryStatiRelativaAlla(modalitaPrenotazione));
		setMapperFactory(chiedeAlContainerLaMapperFactoryiRelativaAlla(modalitaPrenotazione));
	}

	private ElementiPrenotazioneFactory chiedeAlContainerLaFactoryStatiRelativaAlla(String modalitaPrenotazione) {
		return BeanUtil.getBean("ELEMENTI_PRENOTAZIONE_" + modalitaPrenotazione, ElementiPrenotazioneFactory.class);
	}

	private MapperFactory chiedeAlContainerLaMapperFactoryiRelativaAlla(String modalitaPrenotazione) {
		return BeanUtil.getBean("MAPPER_" + modalitaPrenotazione, MapperFactory.class);
	}
	
	public abstract void creaNotificaCreazioneNuovaStrutturaConMessaggioPerSingoloUtente(Map<String, String> mappaEmailDirettoreEMessaggioNotifica, UtentePolisportiva utenteDestinatario, StrutturaPolisportiva nuovaStruttura);
}

