package it.univaq.Model;

import java.util.ArrayList;

import org.javamoney.moneta.Money;

public abstract class Impianto implements IPrenotabile{

	protected int IDImpianto;
	protected boolean indoor;
	protected Money costoImpianto;
	protected Calendario calendario;
	protected IPavimentazione pavimentazione;
	protected ArrayList<Sport> sport;
	
	
	public Money getCostoImpianto() {
		return costoImpianto;
	}


	public void setCostoImpianto(Money costoImpianto) {
		this.costoImpianto = costoImpianto;
	}


	public Calendario getCalendario() {
		return calendario;
	}


	public void setCalendario(Calendario calendario) {
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
