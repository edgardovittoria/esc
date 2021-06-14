package it.univaq.esc.EntityDTOMappers;

import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;

public abstract class PrenotazioneSpecsMapper extends EntityDTOMapper{


	public abstract PrenotazioneSpecsDTO convertiInPrenotazioneSpecsDTO(PrenotazioneSpecs prenotazioneSpecs);
	
	
	protected void impostaDatiGeneraliPrenotazioneSpecs(PrenotazioneSpecs prenotazioneSpecs, PrenotazioneSpecsDTO prenotazioneSpecsDTO) {
		SportDTO sportAssociato = getMapperFactory().getSportMapper().convertiInSportDTO(prenotazioneSpecs.getSportAssociato());
        prenotazioneSpecsDTO.setSportAssociato(sportAssociato);
        prenotazioneSpecsDTO.setIdPrenotazioneSpecsDTO(prenotazioneSpecs.getId());
        prenotazioneSpecsDTO.setTipoSpecifica(prenotazioneSpecs.getTipoPrenotazione());
        prenotazioneSpecsDTO.setCosto(prenotazioneSpecs.getCosto());
        prenotazioneSpecsDTO.setModalitaPrenotazione(prenotazioneSpecs.getModalitaPrenotazione());
	}
}
