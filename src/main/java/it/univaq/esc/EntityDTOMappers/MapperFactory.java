package it.univaq.esc.EntityDTOMappers;

import org.springframework.context.annotation.DependsOn;

import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.utility.BeanUtil;

@DependsOn("beanUtil")
public abstract class MapperFactory {
	
	
	public PrenotazioneMapper getPrenotazioneMapper() {
		PrenotazioneMapper prenotazioneMapper = BeanUtil.getBean(PrenotazioneMapper.class);
		impostaAttributiMapper(prenotazioneMapper);
		
		return prenotazioneMapper;
	}

	
	public UtenteMapper getUtenteMapper() {
		UtenteMapper utenteMapper = BeanUtil.getBean(UtenteMapper.class);
		impostaAttributiMapper(utenteMapper);
		
		return utenteMapper;
	}

	
	public abstract AppuntamentoMapper getAppuntamentoMapper(String tipoPrenotazione);

	
	public QuotaPartecipazioneMapper getQuotaPartecipazioneMapper() {
		QuotaPartecipazioneMapper quotaPartecipazioneMapper = BeanUtil.getBean(QuotaPartecipazioneMapper.class);
		impostaAttributiMapper(quotaPartecipazioneMapper);
		
		return quotaPartecipazioneMapper;
	}

	
	public SportMapper getSportMapper() {
		SportMapper sportMapper = BeanUtil.getBean(SportMapper.class);
		impostaAttributiMapper(sportMapper);
		
		return sportMapper;
	}
	
	
	
	public SquadraMapper getSquadraMapper() {
		SquadraMapper squadraMapper = BeanUtil.getBean(SquadraMapper.class);
		impostaAttributiMapper(squadraMapper);
		
		return squadraMapper;		
	}
	
	public StrutturaPolisportivaMapper getStrutturaPolisportivaMapper(String tipoStruttura) {
		switch (tipoStruttura) {
		case "IMPIANTO":
			ImpiantoMapper impiantoMapper = BeanUtil.getBean(ImpiantoMapper.class);
			impostaAttributiMapper(impiantoMapper);
			return impiantoMapper;
		default:
			return null;
		}
		
	}
	
	
	
	public abstract NotificaMapper getNotificaMapper() ;
	
	protected void impostaAttributiMapper(EntityDTOMapper mapper) {
		mapper.setMapperFactory(this);
		mapper.setRegistroUtentiPolisportiva(BeanUtil.getBean(RegistroUtentiPolisportiva.class));
		mapper.setRegistroImpianti(BeanUtil.getBean(RegistroImpianti.class));
		mapper.setCatalogoPrenotabili(BeanUtil.getBean(CatalogoPrenotabili.class));
		mapper.setRegistroSport(BeanUtil.getBean(RegistroSport.class));
		mapper.setRegistroSquadre(BeanUtil.getBean(RegistroSquadre.class));
	}

}

