package it.univaq.esc.EntityDTOMappers;

import static org.mockito.Mockito.reset;

import org.apache.tomcat.util.IntrospectionUtils.SecurePropertySource;
import org.springframework.context.annotation.DependsOn;

import it.univaq.esc.utility.BeanUtil;

@DependsOn("beanUtil")
public abstract class MapperFactory {
	
	
	public PrenotazioneMapper getPrenotazioneMapper() {
		PrenotazioneMapper prenotazioneMapper = BeanUtil.getBean(PrenotazioneMapper.class);
		prenotazioneMapper.setMapperFactory(this);
		
		return prenotazioneMapper;
	}

	
	public UtenteMapper getUtenteMapper() {
		UtenteMapper utenteMapper = BeanUtil.getBean(UtenteMapper.class);
		utenteMapper.setMapperFactory(this);
		
		return utenteMapper;
	}

	
	public AppuntamentoMapper getAppuntamentoMapper() {
		AppuntamentoMapper appuntamentoMapper = BeanUtil.getBean(AppuntamentoMapper.class);
		appuntamentoMapper.setMapperFactory(this);
		
		return appuntamentoMapper;
	}

	
	public QuotaPartecipazioneMapper getQuotaPartecipazioneMapper() {
		QuotaPartecipazioneMapper quotaPartecipazioneMapper = BeanUtil.getBean(QuotaPartecipazioneMapper.class);
		quotaPartecipazioneMapper.setMapperFactory(this);
		
		return quotaPartecipazioneMapper;
	}

	
	public SportMapper getSportMapper() {
		SportMapper sportMapper = BeanUtil.getBean(SportMapper.class);
		sportMapper.setMapperFactory(this);
		
		return sportMapper;
	}
	
	public abstract PrenotazioneSpecsMapper getPrenotazioneSpecsMapper(String tipoPrenotazione);
	
	public SquadraMapper getSquadraMapper() {
		SquadraMapper squadraMapper = BeanUtil.getBean(SquadraMapper.class);
		squadraMapper.setMapperFactory(this);
		
		return squadraMapper;		
	}
	
	public ImpiantoMapper getImpiantoMapper() {
		ImpiantoMapper impiantoMapper = BeanUtil.getBean(ImpiantoMapper.class);
		impiantoMapper.setMapperFactory(this);
		
		return impiantoMapper;
	}
	
	public NotificaMapper getNotificaMapper() {
		NotificaMapper notificaMapper = BeanUtil.getBean(NotificaMapper.class);
		notificaMapper.setMapperFactory(this);
		
		return notificaMapper;
	}

}

