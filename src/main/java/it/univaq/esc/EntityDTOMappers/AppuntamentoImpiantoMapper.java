package it.univaq.esc.EntityDTOMappers;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpianto;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Singleton
@NoArgsConstructor
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
public class AppuntamentoImpiantoMapper extends AppuntamentoMapper {

	@Override
	public AppuntamentoDTO convertiInAppuntamentoDTO(Appuntamento appuntamentoDaConvertire) {
		
		AppuntamentoDTO appuntamentoDTO = super.convertiInAppuntamentoDTO(appuntamentoDaConvertire);
		
		AppuntamentoImpianto appuntamentoImpianto = (AppuntamentoImpianto) appuntamentoDaConvertire;
		
		for(UtentePolisportivaAbstract invitato : appuntamentoImpianto.getInvitati()) {
			appuntamentoDTO.aggiungiInvitato((String)invitato.getProprieta().get("email"));
		}
		
		
		return appuntamentoDTO;
	}
}
