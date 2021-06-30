package it.univaq.esc.EntityDTOMappers;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.model.Sport;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class SportMapper extends EntityDTOMapper{

	
	public SportDTO convertiInSportDTO(Sport sportDaConvertire) {
        SportDTO sportDTO = new SportDTO();
        sportDTO.setNome(sportDaConvertire.getNome());
        sportDTO.setPostiLiberi(sportDaConvertire.getNumeroGiocatoriPerIncontro());
        sportDTO.setNumeroMinimoGiocatoriPerSquadra(sportDaConvertire.getNumeroMinimoGiocatoriPerSquadra());
    
        return sportDTO;
	}
}
