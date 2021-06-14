package it.univaq.esc.EntityDTOMappers;

import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.PrenotazioneImpiantoSquadraSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.utenti.Squadra;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class PrenotazioneImpiantoSquadraSpecsMapper extends PrenotazioneSpecsMapper{

	

	@Override
	public PrenotazioneSpecsDTO convertiInPrenotazioneSpecsDTO(PrenotazioneSpecs prenotazioneSpecs) {
		PrenotazioneImpiantoSquadraSpecsDTO prenotazioneImpiantoSquadraSpecsDTO = new PrenotazioneImpiantoSquadraSpecsDTO();
    	
        // I posti liberi sono da eliminare perché si possono calcolare in automatico
        prenotazioneImpiantoSquadraSpecsDTO.setPostiLiberi(12);
        
        for(Squadra invitato : (List<Squadra>) prenotazioneSpecs.getValoriSpecificheExtraPrenotazione().get("invitati")){
            
            prenotazioneImpiantoSquadraSpecsDTO.getInvitati().add((String)invitato.getNome());
        }
        
        prenotazioneImpiantoSquadraSpecsDTO.setIdImpiantoPrenotato(((Impianto)prenotazioneSpecs.getValoriSpecificheExtraPrenotazione().get("impianto")).getIdImpianto());
        prenotazioneImpiantoSquadraSpecsDTO.setPavimentazioneImpianto(((Impianto)prenotazioneSpecs.getValoriSpecificheExtraPrenotazione().get("impianto")).getTipoPavimentazione().toString());
        impostaDatiGeneraliPrenotazioneSpecs(prenotazioneSpecs, prenotazioneImpiantoSquadraSpecsDTO);
        
        return prenotazioneImpiantoSquadraSpecsDTO;
	}

}
