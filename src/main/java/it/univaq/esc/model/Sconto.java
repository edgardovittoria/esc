package it.univaq.esc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sconti")
public class Sconto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDSconto;
	@Column
	private boolean usato;
	@Transient
	private IStrategyPoliticaSconto politicaSconto;
	
	public Sconto() {
		super();
	}

	public boolean isUsato() {
		return usato;
	}

	public void setUsato(boolean usato) {
		this.usato = usato;
	}

	public void setPoliticaSconto(IStrategyPoliticaSconto politicaSconto) {
		this.politicaSconto = politicaSconto;
	}
	
	
	
	
	
	
}
