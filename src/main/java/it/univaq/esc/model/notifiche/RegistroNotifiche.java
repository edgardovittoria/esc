package it.univaq.esc.model.notifiche;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.catalina.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.repository.NotificaRepository;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Singleton
@Getter
@Setter(value = AccessLevel.PRIVATE)
@DependsOn("beanUtil")
public class RegistroNotifiche {
	

	@Getter(value = AccessLevel.PRIVATE)
	private RegistroPrenotazioni registroPrenotazioni;

	@Getter(value = AccessLevel.PRIVATE)
	private NotificaRepository notificaRepository;

	private List<NotificaService> listaNotifiche = new ArrayList<NotificaService>();

	public RegistroNotifiche(RegistroPrenotazioni registroPrenotazioni, NotificaRepository notificaRepository) {
		setRegistroPrenotazioni(registroPrenotazioni);
		setNotificaRepository(notificaRepository);
	}

	@PostConstruct
	public void popola() {
		List<NotificaService> listaNotifiche = new ArrayList<NotificaService>();
		for (Notifica notifica : getNotificaRepository().findAll()) {
			notifica.setEvento((Notificabile) getRegistroPrenotazioni().getPrenotazioneById(notifica.getIdEvento()));
			NotificaService notificaService = BeanUtil.getBean(NotificaService.class);
			notificaService.setDestinatario(notifica.getDestinatario());
			notificaService.setMittente(notifica.getMittente());
			notificaService.setEvento(notifica.getEvento());
			notificaService.setLetta(notifica.isLetta());
			listaNotifiche.add(notificaService);
		}
		
		setListaNotifiche(listaNotifiche);
	}

	public List<NotificaService> getNotifichePerDestinatario(UtentePolisportivaAbstract destinatario) {
		List<NotificaService> notificheDestinatario = new ArrayList<NotificaService>();
		for (NotificaService notifica : getListaNotifiche()) {
			if (notifica.getDestinatario().isEqual(destinatario)) {
				notificheDestinatario.add(notifica);
			}
		}
		return notificheDestinatario;
	}

	public void salvaNotifica(NotificaService notificaDaSalvare) {
		Notifica notifica = notificaDaSalvare.getNotifica();
		getListaNotifiche().add(notificaDaSalvare);
		getNotificaRepository().save(notifica);
	}

	public void aggiornaNotificaSuDatabase(NotificaService notificaDaAggiornare) {
		Notifica notifica = notificaDaAggiornare.getNotifica();
		getNotificaRepository().save(notifica);
	}
}
