package it.univaq.esc.controller.notifiche;

import groovy.lang.Singleton;
import it.univaq.esc.utility.BeanUtil;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
@Singleton
@NoArgsConstructor
public class FactoryDettagliNotificaStrategy {

	
	
	    private static final Map<String, Class<? extends DettagliNotificaStrategy>> mappaStrategy = new HashMap<>();
	    
	    
	    
	    public static void registra(String tipoEventoNotificabile, Class<? extends DettagliNotificaStrategy> strategy) {
	        if (tipoEventoNotificabile != null && strategy != null) {
	            mappaStrategy.put(tipoEventoNotificabile, strategy);
	        }
	    }

	    public DettagliNotificaStrategy getStrategy(String tipoEventoNotificabile){

	        if(mappaStrategy.containsKey(tipoEventoNotificabile)) {
	        	return BeanUtil.getBean(mappaStrategy.get(tipoEventoNotificabile));
	        }
	        return null;
	    }
	}

