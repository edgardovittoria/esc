package it.univaq.esc.EntityDTOMappers;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.QuotaPartecipazioneDTO;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class QuotaPartecipazioneMapper extends EntityDTOMapper{

	
	

	public QuotaPartecipazioneDTO convertiInQuotaPartecipazioneDTO(QuotaPartecipazione quotaDaConvertire) {
		QuotaPartecipazioneDTO quotaDTO = new QuotaPartecipazioneDTO();

		quotaDTO.setCosto(quotaDaConvertire.getCosto().getAmmontare());
		quotaDTO.setPagata(quotaDaConvertire.isPagata());

		
		quotaDTO.setSportivo(quotaDaConvertire.getSportivoAssociato().getNominativoCompleto());
		
		return quotaDTO;
	}

}
