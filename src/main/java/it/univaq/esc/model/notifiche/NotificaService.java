package it.univaq.esc.model.notifiche;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.costi.ModalitaPrenotazione;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Service(value = "NOTIFICA_SINGOLO_UTENTE")
@Scope("prototype")
@Getter @Setter(value = AccessLevel.PRIVATE)
public class NotificaService {


	private Notifica notifica;
	
	@Getter(value = AccessLevel.PRIVATE)
	private ElementiPrenotazioneFactory factoryStatiNotifiche;
	
	private NotificaState statoNotifica;
	
	
	public NotificaService() {
		setFactoryStatiNotifiche(BeanUtil.getBean(ModalitaPrenotazione.SINGOLO_UTENTE.toString(), ElementiPrenotazioneFactory.class));
		setNotifica(new Notifica());
		
	}
	
	public NotificaService(Notifica notifica, ElementiPrenotazioneFactory factoryStatiNotifiche) {
		setFactoryStatiNotifiche(factoryStatiNotifiche);
		setNotifica(notifica);
		setStatoNotifica();
		
	}
	
	
	
	public String getMessaggio() {
		return getStatoNotifica().getMessaggioNotifica(this);
	}
	
	public Integer getIdEvento() {
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
		this.statoNotifica = getFactoryStatiNotifiche().getStatoNotifica((String)getEvento().getInfo().get("tipoPrenotazione"));
				
	}
	
	public Integer getIdNotifica() {
		return getNotifica().getIdNotifica();
	}
}

