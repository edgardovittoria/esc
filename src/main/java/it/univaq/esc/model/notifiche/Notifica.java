package it.univaq.esc.model.notifiche;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@NoArgsConstructor
public class Notifica {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.PRIVATE)
	private Integer idNotifica;
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
