package it.univaq.Model;

import org.javamoney.moneta.Money;

public class QuotaPartecipazione {

	private int IDQuotaPartecipazione;
	private int IDPrenotazione;
	private boolean saldata;
	private Money importo;
	
	
	public QuotaPartecipazione(int iDPrenotazione, Money importo) {
		super();
		this.IDPrenotazione = iDPrenotazione;
		this.importo = importo;
	}


	public boolean isSaldata() {
		return saldata;
	}


	public void setSaldata(boolean saldata) {
		this.saldata = saldata;
	}


	public Money getImporto() {
		return importo;
	}


	public void setImporto(Money importo) {
		this.importo = importo;
	}


	public int getIDPrenotazione() {
		return IDPrenotazione;
	}


	public void setIDPrenotazione(int iDPrenotazione) {
		IDPrenotazione = iDPrenotazione;
	}
	
	
	
	
}
