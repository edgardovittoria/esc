package it.univaq.Model;

import java.util.Date;

import org.javamoney.moneta.Money;

public abstract class Pagamento {

	private int IDPagamento;
	private Money importo;
	private Date dataPagamento;
	private int IDQuotaPartecipazione;
	
	public boolean effettuaPagamento(QuotaPartecipazione quotaPartecipazione, Sconto sconto) {
		return false;
	}
}
