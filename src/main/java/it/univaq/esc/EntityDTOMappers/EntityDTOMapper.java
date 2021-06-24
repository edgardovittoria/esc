package it.univaq.esc.EntityDTOMappers;


import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter(value = AccessLevel.PROTECTED) @Setter(value = AccessLevel.PROTECTED)
public abstract class EntityDTOMapper {

	private MapperFactory mapperFactory;
	
	protected void impostaMapperFactory(String modalitaPrenotazione) {
		setMapperFactory(BeanUtil.getBean("MAPPER_" + modalitaPrenotazione, MapperFactory.class));
	}
}
