package it.univaq.esc.model.notifiche;

import it.univaq.esc.model.prenotazioni.Notificabile;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value = "NOTIFICA")
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
	private UtentePolisportiva mittente;
	@ManyToOne
	@JoinColumn
	private UtentePolisportiva destinatario;
	@Column
	private boolean letta = false;
	@ManyToOne
	@JoinColumn
	private Notificabile evento;
	
	@Enumerated(EnumType.STRING)
	private TipoNotifica tipoNotifica;
	
	@Column
	private String messaggio;

}
