package it.univaq.esc.EntityDTOMappers;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.NotificaDTO;
import it.univaq.esc.model.notifiche.NotificaService;
import lombok.NoArgsConstructor;

@Component(value = "MAPPER_NOTIFICA_SINGOLO_UTENTE")
@Singleton
@NoArgsConstructor
public class NotificaMapper extends EntityDTOMapper{

	
	
	public NotificaDTO convertiInNotificaDTO(NotificaService notificaDaConvertire) {
		NotificaDTO notificaDTO = new NotificaDTO();
		
		notificaDTO.setIdNotifica(notificaDaConvertire.getIdNotifica());
		notificaDTO.setMessaggio(notificaDaConvertire.getMessaggio());
		notificaDTO.setMittente(notificaDaConvertire.getMittente().getProprieta().get("nome") + " " + notificaDaConvertire.getMittente().getProprieta().get("cognome"));
		notificaDTO.setIdEvento((Integer)notificaDaConvertire.getEvento().getInfo().get("identificativo"));
		notificaDTO.setTipoEventoNotificabile(notificaDaConvertire.getEvento().getTipoEventoNotificabile());
		notificaDTO.setLetta(notificaDaConvertire.isLetta());
		
		return notificaDTO;
		
	}

}
