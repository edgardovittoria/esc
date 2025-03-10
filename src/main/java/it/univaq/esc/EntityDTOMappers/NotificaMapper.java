package it.univaq.esc.EntityDTOMappers;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.NotificaDTO;
import it.univaq.esc.model.notifiche.NotificaService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component(value = "MAPPER_NOTIFICA_SINGOLO_UTENTE")
@Singleton
@NoArgsConstructor
public class NotificaMapper extends EntityDTOMapper{

	
	
	public NotificaDTO convertiInNotificaDTO(NotificaService notificaDaConvertire) {
		NotificaDTO notificaDTO = new NotificaDTO();
		
		notificaDTO.setIdNotifica(notificaDaConvertire.getIdNotifica());
		notificaDTO.setMessaggio(notificaDaConvertire.getNotifica().getMessaggio());
		notificaDTO.setMittente(notificaDaConvertire.getNominativoCompletoMittente());
		notificaDTO.setIdEvento(notificaDaConvertire.getIdEvento());
		notificaDTO.setTipoEventoNotificabile(notificaDaConvertire.getTipoEventoNotificabile());
		notificaDTO.setLetta(notificaDaConvertire.isLetta());
		notificaDTO.setTipoNotifica(notificaDaConvertire.getTipoNotifica());
		
		return notificaDTO;
		
	}

}
