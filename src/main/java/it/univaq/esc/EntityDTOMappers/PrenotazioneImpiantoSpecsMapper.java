package it.univaq.esc.EntityDTOMappers;

import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.PrenotazioneImpiantoSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class PrenotazioneImpiantoSpecsMapper extends PrenotazioneSpecsMapper{

	

	@Override
	public PrenotazioneSpecsDTO convertiInPrenotazioneSpecsDTO(PrenotazioneSpecs prenotazioneSpecs) {
		PrenotazioneImpiantoSpecsDTO prenotazioneImpiantoSpecsDTO = new PrenotazioneImpiantoSpecsDTO();
    	
        // I posti liberi sono da eliminare perché si possono calcolare in automatico
       prenotazioneImpiantoSpecsDTO.setPostiLiberi(12);
        
        for(UtentePolisportivaAbstract invitato : (List<UtentePolisportivaAbstract>) prenotazioneSpecs.getValoriSpecificheExtraPrenotazione().get("invitati")){
            
            prenotazioneImpiantoSpecsDTO.getInvitati().add((String)invitato.getProprieta().get("email"));
        }
        
        prenotazioneImpiantoSpecsDTO.setIdImpiantoPrenotato(((Impianto)prenotazioneSpecs.getValoriSpecificheExtraPrenotazione().get("impianto")).getIdImpianto());
        prenotazioneImpiantoSpecsDTO.setPavimentazioneImpianto(((Impianto)prenotazioneSpecs.getValoriSpecificheExtraPrenotazione().get("impianto")).getTipoPavimentazione().toString());
        impostaDatiGeneraliPrenotazioneSpecs(prenotazioneSpecs, prenotazioneImpiantoSpecsDTO);
        
        return prenotazioneImpiantoSpecsDTO;
	}

}
