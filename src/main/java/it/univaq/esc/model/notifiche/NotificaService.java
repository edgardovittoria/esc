package it.univaq.esc.model.notifiche;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Service()
@Scope("prototype")
@Getter @Setter(value = AccessLevel.PROTECTED)
public class NotificaService {


	private Notifica notifica;
	
	@Getter(value = AccessLevel.PRIVATE)
	private ElementiPrenotazioneFactory factoryStatiNotifiche;
	
	private NotificaState statoNotifica;
	
	

	
	public NotificaService(Notifica notifica, ElementiPrenotazioneFactory factoryStatiNotifiche) {
		setFactoryStatiNotifiche(factoryStatiNotifiche);
		setNotifica(notifica);
		
	}
	
	public void setTipoNotifica(TipoNotifica tipo) {
		getNotifica().setTipoNotifica(tipo);
	}
	
	
	
	public String getMessaggio() {
		return getStatoNotifica().getMessaggioNotifica(this);
	}
	
	public Integer getIdEvento() {
		return getNotifica().getIdEvento();
	}
	
	public UtentePolisportiva getMittente() {
		return getNotifica().getMittente();
	}
	
	public UtentePolisportiva getDestinatario() {
		return getNotifica().getDestinatario();
	}
	
	public Notificabile getEvento() {
		return getNotifica().getEvento();
	}
	
	public boolean isLetta() {
		return getNotifica().isLetta();
	}
	
	public void setMittente(UtentePolisportiva mittente) {
		getNotifica().setMittente(mittente);
	}
	
	public void setDestinatario(UtentePolisportiva destinatario) {
		getNotifica().setDestinatario(destinatario);
	}
	
	public void setLetta(boolean isLetta) {
		getNotifica().setLetta(isLetta);
	}
	
	public void setEvento(Notificabile evento) {
		getNotifica().setEvento(evento);
	}
	
	public void setStatoNotifica(String tipo) {
		this.statoNotifica = getFactoryStatiNotifiche().getStatoNotifica(tipo);
				
	}
	
	public Integer getIdNotifica() {
		return getNotifica().getIdNotifica();
	}
	

	public String getTipoNotifica() {
		return getNotifica().getTipoNotifica().toString();
	}
	
	public String getModalitaNotifica() {
		return ModalitaPrenotazione.SINGOLO_UTENTE.toString();
	}
	
	
}

