package it.univaq.esc.model.notifiche;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Notificabile;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneSquadra;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.repository.NotificaRepository;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Singleton
@Getter
@Setter(value = AccessLevel.PRIVATE)
@DependsOn("beanUtil")
public class RegistroNotifiche {

	@Getter(value = AccessLevel.PRIVATE)
	private NotificaRepository notificaRepository;

	private List<NotificaService> listaNotifiche = new ArrayList<NotificaService>();

	public RegistroNotifiche(NotificaRepository notificaRepository) {
		setNotificaRepository(notificaRepository);
		popola();
	}

	private void popola() {
		List<NotificaService> listaNotifiche = new ArrayList<NotificaService>();
		for (Notifica notifica : getNotificaRepository().findAll()) {
			if (!notifica.getEvento().getInfo().containsKey("modalitaPrenotazione")  || notifica.getEvento().getInfo().get("modalitaPrenotazione")
					.equals(ModalitaPrenotazione.SINGOLO_UTENTE.toString())) {
				NotificaService notificaService = getElementiPrenotazioneFactorySingoloUtente().getNotifica(notifica);
				notificaService.setStatoNotifica(notifica.getTipoNotifica().toString());
				listaNotifiche.add(notificaService);
			} else {
				NotificaSquadraService notificaService = (NotificaSquadraService) getElementiPrenotazioneFactorySquadra()
						.getNotifica(notifica);
				notificaService.setStatoNotifica(notifica.getTipoNotifica().toString());
				listaNotifiche.add(notificaService);
			}
		}
		setListaNotifiche(listaNotifiche);
	}

	private ElementiPrenotazioneFactory getElementiPrenotazioneFactorySingoloUtente() {
		ElementiPrenotazioneFactory elementiPrenotazioneFactory = BeanUtil.getBean(
				"ELEMENTI_PRENOTAZIONE_" + ModalitaPrenotazione.SINGOLO_UTENTE.toString(),
				ElementiPrenotazioneFactory.class);
		return elementiPrenotazioneFactory;
	}

	private ElementiPrenotazioneFactory getElementiPrenotazioneFactorySquadra() {
		ElementiPrenotazioneFactory elementiPrenotazioneFactory = BeanUtil.getBean(
				"ELEMENTI_PRENOTAZIONE_" + ModalitaPrenotazione.SQUADRA.toString(), ElementiPrenotazioneFactory.class);
		return elementiPrenotazioneFactory;
	}

	public List<NotificaService> getNotifichePerDestinatario(UtentePolisportiva destinatario) {
		List<NotificaService> notificheDestinatario = new ArrayList<NotificaService>();
		for (NotificaService notifica : getListaNotifiche()) {
			if (notifica.isIndirizzataA(destinatario)) {
				notificheDestinatario.add(notifica);
			}
		}
		return notificheDestinatario;
	}

	public void salvaNotifica(NotificaService notificaDaSalvare) {
		Notifica notifica = notificaDaSalvare.getNotifica();
		getListaNotifiche().add(notificaDaSalvare);
		getNotificaRepository().save(notifica);
	}

	public void aggiornaNotificaSuDatabase(NotificaService notificaDaAggiornare) {
		Notifica notifica = notificaDaAggiornare.getNotifica();
		getNotificaRepository().save(notifica);
	}

	public NotificaService getNotificaById(Integer idNotifica) {
		for (NotificaService notifica : getListaNotifiche()) {
			if (notifica.isSuoQuesto(idNotifica)) {
				return notifica;
			}
		}
		return null;
	}

	public void impostaNotificaPerUtenteInvitatoAPrenotazioneImpianto(UtentePolisportiva utenteInvitato,
			Prenotazione prenotazione) {
		NotificaService notifica = getElementiPrenotazioneFactorySingoloUtente().getNotifica(new Notifica());
		notifica.impostaDatiNotifica(TipoNotifica.INVITO_IMPIANTO, prenotazione.getSportivoPrenotante(), utenteInvitato,
				prenotazione);
		salvaNotifica(notifica);
	}

	public void impostaNotificaPerIstruttoreAssociatoANuovaLezione(UtentePolisportiva istruttore,
			Appuntamento appuntamentoLezione) {
		NotificaService notifica = getElementiPrenotazioneFactorySingoloUtente().getNotifica(new Notifica());
		notifica.impostaDatiNotifica(TipoNotifica.ISTRUTTORE_LEZIONE,
				appuntamentoLezione.getUtenteCheHaEffettuatoLaPrenotazioneRelativa(), istruttore, appuntamentoLezione);
		salvaNotifica(notifica);
	}

	public void impostaNotificaPerUtenteInvitatoAPrenotazioneCorso(UtentePolisportiva utenteInvitato,
			Prenotazione prenotazione) {
		NotificaService notifica = getElementiPrenotazioneFactorySingoloUtente().getNotifica(new Notifica());
		notifica.impostaDatiNotifica(TipoNotifica.INVITO_CORSO, prenotazione.getSportivoPrenotante(), utenteInvitato,
				prenotazione);
		salvaNotifica(notifica);
	}

	public void impostaNotificaPerAmministratoreSquadraInvitataAPrenotazioneImpianto(UtentePolisportiva amministratore,
			Squadra squadraInvitata, Prenotazione prenotazione) {
		PrenotazioneSquadra prenotazioneSquadraInAtto = (PrenotazioneSquadra) prenotazione;
		NotificaSquadraService notifica = (NotificaSquadraService) getElementiPrenotazioneFactorySquadra()
				.getNotifica(new NotificaSquadra());
		notifica.impostaDatiNotificaSquadra(TipoNotifica.INVITO_IMPIANTO,
				prenotazioneSquadraInAtto.getSportivoPrenotante(), amministratore, prenotazioneSquadraInAtto,
				prenotazioneSquadraInAtto.getSquadraPrenotante(), squadraInvitata);
		salvaNotifica(notifica);
	}
	
	public String getMessaggioNotificaPerCreazione(Notificabile nuovaStruttura) {
		NotificaService notifica = getElementiPrenotazioneFactorySingoloUtente().getNotifica(new Notifica());
		notifica.impostaDatiNotifica(TipoNotifica.CREAZIONE_STRUTTURA, null, null, nuovaStruttura);
		return notifica.getNotifica().getMessaggio();
	}
}
