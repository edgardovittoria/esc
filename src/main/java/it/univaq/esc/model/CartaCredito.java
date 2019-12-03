package it.univaq.esc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cartaCredito")
public class CartaCredito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDCartaCredito;

	public CartaCredito(int iDCartaCredito) {
		super();
	}
	
	
	
	
}
