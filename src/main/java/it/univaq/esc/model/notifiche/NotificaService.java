package it.univaq.esc.model.notifiche;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import it.univaq.esc.model.Notificabile;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Service
@Scope("prototype")
@Getter @Setter(value = AccessLevel.PRIVATE)
public class NotificaService {


	private Notifica notifica;
	
	@Getter(value = AccessLevel.PRIVATE)
	private FactoryStatiNotifiche factoryStatiNotifiche;
	
	private NotificaState statoNotifica;
	
	@Autowired
	public NotificaService(FactoryStatiNotifiche factoryStatiNotifiche) {
		setFactoryStatiNotifiche(factoryStatiNotifiche);
		setNotifica(new Notifica());
		
	}
	
	public NotificaService(Notifica notifica, FactoryStatiNotifiche factoryStatiNotifiche) {
		setFactoryStatiNotifiche(factoryStatiNotifiche);
		setNotifica(notifica);
		setStatoNotifica();
		
	}
	
	
	
	public String getMessaggio() {
		return getStatoNotifica().getMessaggioNotifica(this);
	}
	
	public Long getIdEvento() {
		return getNotifica().getIdEvento();
	}
	
	public UtentePolisportivaAbstract getMittente() {
		return getNotifica().getMittente();
	}
	
	public UtentePolisportivaAbstract getDestinatario() {
		return getNotifica().getDestinatario();
	}
	
	public Notificabile getEvento() {
		return getNotifica().getEvento();
	}
	
	public boolean isLetta() {
		return getNotifica().isLetta();
	}
	
	public void setMittente(UtentePolisportivaAbstract mittente) {
		getNotifica().setMittente(mittente);
	}
	
	public void setDestinatario(UtentePolisportivaAbstract destinatario) {
		getNotifica().setDestinatario(destinatario);
	}
	
	public void setLetta(boolean isLetta) {
		getNotifica().setLetta(isLetta);
	}
	
	public void setEvento(Notificabile evento) {
		getNotifica().setEvento(evento);
		setStatoNotifica();
	}
	
	private void setStatoNotifica() {
		this.statoNotifica = getFactoryStatiNotifiche().getStato((String)getEvento().getInfo().get("tipoPrenotazione"));
				
	}
}

