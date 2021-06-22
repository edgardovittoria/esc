package it.univaq.esc.EntityDTOMappers;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoCorso;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
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
			
			for(UtentePolisportivaAbstract invitato : appuntamentoCorso.getInvitati()) {
				appuntamentoDTO.aggiungiInvitato((String)invitato.getProprieta().get("email"));
			}
			
			appuntamentoDTO.setIstruttore((String)appuntamentoCorso.getIstruttore().getProprieta().get("email"));
			
			
			return appuntamentoDTO;
		}
}
