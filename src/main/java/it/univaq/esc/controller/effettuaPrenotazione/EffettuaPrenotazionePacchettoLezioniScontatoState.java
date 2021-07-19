package it.univaq.esc.controller.effettuaPrenotazione;

import java.util.Map;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.RegistroQuotePartecipazione;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
public class EffettuaPrenotazionePacchettoLezioniScontatoState extends EffettuaPrenotazioneState{

	private EffettuaPrenotazioneState effettuaPrenotazioneLezioneState;
	
	public EffettuaPrenotazionePacchettoLezioniScontatoState(RegistroNotifiche registroNotifiche,
			RegistroSport registroSport, RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtenti,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, RegistroSquadre registroSquadre,
			RegistroQuotePartecipazione registroQuotePartecipazione, EffettuaPrenotazioneLezioneState effettuaPrenotazioneLezioneState) {
		super(registroNotifiche, registroSport, registroImpianti, registroUtenti, registroAppuntamenti, registroPrenotazioni,
				catalogoPrenotabili, registroSquadre, registroQuotePartecipazione);
		
		setEffettuaPrenotazioneLezioneState(effettuaPrenotazioneLezioneState);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<String, Object> getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(
			EffettuaPrenotazioneHandler effettuaPrenotazioneHandler) {
		return getEffettuaPrenotazioneLezioneState().getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(effettuaPrenotazioneHandler);
	}

	@Override
	public PrenotazioneDTO impostaPrenotazioneConDatiDellaFormPerRiepilogo(FormPrenotabile formDati,
			EffettuaPrenotazioneHandler controller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aggiornaElementiLegatiAllaPrenotazioneConfermata(EffettuaPrenotazioneHandler controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> getDatiOpzioniPrenotazioneAggiornatiInBaseAllaMappa(
			Map<String, Object> datiGiaCompilati) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, Object identificativoPartecipante) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getDatiOpzioniPerPrenotazioneInModalitaDirettore(
			EffettuaPrenotazioneHandler controller) {
		// TODO Auto-generated method stub
		return null;
	}

}
