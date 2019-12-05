package it.univaq.esc.model;

import java.util.ArrayList;

public class RegistroSportivi {

	private ArrayList<Sportivo> sportivi;
	private static RegistroSportivi RegistroSportiviInstance;

	private RegistroSportivi(){
		this.sportivi = new ArrayList<Sportivo>();
	}

	public static RegistroSportivi getInstance() {
		if(RegistroSportiviInstance == null){
			RegistroSportiviInstance = new RegistroSportivi();
		}
		return RegistroSportiviInstance;
		
	}
	
	public Sportivo getSportivo(int IDSportivo){
		for(Sportivo sp : this.sportivi){
			if(sp.getIDSportivo() == IDSportivo){
				return sp;
			}
		}
		return null;
	}

	public void addSportivo(Sportivo sportivo) {
		sportivi.add(sportivo);
		
	}



	
}
