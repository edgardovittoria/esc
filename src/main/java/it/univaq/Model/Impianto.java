package it.univaq.Model;

import java.util.ArrayList;

import org.javamoney.moneta.Money;

public class Impianto implements IPrenotabile{

	private int IDImpianto;
	private boolean indoor;
	private Money costoImpianto;
	private Calendario calendario;
	private IPavimentazione pavimentazione;
	private ArrayList<Sport> sport;
	
	//costruttore
	public Impianto(IPavimentazione pavimentazione) {
		super();
		this.pavimentazione = pavimentazione;
	}
	
	
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
