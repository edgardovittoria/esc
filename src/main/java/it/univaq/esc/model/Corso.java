package it.univaq.esc.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.javamoney.moneta.Money;

@Entity
@Table(name = "corsi")
public class Corso implements IPrenotabile{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDCorso;
	@Column
	private int numMaxPartecipanti;
	@Column
	private int numMinPartecipanti;
	@Column
	private Money costoCorso;
	@OneToMany(mappedBy = "IDLezione")
	private List<Lezione> lezioni;
	@OneToMany(mappedBy = "IDIstruttore")
	private List<Istruttore> istruttori;
	
	
	
	public Corso() {
		super();
	}

	

	public int getNumMaxPartecipanti() {
		return numMaxPartecipanti;
	}



	public void setNumMaxPartecipanti(int numMaxPartecipanti) {
		this.numMaxPartecipanti = numMaxPartecipanti;
	}



	public int getNumMinPartecipanti() {
		return numMinPartecipanti;
	}



	public void setNumMinPartecipanti(int numMinPartecipanti) {
		this.numMinPartecipanti = numMinPartecipanti;
	}



	public Money getCostoCorso() {
		return costoCorso;
	}



	public void setCostoCorso(Money costoCorso) {
		this.costoCorso = costoCorso;
	}



	public List<Lezione> getLezioni() {
		return lezioni;
	}



	public void setLezioni(List<Lezione> lezioni) {
		this.lezioni = lezioni;
	}



	public List<Istruttore> getIstruttori() {
		return istruttori;
	}



	public void setIstruttori(List<Istruttore> istruttori) {
		this.istruttori = istruttori;
	}



	@Override
	public boolean confermaPrenotazione(List<Object> parametri) {
		// TODO Auto-generated method stub
		return false;
	}

}
