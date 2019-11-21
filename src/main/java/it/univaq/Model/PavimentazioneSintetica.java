package it.univaq.Model;

import org.javamoney.moneta.Money;

public class PavimentazioneSintetica implements IPavimentazione{

	private Money CostoPavimentazione;
	private String Descrizione;
	
	
	
	public PavimentazioneSintetica(Money costoPavimentazione, String descrizione) {
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
