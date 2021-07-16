package it.univaq.esc.EntityDTOMappers;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.QuotaPartecipazioneDTO;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

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
