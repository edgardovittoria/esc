package it.univaq.Model;

import org.javamoney.moneta.Money;

public class CostoPrenotazione {

	private Money costoTotale;
	private Money costoPerPartecipante;
	//costruttore
	public CostoPrenotazione(Money costoTotale, Money costoPerPartecipante) {
		super();
		this.costoTotale = costoTotale;
		this.costoPerPartecipante = costoPerPartecipante;
	}
	public Money getCostoTotale() {
		return costoTotale;
	}
	public void setCostoTotale(Money costoTotale) {
		this.costoTotale = costoTotale;
	}
	public Money getCostoPerPartecipante() {
		return costoPerPartecipante;
	}
	public void setCostoPerPartecipante(Money costoPerPartecipante) {
		this.costoPerPartecipante = costoPerPartecipante;
	}
	
	
	

}
