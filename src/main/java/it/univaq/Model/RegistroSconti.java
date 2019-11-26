package it.univaq.Model;

import java.util.ArrayList;

public class RegistroSconti {

	private ArrayList<Sconto> sconti;
	private static RegistroSconti registroScontiInstance;

	public RegistroSconti() {
		this.sconti = new ArrayList<Sconto>();
	}

	public static RegistroSconti getInstance(){
		if (registroScontiInstance == null){
			registroScontiInstance = new RegistroSconti();
		}

		return registroScontiInstance;
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
