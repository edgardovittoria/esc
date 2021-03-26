package it.univaq.esc.factory;

import org.springframework.stereotype.Component;

import it.univaq.esc.model.PrenotazioneImpiantoSpecs;
import it.univaq.esc.model.PrenotazioneSpecs;

@Component
public class FactorySpecifichePrenotazione {

    public FactorySpecifichePrenotazione() {
    }

    public PrenotazioneSpecs getSpecifichePrenotazione(String tipoPrenotazione){
        switch(tipoPrenotazione){
            default :
                return null;

            case "IMPIANTO" :  
                return new PrenotazioneImpiantoSpecs();

            case "LEZIONE" : 
                return null;

        }
    }

    
    
}
