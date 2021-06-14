package it.univaq.esc.EntityDTOMappers;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.PrenotazioneLezioneSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class PrenotazioneLezioneSpecsMapper extends PrenotazioneSpecsMapper{

	

	@Override
	public PrenotazioneSpecsDTO convertiInPrenotazioneSpecsDTO(PrenotazioneSpecs prenotazioneSpecs) {
		PrenotazioneLezioneSpecsDTO prenotazioneLezioneSpecsDTO = new PrenotazioneLezioneSpecsDTO();
		
        prenotazioneLezioneSpecsDTO.setIstruttore((String)((UtentePolisportivaAbstract)prenotazioneSpecs.getValoriSpecificheExtraPrenotazione().get("istruttore")).getProprieta().get("email"));
        prenotazioneLezioneSpecsDTO.setIdImpiantoPrenotato(((Impianto)prenotazioneSpecs.getValoriSpecificheExtraPrenotazione().get("impianto")).getIdImpianto());
        prenotazioneLezioneSpecsDTO.setPavimentazioneImpianto(((Impianto)prenotazioneSpecs.getValoriSpecificheExtraPrenotazione().get("impianto")).getTipoPavimentazione().toString());
        impostaDatiGeneraliPrenotazioneSpecs(prenotazioneSpecs, prenotazioneLezioneSpecsDTO);
        
        return prenotazioneLezioneSpecsDTO;
	}

}
