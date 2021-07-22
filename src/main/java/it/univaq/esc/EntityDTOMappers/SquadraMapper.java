package it.univaq.esc.EntityDTOMappers;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.dtoObjects.SquadraDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Singleton
@NoArgsConstructor
public class SquadraMapper extends EntityDTOMapper{

	
	
	public SquadraDTO convertiInSquadraDTO(Squadra squadraDaConvertire) {
		SquadraDTO squadraDTO = new SquadraDTO();
		squadraDTO.setIdSquadra(squadraDaConvertire.getIdSquadra());
		squadraDTO.setNome(squadraDaConvertire.getNome());
		SportDTO sportDTO = getMapperFactory().getSportMapper().convertiInSportDTO(squadraDaConvertire.getSport());
		squadraDTO.setSport(sportDTO);
		squadraDTO.setMembri(convertiInDTOLaListaDei(squadraDaConvertire.getMembri()));
		squadraDTO.setAmministratori(convertiInDTOLaListaDei(squadraDaConvertire.getAmministratori()));
		squadraDTO.setAppuntamenti(convertiInDTOGliAppuntamentiDel(squadraDaConvertire.getCalendarioSquadra()));		
		return squadraDTO;
	}
	
	private List<UtentePolisportivaDTO> convertiInDTOLaListaDei(List<UtentePolisportiva> membriOAmministratori){
		List<UtentePolisportivaDTO> membriDTO = new ArrayList<UtentePolisportivaDTO>();
		for(UtentePolisportiva membro : membriOAmministratori) {
			UtentePolisportivaDTO membroDTO = getMapperFactory().getUtenteMapper().convertiInUtentePolisportivaDTO(membro);
			membriDTO.add(membroDTO);
		}
		return membriDTO;
	}
	
	private List<AppuntamentoDTO> convertiInDTOGliAppuntamentiDel(Calendario calendarioSquadra){
		List<AppuntamentoDTO> appuntamentiDTO = new ArrayList<AppuntamentoDTO>();
		for(Appuntamento appuntamento : calendarioSquadra.getListaAppuntamenti()) {
			impostaMapperFactory(appuntamento.getModalitaPrenotazione());
			AppuntamentoDTO appuntamentoDTO = getMapperFactory().getAppuntamentoMapper(appuntamento.getTipoPrenotazione().toString()).convertiInAppuntamentoDTO(appuntamento);
			appuntamentiDTO.add(appuntamentoDTO);
		}
		return appuntamentiDTO;
	}

}
