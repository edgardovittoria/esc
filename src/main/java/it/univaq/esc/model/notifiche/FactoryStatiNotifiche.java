package it.univaq.esc.model.notifiche;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.utility.BeanUtil;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class FactoryStatiNotifiche {
    private static final Map<String, Class<? extends NotificaState>> mappaStati = new HashMap<>();
    
    
    
    public static void registra(String tipoPrenotazione, Class<? extends NotificaState> stato) {
        if (tipoPrenotazione != null && stato != null) {
            mappaStati.put(tipoPrenotazione, stato);
        }
    }

    public static NotificaState getStato(String tipoPrenotazione){

        if(mappaStati.containsKey(tipoPrenotazione)) {
        	return BeanUtil.getBean(mappaStati.get(tipoPrenotazione));
        }
        return null;
    }
}
