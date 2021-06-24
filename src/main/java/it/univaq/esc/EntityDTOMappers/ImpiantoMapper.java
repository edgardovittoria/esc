package it.univaq.esc.EntityDTOMappers;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.ImpiantoDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class ImpiantoMapper extends EntityDTOMapper{

	

	public ImpiantoDTO convertiInImpiantoDTO(Impianto impiantoDaConvertire) {
		ImpiantoDTO impiantoDTO = new ImpiantoDTO();
        impiantoDTO.setIdImpianto(impiantoDaConvertire.getIdImpianto());
        impiantoDTO.setIndoor(impiantoDaConvertire.isIndoor());
        impiantoDTO.setPavimentazione(impiantoDaConvertire.getTipoPavimentazione().toString());
        for(Sport sport : impiantoDaConvertire.getSportPraticabili()){
            SportDTO sportDTO = getMapperFactory().getSportMapper().convertiInSportDTO(sport);
            impiantoDTO.getSportPraticabili().add(sportDTO);
        }
        
        for(Appuntamento appuntamento : impiantoDaConvertire.getListaAppuntamenti()){
        	impostaMapperFactory(appuntamento.getModalitaPrenotazione());
        	AppuntamentoDTO appuntamentoDTO = getMapperFactory().getAppuntamentoMapper(appuntamento.getTipoPrenotazione()).convertiInAppuntamentoDTO(appuntamento);
            impiantoDTO.getAppuntamenti().add(appuntamentoDTO);
        }
        
        return impiantoDTO;
	}
	
	

}
