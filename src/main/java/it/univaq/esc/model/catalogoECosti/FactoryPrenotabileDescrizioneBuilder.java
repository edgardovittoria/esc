package it.univaq.esc.model.catalogoECosti;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.utility.BeanUtil;

@Component
@Singleton
public class FactoryPrenotabileDescrizioneBuilder {
	
	public PrenotabileDescrizioneBuilder creaPrenotabileDescrizioneBuilderInBaseAl(String tipoPrenotazione) {
    	switch (tipoPrenotazione) {
		case "PACCHETTO_LEZIONI":
			return BeanUtil.getBean("PRENOTABILE_DESCRIZIONE_CON_NUMERO_DATE_BUILDER", PrenotabileDescrizioneBuilder.class);
		default:
			return BeanUtil.getBean("PRENOTABILE_DESCRIZIONE_BUILDER", PrenotabileDescrizioneBuilder.class);
		}
    }
}
