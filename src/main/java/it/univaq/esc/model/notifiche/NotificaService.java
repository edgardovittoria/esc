package it.univaq.esc.model.notifiche;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
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
	
	
	public NotificaService(ElementiPrenotazioneFactory factoryStatiNotifiche) {
		setFactoryStatiNotifiche(factoryStatiNotifiche);
		setNotifica(new Notifica());
		setStatoNotifica();
		
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
		setStatoNotifica();
	}
	
	protected void setStatoNotifica() {
		this.statoNotifica = getFactoryStatiNotifiche().getStatoNotifica((String)getEvento().getInfo().get("tipoPrenotazione"));
				
	}
	
	public Integer getIdNotifica() {
		return getNotifica().getIdNotifica();
	}
	
	public void impostaParametri(Notifica notifica) {
		setDestinatario(notifica.getDestinatario());
		setMittente(notifica.getMittente());
		setEvento(notifica.getEvento());
		setLetta(notifica.isLetta());
	}
	
	public String getModalitaNotifica() {
		return ModalitaPrenotazione.SINGOLO_UTENTE.toString();
	}
	
	public void impostaNotifica(Notifica notifica) {
		setNotifica(notifica);
	}
}

