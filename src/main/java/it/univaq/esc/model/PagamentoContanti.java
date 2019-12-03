package it.univaq.esc.model;

public class PagamentoContanti extends Pagamento{

	@Override
	public boolean effettuaPagamento(QuotaPartecipazione quotaPartecipazione, Sconto sconto) {
		return false;
	}
}
