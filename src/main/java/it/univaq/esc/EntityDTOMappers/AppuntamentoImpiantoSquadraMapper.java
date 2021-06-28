package it.univaq.esc.EntityDTOMappers;

import java.util.ArrayList;
import java.util.List;

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
						
			appuntamentoDTO.setInvitati(getListaNomiSquadreDallaLista(appuntamentoImpiantoSquadra.getSquadreInvitate()));
			appuntamentoDTO.setSquadrePartecipanti(getIdSquadrePartecipantiDallaLista(appuntamentoImpiantoSquadra.getSquadrePartecipanti()));
			
			return appuntamentoDTO;
		}
	
	
	private List<String> getListaNomiSquadreDallaLista(List<Squadra> squadreInvitate) {
		List<String> listaNomiSquadreInvitate = new ArrayList<String>();
		squadreInvitate.forEach((squadra) -> listaNomiSquadreInvitate.add(squadra.getNome()));
		return listaNomiSquadreInvitate;
	}
	
	private List<Integer> getIdSquadrePartecipantiDallaLista(List<Squadra> squadrePartecipanti){
		List<Integer> listaIdSquadrePartecipanti = new ArrayList<Integer>();
		squadrePartecipanti.forEach((squadra) -> listaIdSquadrePartecipanti.add(squadra.getIdSquadra()));
		return listaIdSquadrePartecipanti;
	}
}
