package it.univaq.esc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.javamoney.moneta.Money;

@Entity
@Table(name = "quotePartecipazione")
public class QuotaPartecipazione {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDQuotaPartecipazione;
	// @ManyToOne
	// private Prenotazione prenotazione;
	// @ManyToOne
	// private Sportivo sportivoAssociato;
	@Column
	private boolean saldata;
	@Column
	private Money importo;

	@Transient
	private int idPrenotazione;
	
	
	public QuotaPartecipazione(int idPrenotazione, Money importo) {
		this.idPrenotazione = idPrenotazione;
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


	// public Prenotazione getPrenotazione() {
	// 	return this.prenotazione;
	// }


	// public void setPrenotazione(Prenotazione prenotazione) {
	// 	this.prenotazione = prenotazione;
	// }
	
	
	
	

    /**
     * @return int return the IDQuotaPartecipazione
     */
    public int getIDQuotaPartecipazione() {
        return IDQuotaPartecipazione;
    }

    /**
     * @param IDQuotaPartecipazione the IDQuotaPartecipazione to set
     */
    public void setIDQuotaPartecipazione(int IDQuotaPartecipazione) {
        this.IDQuotaPartecipazione = IDQuotaPartecipazione;
    }

    /**
     * @return Sportivo return the sportivoAssociato
     */
    // public Sportivo getSportivoAssociato() {
    //     return sportivoAssociato;
    // }

    /**
     * @param sportivoAssociato the sportivoAssociato to set
     */
    // public void setSportivoAssociato(Sportivo sportivoAssociato) {
    //     this.sportivoAssociato = sportivoAssociato;
    // }

}
