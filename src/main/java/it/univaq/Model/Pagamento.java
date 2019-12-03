package it.univaq.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.javamoney.moneta.Money;

@Entity
@Table(name = "pagamenti")
public abstract class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDPagamento;
	@Column
	private Money importo;
	@Column
	private Date dataPagamento;
	@OneToOne
	private QuotaPartecipazione quotaPartecipazione;
	
	public boolean effettuaPagamento(QuotaPartecipazione quotaPartecipazione, Sconto sconto) {
		return false;
	}
}
