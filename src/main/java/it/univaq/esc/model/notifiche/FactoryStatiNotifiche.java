package it.univaq.esc.model.notifiche;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.utility.BeanUtil;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class FactoryStatiNotifiche {
    private static final Map<String, NotificaState> mappaStati = new HashMap<>();
    
    static {
    	mappaStati.put(TipiPrenotazione.IMPIANTO.toString(), new NotificaImpiantoState());
    	mappaStati.put(TipiPrenotazione.CORSO.toString(), new NotificaCorsoState());
    }

    
    public NotificaState getStato(String tipoPrenotazione){
        if(mappaStati.containsKey(tipoPrenotazione)) {
        	return mappaStati.get(tipoPrenotazione);
        }
        
        return null;
    }
}
