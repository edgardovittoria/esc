package it.univaq.esc.dtoObjects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter(value = AccessLevel.PROTECTED) @NoArgsConstructor
public abstract class NotificabileDTO {
	
	private String tipoEventoNotificabile;
}
