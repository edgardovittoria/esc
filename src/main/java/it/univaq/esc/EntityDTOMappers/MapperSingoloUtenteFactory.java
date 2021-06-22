package it.univaq.esc.EntityDTOMappers;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.utility.BeanUtil;
import lombok.NoArgsConstructor;

@Component(value = "MAPPER_SINGOLO_UTENTE")
@Singleton
@NoArgsConstructor
public class MapperSingoloUtenteFactory extends MapperFactory {

	@Override
	public NotificaMapper getNotificaMapper() {
		NotificaMapper mapper = (NotificaMapper) BeanUtil.getBean("MAPPER_NOTIFICA_SINGOLO_UTENTE",
				NotificaMapper.class);
		mapper.setMapperFactory(this);
		return mapper;
	}

	@Override
	public AppuntamentoMapper getAppuntamentoMapper(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		case "IMPIANTO":
			return new AppuntamentoImpiantoMapper();
		case "LEZIONE":
			return new AppuntamentoLezioneMapper();
		case "CORSO":
			return new AppuntamentoCorsoMapper();
		default:
			return null;
		}
	}

}
