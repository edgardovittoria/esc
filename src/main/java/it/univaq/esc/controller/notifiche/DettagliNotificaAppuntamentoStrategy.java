package it.univaq.esc.controller.notifiche;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.NotificabileDTO;
import it.univaq.esc.model.TipoEventoNotificabile;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE) 
public class DettagliNotificaAppuntamentoStrategy extends DettagliNotificaStrategy{
	
	private RegistroAppuntamenti registroAppuntamenti;
	
	public DettagliNotificaAppuntamentoStrategy(RegistroAppuntamenti registroAppuntamenti) {
		setRegistroAppuntamenti(registroAppuntamenti);
		setMapperFactory(getMapperFactory());
	}

	@Override
	public NotificabileDTO getDettagliNotifica(Integer idEvento) {
		Appuntamento appuntamento = getRegistroAppuntamenti().getAppuntamentoById(idEvento);
		AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper(appuntamento.getTipoPrenotazione().toString()).convertiInAppuntamentoDTO(appuntamento);		
		return appDTO;
	}
	
	
	@Override
	@Resource(name = "MAPPER_SINGOLO_UTENTE")
	protected void setMapperFactory(MapperFactory mapperFactory) {
		super.setMapperFactory(mapperFactory);
	}
	
	static {
		FactoryDettagliNotificaStrategy.registra(TipoEventoNotificabile.APPUNTAMENTO.toString(), DettagliNotificaAppuntamentoStrategy.class);
	}

}
