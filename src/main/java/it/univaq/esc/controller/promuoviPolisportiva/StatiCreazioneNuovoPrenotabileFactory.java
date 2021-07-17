package it.univaq.esc.controller.promuoviPolisportiva;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.utility.BeanUtil;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
@DependsOn("beanUtil")
public class StatiCreazioneNuovoPrenotabileFactory {

	
	public CreazioneNuovoPrenotabileState getStatoCreazioneNuovoPrenotabileInBaseAl(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		case "CORSO":
			 return BeanUtil.getBean(CreazionePrenotabileDescrizioneCorsoState.class);
		default:
			return BeanUtil.getBean(CreazionePrenotabileDescrizioneCorsoState.class);
		}
	}
}
