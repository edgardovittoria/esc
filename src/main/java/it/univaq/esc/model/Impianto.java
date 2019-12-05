package it.univaq.esc.model;

import java.util.List;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.javamoney.moneta.Money;

@Entity
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
	@ManyToMany
	protected List<Sport> sport;
	
	
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
	
	public List<Sport> getSport(){
		return this.sport;
	}
	
	public void addSport(Sport sport) {
		this.sport.add(sport);
	}


	@Override
	public boolean confermaPrenotazione(List<Object> parametri) {
		return false;
	}

	

}
