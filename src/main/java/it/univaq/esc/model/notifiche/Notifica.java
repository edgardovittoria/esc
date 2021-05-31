package it.univaq.esc.model.notifiche;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notifica {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.PRIVATE)
	private Long idNotifica;
	@ManyToOne
	@JoinColumn
	private UtentePolisportivaAbstract mittente;
	@ManyToOne
	@JoinColumn
	private UtentePolisportivaAbstract destinatario;
	@Column
	private boolean letta = false;
	@ManyToOne
	@JoinColumn
	private Notificabile evento;

	public Integer getIdEvento() {
		return (Integer)getEvento().getInfo().get("identificativo");
	}

}
