package it.univaq.esc.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter @Setter @NoArgsConstructor
public class Notifica {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idNotifica;
	@Column
	private String messaggio;
	@ManyToOne
	@JoinColumn
	private UtentePolisportivaAbstract mittente;
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UtentePolisportivaAbstract> destinatari;
	@Column
	private boolean letta = false;
	@ManyToOne
	@JoinColumn
	private Notificabile evento;
	
	
	public Long getIdEvento() {
		return (Long)getEvento().getInfo().get("identificativo");
	}
}
