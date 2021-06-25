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
	public NotificaMapper getNotificaMapper() {
		NotificaMapper mapper = BeanUtil.getBean("MAPPER_NOTIFICA_SQUADRA", NotificaMapper.class);
		impostaAttributiMapper(mapper);
		return mapper;
	}
	
	
	@Override
	public AppuntamentoMapper getAppuntamentoMapper(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		case "IMPIANTO":
			AppuntamentoImpiantoSquadraMapper appuntamentoImpiantoSquadraMapper = BeanUtil.getBean(AppuntamentoImpiantoSquadraMapper.class);
			impostaAttributiMapper(appuntamentoImpiantoSquadraMapper);
			return appuntamentoImpiantoSquadraMapper;
		case "LEZIONE":
			return null;
		case "CORSO":
			return null;
		default:
			return null;
		}
	}

}
