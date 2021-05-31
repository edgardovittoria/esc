package it.univaq.esc.model.notifiche;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Setter(value = AccessLevel.PRIVATE) @Getter(value = AccessLevel.PROTECTED) @NoArgsConstructor
public abstract class Notificabile {
	@Id
	private Integer idNotificabile;
	
	public Notificabile(Integer lastIdNotificabile) {
		setIdNotificabile(lastIdNotificabile + 1);
	}

	public abstract Map<String, Object> getInfo();
	public abstract String getTipoEventoPrenotabile();
}
