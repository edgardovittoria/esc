package it.univaq.esc.controller.promuoviPolisportiva;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Pavimentazione;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.StrutturaPolisportiva;
import it.univaq.esc.model.notifiche.Notifica;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.notifiche.TipoNotifica;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Singleton
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
public class CreazioneNuovoImpiantoState extends CreazioneNuovaStrutturaState {
	
	

	public CreazioneNuovoImpiantoState(RegistroSport registroSport, RegistroImpianti registroImpianti,
			RegistroNotifiche registroNotifiche, RegistroUtentiPolisportiva registroUtentiPolisportiva) {
		super(registroSport, registroImpianti, registroNotifiche, registroUtentiPolisportiva);
	}

	@Override
	public Map<String, Object> getDatiPerCreazioneNuovaStruttura() {
		Map<String, Object> mappaNomiSportEPavimentazioni = new HashMap<String, Object>();
		mappaNomiSportEPavimentazioni.put("pavimentazioniDisponibili",
				Pavimentazione.getListaPavimentazioniComeStringhe());
		mappaNomiSportEPavimentazioni.put("sportPraticabili", getMapperFactory().getSportMapper()
				.convertiInDTOLaLista(getRegistroSport().getListaSportPolisportiva()));
		return mappaNomiSportEPavimentazioni;
	}

	@Override
	public void creaNuovaStrutturaConfermata(StrutturaPolisportiva struttraDaInserireNellaPolisportiva) {
		getRegistroImpianti().aggiungiImpianto((Impianto) struttraDaInserireNellaPolisportiva);	
	}

	@Override
	public void creaNotificaCreazioneNuovaStrutturaConMessaggioPerSingoloUtente(
			Map<String, String> mappaEmailDirettoreEMessaggioNotifica, UtentePolisportiva utenteDestinatario, StrutturaPolisportiva nuovaStruttura) {
		UtentePolisportiva direttore = getRegistroUtentiPolisportiva().trovaUtenteInBaseAllaSua(mappaEmailDirettoreEMessaggioNotifica.get("emailDirettore"));
		NotificaService notifica = getElementiPrenotazioneFactory().getNotifica(new Notifica());
		notifica.impostaDatiNotifica(TipoNotifica.CREAZIONE_IMPIANTO, direttore, utenteDestinatario, nuovaStruttura);
		notifica.modificaMessaggio(mappaEmailDirettoreEMessaggioNotifica.get("messaggioNotifica"));
		getRegistroNotifiche().salvaNotifica(notifica);	
	}

}
