package it.univaq.esc.EntityDTOMappers;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoLezione;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Singleton
@NoArgsConstructor
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
public class AppuntamentoLezioneMapper extends AppuntamentoMapper{

	@Override
		public AppuntamentoDTO convertiInAppuntamentoDTO(Appuntamento appuntamentoDaConvertire) {
			
			AppuntamentoDTO appuntamentoDTO = super.convertiInAppuntamentoDTO(appuntamentoDaConvertire);
			
			AppuntamentoLezione appuntamentoLezione = (AppuntamentoLezione) appuntamentoDaConvertire;
			
			appuntamentoDTO.setIstruttore(appuntamentoLezione.getNominativoIstruttore());
			
			
			return appuntamentoDTO;
		}
}
