package it.univaq.Model;

public class PagamentoCartaCredito extends Pagamento{

	@Override
	public boolean effettuaPagamento(QuotaPartecipazione quotaPartecipazione, Sconto sconto) {
		return false;
	}
}
