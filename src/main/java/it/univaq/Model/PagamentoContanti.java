package it.univaq.Model;

public class PagamentoContanti extends Pagamento{

	@Override
	public boolean effettuaPagamento(QuotaPartecipazione quotaPartecipazione, Sconto sconto) {
		return false;
	}
}
