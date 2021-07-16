package it.univaq.esc.EntityDTOMappers;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.NotificaDTO;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.NotificaSquadraService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component(value = "MAPPER_NOTIFICA_SQUADRA")
@Singleton
@NoArgsConstructor
public class NotificaSquadraMapper extends NotificaMapper{

	
	@Override
		public NotificaDTO convertiInNotificaDTO(NotificaService notificaDaConvertire) {
			
			NotificaDTO notificaDTO = super.convertiInNotificaDTO(notificaDaConvertire);
			NotificaSquadraService notificaSquadraService = (NotificaSquadraService) notificaDaConvertire;
			
			notificaDTO.setSquadraDelDestinatario(getMapperFactory().getSquadraMapper().convertiInSquadraDTO(notificaSquadraService.getSquadraDelDestinatario()));
			notificaDTO.setSquadraDelMittente(getMapperFactory().getSquadraMapper().convertiInSquadraDTO(notificaSquadraService.getSquadraDelMittente()));
			
			return notificaDTO;
		}
}
