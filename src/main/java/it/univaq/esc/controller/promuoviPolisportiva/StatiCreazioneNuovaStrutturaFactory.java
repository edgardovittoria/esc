package it.univaq.esc.controller.promuoviPolisportiva;

import groovy.lang.Singleton;
import it.univaq.esc.utility.BeanUtil;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@Singleton
@NoArgsConstructor
@DependsOn("beanUtil")
public class StatiCreazioneNuovaStrutturaFactory {

	public CreazioneNuovaStrutturaState getStatoCreazioneNuovaStrutturaInBaseAl(String tipoNuovaStruttura) {
		switch (tipoNuovaStruttura) {
		case "IMPIANTO":
			 return BeanUtil.getBean(CreazioneNuovoImpiantoState.class);
		default:
			return BeanUtil.getBean(CreazioneNuovoImpiantoState.class);
		}
	}
}
