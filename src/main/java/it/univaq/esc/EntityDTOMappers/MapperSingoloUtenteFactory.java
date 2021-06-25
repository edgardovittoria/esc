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
		impostaAttributiMapper(mapper);
		return mapper;
	}

	@Override
	public AppuntamentoMapper getAppuntamentoMapper(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		case "IMPIANTO":
			AppuntamentoImpiantoMapper appuntamentoImpiantoMapper = BeanUtil.getBean(AppuntamentoImpiantoMapper.class);
			impostaAttributiMapper(appuntamentoImpiantoMapper);
			return appuntamentoImpiantoMapper;
		case "LEZIONE":
			AppuntamentoLezioneMapper appuntamentoLezioneMapper = BeanUtil.getBean(AppuntamentoLezioneMapper.class);
			impostaAttributiMapper(appuntamentoLezioneMapper);
			return appuntamentoLezioneMapper;
		case "CORSO":
			AppuntamentoCorsoMapper appuntamentoCorsoMapper = BeanUtil.getBean(AppuntamentoCorsoMapper.class);
			impostaAttributiMapper(appuntamentoCorsoMapper);
			return appuntamentoCorsoMapper;
		default:
			return null;
		}
	}

}
