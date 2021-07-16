package it.univaq.esc.model.notifiche;

import it.univaq.esc.model.utenti.Squadra;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "NOTIFICA_SQUADRA")
@Getter
@Setter
@NoArgsConstructor
public class NotificaSquadra extends Notifica{

	@ManyToOne
	@JoinColumn
	private Squadra squadraDelMittente;
	@ManyToOne
	@JoinColumn
	private Squadra squadraDelDestinatario;
}
