package it.univaq.esc.EntityDTOMappers;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpiantoSquadra;
import it.univaq.esc.model.utenti.Squadra;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Singleton
@NoArgsConstructor
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
public class AppuntamentoImpiantoSquadraMapper extends AppuntamentoMapper{

	@Override
		public AppuntamentoDTO convertiInAppuntamentoDTO(Appuntamento appuntamentoDaConvertire) {
			AppuntamentoDTO appuntamentoDTO = super.convertiInAppuntamentoDTO(appuntamentoDaConvertire);
			
			AppuntamentoImpiantoSquadra appuntamentoImpiantoSquadra = (AppuntamentoImpiantoSquadra) appuntamentoDaConvertire;
						
			for(Squadra squadraInviata : appuntamentoImpiantoSquadra.getSquadreInvitate()) {
				appuntamentoDTO.aggiungiInvitato(squadraInviata.getNome());
			}
			
			for(Squadra squadraPartecipante : appuntamentoImpiantoSquadra.getSquadrePartecipanti()) {
				appuntamentoDTO.aggiungiSquadraPartecipante(squadraPartecipante.getIdSquadra());
			}
			
			
			return appuntamentoDTO;
		}
}
