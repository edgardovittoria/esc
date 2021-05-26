package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.repository.NotificaRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
@Component
@Singleton
@Getter @Setter(value = AccessLevel.PRIVATE)
public class RegistroNotifiche {

	@Getter(value = AccessLevel.PRIVATE)
	private RegistroPrenotazioni registroPrenotazioni;
	
	@Getter(value = AccessLevel.PRIVATE)
	private NotificaRepository notificaRepository;
	
	private List<Notifica> listaNotifiche = new ArrayList<Notifica>();
	
	
	public RegistroNotifiche(RegistroPrenotazioni registroPrenotazioni, NotificaRepository notificaRepository) {
		setRegistroPrenotazioni(registroPrenotazioni);
		setNotificaRepository(notificaRepository);
	}
	
	@PostConstruct
	public void popola() {
		setListaNotifiche(getNotificaRepository().findAll());
		for(Notifica notifica : getListaNotifiche()) {
			notifica.setEvento((Notificabile)getRegistroPrenotazioni().getPrenotazioneById(notifica.getIdEvento()));
		}
	}
	
	public List<Notifica> getNotifichePerDestinatario(UtentePolisportivaAbstract destinatario){
		List<Notifica> notificheDestinatario = new ArrayList<Notifica>();
		for(Notifica notifica : getListaNotifiche()) {
			for(UtentePolisportivaAbstract utente : notifica.getDestinatari()) {
				if(utente.isEqual(destinatario)) {
					notificheDestinatario.add(notifica);
				}
			}
		}
		return notificheDestinatario;
	}
}
