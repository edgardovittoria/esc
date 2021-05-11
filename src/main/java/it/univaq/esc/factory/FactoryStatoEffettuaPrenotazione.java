package it.univaq.esc.factory;


import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.controller.EffettuaPrenotazioneImpiantoState;
import it.univaq.esc.controller.EffettuaPrenotazioneLezioneState;
import it.univaq.esc.controller.EffettuaPrenotazioneState;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;


@Component
@Singleton
public class FactoryStatoEffettuaPrenotazione {
    private Map<String, EffettuaPrenotazioneState> mappaStati = new HashMap<String, EffettuaPrenotazioneState>();
    
    
    @Autowired
    public FactoryStatoEffettuaPrenotazione(EffettuaPrenotazioneImpiantoState statoImpianto, EffettuaPrenotazioneLezioneState statoLezione){
        getMappaStati().put(TipiPrenotazione.IMPIANTO.toString(), statoImpianto);
        getMappaStati().put(TipiPrenotazione.LEZIONE.toString(), statoLezione);
    }


    private Map<String, EffettuaPrenotazioneState> getMappaStati(){
        return this.mappaStati;
    }
    

    public EffettuaPrenotazioneState getStato(String tipoPrenotazione){

        switch (tipoPrenotazione) {
            case "IMPIANTO":
                return getMappaStati().get(tipoPrenotazione);
                
            case "LEZIONE":
                return getMappaStati().get(tipoPrenotazione);
                
            default:
                return null;
        }
    }
}
