package it.univaq.esc.EntityDTOMappers;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoCorso;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Singleton
@NoArgsConstructor
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
public class AppuntamentoCorsoMapper extends AppuntamentoMapper{

	@Override
		public AppuntamentoDTO convertiInAppuntamentoDTO(Appuntamento appuntamentoDaConvertire) {
			AppuntamentoDTO appuntamentoDTO = super.convertiInAppuntamentoDTO(appuntamentoDaConvertire);
			
			AppuntamentoCorso appuntamentoCorso = (AppuntamentoCorso) appuntamentoDaConvertire;
			
			appuntamentoDTO.setInvitati(appuntamentoCorso.getNominativiInvitati());
			
			appuntamentoDTO.setIstruttore(appuntamentoCorso.getNominativoIstruttore());
			
			
			return appuntamentoDTO;
		}
}
