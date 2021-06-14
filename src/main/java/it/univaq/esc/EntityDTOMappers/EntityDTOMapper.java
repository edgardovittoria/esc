package it.univaq.esc.EntityDTOMappers;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter(value = AccessLevel.PROTECTED) @Setter(value = AccessLevel.PROTECTED)
public abstract class EntityDTOMapper {

	private MapperFactory mapperFactory;
}
