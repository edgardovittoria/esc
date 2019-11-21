package it.univaq.Model;

import java.util.ArrayList;

import org.javamoney.moneta.Money;

public class Lezione implements IPrenotabile{

	private int IDLezione;
	private Money costoLezione;
	private Impianto impianto;
	private Istruttore istruttore;
	private Calendario calendario;
	
	
	
	public Lezione() {
		super();
	}

	

	public Money getCostoLezione() {
		return costoLezione;
	}



	public void setCostoLezione(Money costoLezione) {
		this.costoLezione = costoLezione;
	}



	public Impianto getImpianto() {
		return impianto;
	}



	public void setImpianto(Impianto impianto) {
		this.impianto = impianto;
	}



	public Istruttore getIstruttore() {
		return istruttore;
	}



	public void setIstruttore(Istruttore istruttore) {
		this.istruttore = istruttore;
	}



	public Calendario getCalendario() {
		return calendario;
	}



	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}



	@Override
	public boolean confermaPrenotazione(ArrayList<Object> parametri) {
		// TODO Auto-generated method stub
		return false;
	}

}
