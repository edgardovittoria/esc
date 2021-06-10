package it.univaq.esc.model.notifiche;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.utenti.Squadra;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Service
@Scope("prototype")
@Getter @Setter(value = AccessLevel.PRIVATE)
public class NotificaSquadraService extends NotificaService{

	public NotificaSquadraService(ElementiPrenotazioneFactory factoryStatiNotifiche) {
		super(factoryStatiNotifiche);
		// TODO Auto-generated constructor stub
	}
	
	public NotificaSquadraService(Notifica notifica, ElementiPrenotazioneFactory factoryStatiNotifiche) {
		super(notifica, factoryStatiNotifiche);
		
	}
	
	public void setSquadraDelMittente(Squadra squadraDelMittente) {
		((NotificaSquadra)getNotifica()).setSquadraDelMittente(squadraDelMittente);
	}
	
	public void setSquadraDelDestinatario(Squadra squadraDelDestinatario) {
		((NotificaSquadra)getNotifica()).setSquadraDelDestinatario(squadraDelDestinatario);
	}
	
	public Squadra getSquadraDelMittente() {
		return ((NotificaSquadra)getNotifica()).getSquadraDelMittente();
	}
	
	public Squadra getSquadraDelDestinatario() {
		return ((NotificaSquadra)getNotifica()).getSquadraDelDestinatario();
	}
	

}
