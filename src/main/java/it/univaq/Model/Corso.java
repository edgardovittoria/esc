package it.univaq.Model;

import java.util.ArrayList;

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
	private ArrayList<Lezione> lezioni;
	@OneToMany(mappedBy = "IDIstruttore")
	private ArrayList<Istruttore> istruttori;
	
	
	
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



	public ArrayList<Lezione> getLezioni() {
		return lezioni;
	}



	public void setLezioni(ArrayList<Lezione> lezioni) {
		this.lezioni = lezioni;
	}



	public ArrayList<Istruttore> getIstruttori() {
		return istruttori;
	}



	public void setIstruttori(ArrayList<Istruttore> istruttori) {
		this.istruttori = istruttori;
	}



	@Override
	public boolean confermaPrenotazione(ArrayList<Object> parametri) {
		// TODO Auto-generated method stub
		return false;
	}

}
