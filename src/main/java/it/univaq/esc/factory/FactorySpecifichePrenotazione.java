package it.univaq.esc.factory;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.IPrenotabileDTO;
import it.univaq.esc.dtoObjects.PrenotazioneImpiantoSpecsDTO;
import it.univaq.esc.model.IPrenotabile;
import it.univaq.esc.model.PrenotazioneImpiantoSpecs;
import it.univaq.esc.model.PrenotazioneSpecs;
import it.univaq.esc.model.TipiPrenotazione;


@Component
public class FactorySpecifichePrenotazione {

    public FactorySpecifichePrenotazione() {
    }

    public IPrenotabile getSpecifichePrenotazione(String tipoPrenotazione){
        switch(tipoPrenotazione){
            default :
                return null;

            case "IMPIANTO" :  
                return new PrenotazioneImpiantoSpecs(new PrenotazioneSpecs());

            case "LEZIONE" : 
                return null;

        }
    }

    public IPrenotabileDTO getSpecifichePrenotazioneDTO(String tipoPrenotazione){
        switch(tipoPrenotazione){
            default :
                return null;

            case "IMPIANTO" :  
                return new PrenotazioneImpiantoSpecsDTO();

            case "LEZIONE" : 
                return null;

        }
    }

    
    
}
