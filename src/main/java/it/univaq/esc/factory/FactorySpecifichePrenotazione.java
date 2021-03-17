package it.univaq.esc.factory;

import it.univaq.esc.model.PrenotazioneImpiantoSpecs;
import it.univaq.esc.model.PrenotazioneSpecs;
import it.univaq.esc.model.TipiPrenotazione;

public class FactorySpecifichePrenotazione {

    public FactorySpecifichePrenotazione() {
    }

    public PrenotazioneSpecs getSpecifichePrenotazione(TipiPrenotazione tipoPrenotazione){
        switch(tipoPrenotazione){
            default :
                return null;

            case IMPIANTO :  
                return new PrenotazioneImpiantoSpecs();

            case LEZIONE : 
                return null;

        }
    }

    
    
}
