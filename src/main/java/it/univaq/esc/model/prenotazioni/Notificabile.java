package it.univaq.esc.model.prenotazioni;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Setter(value = AccessLevel.PRIVATE) @Getter(value = AccessLevel.PUBLIC) @NoArgsConstructor
public abstract class Notificabile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idNotificabile;
	

	public abstract Map<String, Object> getInfo();
	public abstract String getTipoEventoNotificabile();
}
