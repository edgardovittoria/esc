package it.univaq.esc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.javamoney.moneta.Money;

@Entity
@Table(name = "costoPrenotazione")
public class CostoPrenotazione {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDCostoPrenotazione;
	@Column
	private Money costoTotale;
	@Column
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
