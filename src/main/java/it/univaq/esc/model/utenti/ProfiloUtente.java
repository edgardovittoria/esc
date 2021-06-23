package it.univaq.esc.model.utenti;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ProfiloUtente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	public abstract boolean isProfilo(TipoRuolo ruolo);
	
	public abstract TipoRuolo getRuoloRelativo(); 
}
