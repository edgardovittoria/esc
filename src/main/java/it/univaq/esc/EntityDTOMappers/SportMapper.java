package it.univaq.esc.EntityDTOMappers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
	public List<SportDTO> convertiInDTOLaLista(List<Sport> sports){
		List<SportDTO> sportsDTO = new ArrayList<SportDTO>();
		for(Sport sport : sports) {
			sportsDTO.add(convertiInSportDTO(sport));
		}
		return sportsDTO;
	}
		
}
