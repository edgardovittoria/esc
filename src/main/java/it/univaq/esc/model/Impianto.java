package it.univaq.esc.model;

import java.util.List;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.javamoney.moneta.Money;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "nome")
@Table(name = "impianti")
public abstract class Impianto implements IPrenotabile{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int IDImpianto;
	@Column
	protected boolean indoor;
	@Column
	protected Money costoImpianto;
	@Column
	protected Calendar calendario;
	@Transient
	protected IPavimentazione pavimentazione;
	
	
	
	public Money getCostoImpianto() {
		return costoImpianto;
	}


	public void setCostoImpianto(Money costoImpianto) {
		this.costoImpianto = costoImpianto;
	}


	public Calendar getCalendario() {
		return calendario;
	}


	public void setCalendario(Calendar calendario) {
		this.calendario = calendario;
	}


	@Override
	public boolean confermaPrenotazione(List<Object> parametri) {
		return false;
	}

	

}
