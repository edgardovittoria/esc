package it.univaq.esc.model.notifiche;

import static org.hamcrest.CoreMatchers.instanceOf;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.utenti.Squadra;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Service()
@Scope("prototype")
@Getter @Setter(value = AccessLevel.PRIVATE)
public class NotificaSquadraService extends NotificaService{

	public NotificaSquadraService(ElementiPrenotazioneFactory factoryStatiNotifiche) {
		super(factoryStatiNotifiche);		
		setNotifica(new NotificaSquadra());
	}
	
	public NotificaSquadraService(NotificaSquadra notifica, ElementiPrenotazioneFactory factoryStatiNotifiche) {
		super(notifica, factoryStatiNotifiche);
		
	}
	
	public void setSquadraDelMittente(Squadra squadraDelMittente) {
		if(getNotifica() instanceof NotificaSquadra)
		((NotificaSquadra)getNotifica()).setSquadraDelMittente(squadraDelMittente);
	}
	
	public void setSquadraDelDestinatario(Squadra squadraDelDestinatario) {
		if(getNotifica() instanceof NotificaSquadra)
		((NotificaSquadra)getNotifica()).setSquadraDelDestinatario(squadraDelDestinatario);
	}
	
	public Squadra getSquadraDelMittente() {
		if(getNotifica() instanceof NotificaSquadra) {
		return ((NotificaSquadra)getNotifica()).getSquadraDelMittente();
		}
		return null;
	}
	
	public Squadra getSquadraDelDestinatario() {
		if(getNotifica() instanceof NotificaSquadra) {
		return ((NotificaSquadra)getNotifica()).getSquadraDelDestinatario();
		}
		return null;
	}
	
	@Override
	public void impostaParametri(Notifica notifica) {
		super.impostaParametri(notifica);
		NotificaSquadra notificaSquadra = (NotificaSquadra) notifica;
		setSquadraDelDestinatario(notificaSquadra.getSquadraDelDestinatario());
		setSquadraDelMittente(notificaSquadra.getSquadraDelMittente());
	}
	

}
