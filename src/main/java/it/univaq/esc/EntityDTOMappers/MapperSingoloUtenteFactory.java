package it.univaq.esc.EntityDTOMappers;


import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.utility.BeanUtil;
import lombok.NoArgsConstructor;


@Component(value = "MAPPER_SINGOLO_UTENTE")
@Singleton
@NoArgsConstructor
public class MapperSingoloUtenteFactory extends MapperFactory{@Override
	
	public PrenotazioneSpecsMapper getPrenotazioneSpecsMapper(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		case "IMPIANTO":
			PrenotazioneImpiantoSpecsMapper prenotazioneImpiantoSpecsMapper = BeanUtil.getBean(PrenotazioneImpiantoSpecsMapper.class);
			prenotazioneImpiantoSpecsMapper.setMapperFactory(this);
			return prenotazioneImpiantoSpecsMapper;
		case "LEZIONE":
			PrenotazioneLezioneSpecsMapper prenotazioneLezioneSpecsMapper = BeanUtil.getBean(PrenotazioneLezioneSpecsMapper.class);
			prenotazioneLezioneSpecsMapper.setMapperFactory(this);
			return prenotazioneLezioneSpecsMapper;
		case "CORSO":
			return null;

		default:
			return null;
		}
	}

@Override
public NotificaMapper getNotificaMapper() {
	NotificaMapper mapper = (NotificaMapper) BeanUtil.getBean("MAPPER_NOTIFICA_SINGOLO_UTENTE", NotificaMapper.class);
	mapper.setMapperFactory(this);
	return mapper;
}

	

}
