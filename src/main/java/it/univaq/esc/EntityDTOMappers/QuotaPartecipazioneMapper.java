package it.univaq.esc.EntityDTOMappers;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.QuotaPartecipazioneDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class QuotaPartecipazioneMapper extends EntityDTOMapper{

	
	

	public QuotaPartecipazioneDTO convertiInQuotaPartecipazioneDTO(QuotaPartecipazione quotaDaConvertire) {
		QuotaPartecipazioneDTO quotaDTO = new QuotaPartecipazioneDTO();

		quotaDTO.setCosto(quotaDaConvertire.getCosto());
		quotaDTO.setPagata(quotaDaConvertire.isPagata());

		UtentePolisportivaDTO sportivo = getMapperFactory().getUtenteMapper()
				.convertiInUtentePolisportivaDTO(quotaDaConvertire.getSportivoAssociato());
		quotaDTO.setSportivo(sportivo);
		
		return quotaDTO;
	}

}
