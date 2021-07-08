package it.univaq.esc.model.notifiche;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Singleton
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
public class RegistroNotificabili {
	
	private List<Notificabile> listaNotificabili = new ArrayList<Notificabile>();
	
	public RegistroNotificabili(RegistroPrenotazioni registroPrenotazioni, RegistroAppuntamenti registroAppuntamenti) {
		getListaNotificabili().addAll(registroPrenotazioni.getPrenotazioniRegistrate());
		getListaNotificabili().addAll(registroAppuntamenti.getListaAppuntamenti());
	}
	
	
	public Notificabile trovaNotificabileDaId(Integer idNotificabile) {
		for(Notificabile notificabile : getListaNotificabili()) {
			if(notificabile.getIdNotificabile() == idNotificabile) {
				return notificabile;
			}
		}
		return null;
	}

}
