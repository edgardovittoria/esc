package it.univaq.Model;

import java.util.ArrayList;

public class RegistroSportivi {

	private ArrayList<Sportivo> sportivi;
	private RegistroSportivi RegistroSportiviInstance;

	private RegistroSportivi(){
		this.sportivi = new ArrayList<Sportivo>();
	}

	public static ResistroSportivi getInstance() {
		if(this.RegistroSportiviInstance == null){
			this.RegistroSportiviInstance = new RegistroSportivi()
		}
		return this.RegistroSportiviInstance;
		
	}
	
	public Sportivo getSportivo(int IDSportivo){
		
	}

	
}
