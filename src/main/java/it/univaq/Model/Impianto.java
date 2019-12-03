package it.univaq.Model;

import java.util.ArrayList;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	@OneToOne
	protected Calendar calendario;
	@OneToOne
	protected IPavimentazione pavimentazione;
	@OneToOne(mappedBy = "IDSport")
	protected ArrayList<Sport> sport;
	
	
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
	
	public ArrayList<Sport> getSport(){
		return this.sport;
	}


	@Override
	public boolean confermaPrenotazione(ArrayList<Object> parametri) {
		return false;
	}

	

}
