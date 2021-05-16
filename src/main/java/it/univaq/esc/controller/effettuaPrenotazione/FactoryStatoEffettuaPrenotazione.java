package it.univaq.esc.controller.effettuaPrenotazione;


import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import groovy.lang.Singleton;
import it.univaq.esc.utility.BeanUtil;
import lombok.NoArgsConstructor;

/**
 * Factory per la creazione degli Stati del controller EffettuaPrenotazioneHandlerRest.
 * In base al tipo di prenotazione pass
 * @author esc
 *
 */
@Component
@Singleton
@NoArgsConstructor
public class FactoryStatoEffettuaPrenotazione {
    private static final Map<String, Class<? extends EffettuaPrenotazioneState>> mappaStati = new HashMap<>();
    
    
    
    public static void registra(String tipoPrenotazione, Class<? extends EffettuaPrenotazioneState> stato) {
        if (tipoPrenotazione != null && stato != null) {
            mappaStati.put(tipoPrenotazione, stato);
        }
    }

    public EffettuaPrenotazioneState getStato(String tipoPrenotazione){

        if(mappaStati.containsKey(tipoPrenotazione)) {
        	return BeanUtil.getBean(mappaStati.get(tipoPrenotazione));
        }
        return null;
    }
}
