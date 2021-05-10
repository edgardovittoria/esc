package it.univaq.esc.factory;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.PrenotazioneImpiantoSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneLezioneSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;
import it.univaq.esc.model.PrenotazioneImpiantoSpecs;
import it.univaq.esc.model.PrenotazioneLezioneSpecs;
import it.univaq.esc.model.PrenotazioneSpecs;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCosto;



@Component
public class FactorySpecifichePrenotazione {

    public FactorySpecifichePrenotazione() {
    }

    public static PrenotazioneSpecs getSpecifichePrenotazione(String tipoPrenotazione){
        switch(tipoPrenotazione){
            default :
                return null;

            case "IMPIANTO" :  
                return new PrenotazioneImpiantoSpecs();

            case "LEZIONE" : 
                return new PrenotazioneLezioneSpecs();

        }
    }

    public static PrenotazioneSpecsDTO getSpecifichePrenotazioneDTO(String tipoPrenotazione){
        switch(tipoPrenotazione){
            default :
                return null;

            case "IMPIANTO" :  
                return new PrenotazioneImpiantoSpecsDTO();

            case "LEZIONE" : 
                return new PrenotazioneLezioneSpecsDTO();

        }
    }

    
    
}
