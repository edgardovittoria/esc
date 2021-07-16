package it.univaq.esc.model.utenti;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ProfiloUtente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	public abstract boolean isProfilo(TipoRuolo ruolo);
	
	public abstract TipoRuolo getRuoloRelativo(); 
}
