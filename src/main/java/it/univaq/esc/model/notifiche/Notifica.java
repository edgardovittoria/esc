package it.univaq.esc.model.notifiche;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import it.univaq.esc.model.Notificabile;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter @Setter
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
	
	@Transient
	@Setter(value = AccessLevel.PRIVATE)
	@Getter(value = AccessLevel.PRIVATE)
	private NotificaState statoNotifica;
	
	

	
	
	public Long getIdEvento() {
		return (Long)getEvento().getInfo().get("identificativo");
	}
	
	public String getMessaggio() {
		return getStatoNotifica().getMessaggioNotifica(this);
	}
	
	public void setEvento(Notificabile evento) {
		this.evento = evento;
		this.setStatoNotifica(FactoryStatiNotifiche.getStato((String)getEvento().getInfo().get("tipoPrenotazione")));
	}
}
