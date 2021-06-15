package it.univaq.esc.EntityDTOMappers;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.utility.BeanUtil;
import lombok.NoArgsConstructor;

@Component(value = "MAPPER_SQUADRA")
@Singleton
@NoArgsConstructor
public class MapperSquadraFactory extends MapperFactory {
	@Override
	public PrenotazioneSpecsMapper getPrenotazioneSpecsMapper(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		case "IMPIANTO":
			PrenotazioneImpiantoSquadraSpecsMapper prenotazioneImpiantoSquadraSpecsMapper = BeanUtil.getBean(PrenotazioneImpiantoSquadraSpecsMapper.class);
			prenotazioneImpiantoSquadraSpecsMapper.setMapperFactory(this);
			return prenotazioneImpiantoSquadraSpecsMapper;
		case "LEZIONE":
			return null;
		case "CORSO":
			return null;

		default:
			return null;
		}
	}

	@Override
	public NotificaMapper getNotificaMapper() {
		NotificaMapper mapper = BeanUtil.getBean("MAPPER_NOTIFICA_SQUADRA", NotificaMapper.class);
		return mapper;
	}

}
