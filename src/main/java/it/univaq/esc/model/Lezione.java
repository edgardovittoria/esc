package it.univaq.esc.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.javamoney.moneta.Money;

@Entity
@Table(name = "lezioni")
public class Lezione implements IPrenotabile{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDLezione;
	@Column
	private Money costoLezione;
	@ManyToOne
	private Impianto impianto;
	@ManyToOne
	private Istruttore istruttore;
	@Column
	private Calendar calendario;
	
	@ManyToOne
	private Corso corsoAssociato;
	
	
	
	public Lezione() {
		super();
	}

	

	public Money getCostoLezione() {
		return costoLezione;
	}



	public void setCostoLezione(Money costoLezione) {
		this.costoLezione = costoLezione;
	}



	public Impianto getImpianto() {
		return impianto;
	}



	public void setImpianto(Impianto impianto) {
		this.impianto = impianto;
	}



	public Istruttore getIstruttore() {
		return istruttore;
	}



	public void setIstruttore(Istruttore istruttore) {
		this.istruttore = istruttore;
	}



	public Calendar getCalendario() {
		return calendario;
	}



	public void setCalendario(Calendar calendario) {
		this.calendario = calendario;
	}



	@Override
	public boolean confermaPrenotazione(List<Object> parametri) {
		
		return false;
	}

}
