package it.univaq.esc.controller.notifiche;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
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
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE) 
public class DettagliNotificaPrenotazioneStrategy extends DettagliNotificaStrategy{
	
	private RegistroPrenotazioni registroPrenotazioni;
	private RegistroAppuntamenti registroAppuntamenti;
	
	public DettagliNotificaPrenotazioneStrategy(RegistroPrenotazioni registroPrenotazioni, RegistroAppuntamenti registroAppuntamenti) {
		setRegistroAppuntamenti(registroAppuntamenti);
		setRegistroPrenotazioni(registroPrenotazioni);
		setMapperFactory(getMapperFactory());
	}
	
	@Override
	@Resource(name = "MAPPER_SINGOLO_UTENTE")
	protected void setMapperFactory(MapperFactory mapperFactory) {
		super.setMapperFactory(mapperFactory);
	}

	
		
	static {
		FactoryDettagliNotificaStrategy.registra(TipoEventoNotificabile.PRENOTAZIONE.toString(), DettagliNotificaPrenotazioneStrategy.class);
	}

	@Override
	public NotificabileDTO getDettagliNotifica(Integer idEvento) {
		
		Prenotazione prenotazione = getRegistroPrenotazioni().getPrenotazioneById(idEvento);
		List<Appuntamento> appuntamentiPrenotazione = getRegistroAppuntamenti().getAppuntamentiByPrenotazioneId(idEvento);
		 
		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper().convertiInPrenotazioneDTO(prenotazione, appuntamentiPrenotazione);
						
		return prenDTO;
	}

}
