package it.univaq.Model;

import java.util.ArrayList;

public class RegistroSconti {

	private ArrayList<Sconto> sconti;

	public RegistroSconti() {
		super();
	}

	public ArrayList<Sconto> getSconti() {
		return sconti;
	}
    
	public Sconto getSconto(int IDSconto) {
		return this.sconti.get(IDSconto);
	}
	 
	public void setSconti(ArrayList<Sconto> sconti) {
		this.sconti = sconti;
	}
	
	
}
