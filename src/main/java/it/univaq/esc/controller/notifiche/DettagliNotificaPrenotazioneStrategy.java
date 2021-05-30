package it.univaq.esc.controller.notifiche;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.NotificabileDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.TipoEventoNotificabile;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Component
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE) @AllArgsConstructor
public class DettagliNotificaPrenotazioneStrategy extends DettagliNotificaStrategy{
	
	private RegistroPrenotazioni registroPrenotazioni;
	private RegistroAppuntamenti registroAppuntamenti;
		
	static {
		FactoryDettagliNotificaStrategy.registra(TipoEventoNotificabile.PRENOTAZIONE.toString(), DettagliNotificaPrenotazioneStrategy.class);
	}

	@Override
	public NotificabileDTO getDettagliNotifica(Long idEvento) {
		
		Prenotazione prenotazione = getRegistroPrenotazioni().getPrenotazioneById(idEvento);
		 
		PrenotazioneDTO prenDTO = new PrenotazioneDTO();
		Map<String, Object> mappaPrenotazione = new HashMap<String, Object>();
		mappaPrenotazione.put("prenotazione", prenotazione);
		List<Appuntamento> appuntamentiPrenotazione = getRegistroAppuntamenti().getAppuntamentiByPrenotazioneId(idEvento);
		mappaPrenotazione.put("appuntamentiPrenotazione", appuntamentiPrenotazione);
		prenDTO.impostaValoriDTO(mappaPrenotazione);
		
		return prenDTO;
	}

}
