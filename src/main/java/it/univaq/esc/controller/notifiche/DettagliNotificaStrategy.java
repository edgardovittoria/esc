package it.univaq.esc.controller.notifiche;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
import it.univaq.esc.dtoObjects.NotificabileDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PROTECTED) @Setter(value = AccessLevel.PROTECTED)
public abstract class DettagliNotificaStrategy {
	
	private MapperFactory mapperFactory;

	public abstract NotificabileDTO getDettagliNotifica(Integer idEvento);
}
