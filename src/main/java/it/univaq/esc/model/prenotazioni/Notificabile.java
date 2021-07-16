package it.univaq.esc.model.prenotazioni;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;
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
