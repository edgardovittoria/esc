package it.univaq.esc.factory;

import org.springframework.stereotype.Component;

import it.univaq.esc.model.IPrenotabile;
import it.univaq.esc.model.PrenotazioneImpiantoSpecs;


@Component
public class FactorySpecifichePrenotazione {

    public FactorySpecifichePrenotazione() {
    }

    public IPrenotabile getSpecifichePrenotazione(String tipoPrenotazione){
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
