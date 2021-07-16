package it.univaq.esc.EntityDTOMappers;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.model.Sport;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
