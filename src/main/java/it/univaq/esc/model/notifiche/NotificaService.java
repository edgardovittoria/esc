package it.univaq.esc.model.notifiche;

import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.prenotazioni.Notificabile;
import it.univaq.esc.model.prenotazioni.TipoPrenotazione;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service()
@Scope("prototype")
@Getter
@Setter(value = AccessLevel.PROTECTED)
public class NotificaService {

	private Notifica notifica;

	@Getter(value = AccessLevel.PRIVATE)
	private ElementiPrenotazioneFactory factoryStatiNotifiche;

	private NotificaState statoNotifica;

	public NotificaService(Notifica notifica, ElementiPrenotazioneFactory factoryStatiNotifiche) {
		setFactoryStatiNotifiche(factoryStatiNotifiche);
		setNotifica(notifica);

	}

	public void impostaDatiNotifica(TipoNotifica tipoNotifica, UtentePolisportiva mittente,
			UtentePolisportiva destinatario, Notificabile evento) {
		getNotifica().setTipoNotifica(tipoNotifica);
		setStatoNotifica(tipoNotifica.toString());
		getNotifica().setDestinatario(destinatario);
		getNotifica().setEvento(evento);
		getNotifica().setLetta(false);
		getNotifica().setMittente(mittente);
		getNotifica().setMessaggio(costruisciMessaggio());
	}

	private String costruisciMessaggio() {
		return getStatoNotifica().getMessaggioNotifica(this);
	}

	public void modificaMessaggio(String nuovoMessaggio) {
		if (!nuovoMessaggio.isEmpty()) {
			getNotifica().setMessaggio(nuovoMessaggio);
		}
	}

	public Integer getIdEvento() {
		return (Integer) getNotifica().getEvento().getInfo().get("identificativo");
	}

	public String getNominativoCompletoMittente() {
		UtentePolisportiva mittente = getNotifica().getMittente();
		return mittente.getNominativoCompleto();
	}

	public boolean isIndirizzataA(UtentePolisportiva utente) {
		UtentePolisportiva destinatario = getNotifica().getDestinatario();
		return destinatario.isEqual(utente);
	}

	public String getTipoPrenotazioneEvento() {
		TipoPrenotazione tipoPrenotazioneEvento = (TipoPrenotazione) getEvento().getInfo().get("tipoPrenotazione");
		return tipoPrenotazioneEvento.toString();
	}

	public Notificabile getEvento() {
		return getNotifica().getEvento();
	}

	public String getTipoEventoNotificabile() {
		return getEvento().getTipoEventoNotificabile();
	}

	public String getNomeSportEvento() {
		String sportNome = (String) getEvento().getInfo().get("sportNome");
		return sportNome;
	}

	public Integer getNumeroIncontriEvento() {
		if (getEvento().getInfo().containsKey("numeroIncontri")) {
			return (Integer) getEvento().getInfo().get("numeroIncontri");
		}
		return null;
	}

	public boolean isLetta() {
		return getNotifica().isLetta();
	}

	public void impostaComeLetta() {
		getNotifica().setLetta(true);
	}

	public void setStatoNotifica(String tipo) {
		this.statoNotifica = getFactoryStatiNotifiche().getStatoNotifica(tipo);
	}

	public Integer getIdNotifica() {
		return getNotifica().getIdNotifica();
	}

	public boolean isSuoQuesto(Integer identificativo) {
		return getIdNotifica() == identificativo;
	}

	public String getTipoNotifica() {
		TipoNotifica tipoNotifica = getNotifica().getTipoNotifica();
		return tipoNotifica.toString();
	}

	public String getModalitaNotifica() {
		return ModalitaPrenotazione.SINGOLO_UTENTE.toString();
	}

}
