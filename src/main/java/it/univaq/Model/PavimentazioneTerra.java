package it.univaq.Model;

import org.javamoney.moneta.Money;

public class PavimentazioneTerra implements IPavimentazione{

	private Money CostoPavimentazione;
	private String Descrizione;
	
	
	
	public PavimentazioneTerra(Money costoPavimentazione, String descrizione) {
		super();
		this.CostoPavimentazione = costoPavimentazione;
		this.Descrizione = descrizione;
	}

	@Override
	public Money getCostoPavimentazione() {
		return this.CostoPavimentazione;
	}

	@Override
	public String getDescrizione() {
		return this.Descrizione;
	}

}
