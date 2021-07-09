package it.univaq.esc.EntityDTOMappers;


import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter(value = AccessLevel.PROTECTED) @Setter(value = AccessLevel.PROTECTED)
public abstract class EntityDTOMapper {

	private MapperFactory mapperFactory;
	private RegistroUtentiPolisportiva registroUtentiPolisportiva;
	private RegistroImpianti registroImpianti;
	private RegistroSport registroSport;
	private CatalogoPrenotabili catalogoPrenotabili;
	private RegistroSquadre registroSquadre;
	
	protected void impostaMapperFactory(String modalitaPrenotazione) {
		setMapperFactory(BeanUtil.getBean("MAPPER_" + modalitaPrenotazione, MapperFactory.class));
	}
}
