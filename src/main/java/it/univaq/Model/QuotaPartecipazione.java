package it.univaq.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.javamoney.moneta.Money;

@Entity
@Table(name = "quotePartecipazione")
public class QuotaPartecipazione {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDQuotaPartecipazione;
	@OneToOne
	private Prenotazione prenotazione;
	@Column
	private boolean saldata;
	@Column
	private Money importo;
	
	
	public QuotaPartecipazione(Prenotazione prenotazione, Money importo) {
		super();
		this.prenotazione = prenotazione;
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


	public Prenotazione getPrenotazione() {
		return this.prenotazione;
	}


	public void setPrenotazione(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
	}
	
	
	
	
}
