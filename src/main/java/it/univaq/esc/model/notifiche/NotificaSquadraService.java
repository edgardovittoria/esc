package it.univaq.esc.model.notifiche;

import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.prenotazioni.Notificabile;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service()
@Scope("prototype")
@Getter @Setter(value = AccessLevel.PRIVATE)
public class NotificaSquadraService extends NotificaService{

	public NotificaSquadraService(Notifica notifica, ElementiPrenotazioneFactory factoryStatiNotifiche) {
		super(notifica, factoryStatiNotifiche);
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
	
	public void impostaDatiNotificaSquadra(TipoNotifica tipoNotifica, UtentePolisportiva mittente,
			UtentePolisportiva destinatario, Notificabile evento, Squadra squadraDelMittente, Squadra squadraDelDestinatario) {
				setSquadraDelMittente(squadraDelMittente);
				setSquadraDelDestinatario(squadraDelDestinatario);
				super.impostaDatiNotifica(tipoNotifica, mittente, destinatario, evento);
	}
	
	private void setSquadraDelMittente(Squadra squadraDelMittente) {
		if(getNotifica() instanceof NotificaSquadra)
		((NotificaSquadra)getNotifica()).setSquadraDelMittente(squadraDelMittente);
	}
	
	private void setSquadraDelDestinatario(Squadra squadraDelDestinatario) {
		if(getNotifica() instanceof NotificaSquadra)
		((NotificaSquadra)getNotifica()).setSquadraDelDestinatario(squadraDelDestinatario);
	}
	
	
	@Override
	public String getModalitaNotifica() {
		return ModalitaPrenotazione.SQUADRA.toString();
	}
	

}
