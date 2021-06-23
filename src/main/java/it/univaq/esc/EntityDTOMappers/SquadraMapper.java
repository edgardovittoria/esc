package it.univaq.esc.EntityDTOMappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.dtoObjects.SquadraDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.NoArgsConstructor;

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
		
		List<UtentePolisportivaDTO> membriDTO = new ArrayList<UtentePolisportivaDTO>();
		for(UtentePolisportiva membro : squadraDaConvertire.getMembri()) {
			UtentePolisportivaDTO membroDTO = getMapperFactory().getUtenteMapper().convertiInUtentePolisportivaDTO(membro);
			membriDTO.add(membroDTO);
		}
		squadraDTO.setMembri(membriDTO);
		
		
		List<UtentePolisportivaDTO> amministratoriDTO = new ArrayList<UtentePolisportivaDTO>();
		for(UtentePolisportiva amministratore : squadraDaConvertire.getAmministratori()) {
			UtentePolisportivaDTO amministratoreDTO = getMapperFactory().getUtenteMapper().convertiInUtentePolisportivaDTO(amministratore);
			amministratoriDTO.add(amministratoreDTO);
		}
		squadraDTO.setAmministratori(amministratoriDTO);
		
		
		List<AppuntamentoDTO> appuntamentiDTO = new ArrayList<AppuntamentoDTO>();
		for(Appuntamento appuntamento : squadraDaConvertire.getCalendarioSquadra().getListaAppuntamenti()) {
			AppuntamentoDTO appuntamentoDTO = getMapperFactory().getAppuntamentoMapper(appuntamento.getTipoPrenotazione()).convertiInAppuntamentoDTO(appuntamento);
			appuntamentiDTO.add(appuntamentoDTO);
		}
		squadraDTO.setAppuntamenti(appuntamentiDTO);
		
		
		return squadraDTO;
	}

}
