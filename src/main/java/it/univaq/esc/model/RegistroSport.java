package it.univaq.esc.model;

import java.util.ArrayList;

public class RegistroSport {
    
    private ArrayList<Sport> sport;
	private static RegistroSport RegistroSportInstance;

	private RegistroSport(){
		this.sport = new ArrayList<Sport>();
	}

	public static RegistroSport getInstance() {
		if(RegistroSportInstance == null){
			RegistroSportInstance = new RegistroSport();
		}
		return RegistroSportInstance;
		
	}
	
	public ArrayList<Sport> getAllSport(){
		return this.sport;
    }
    
    public Sport getSport(int IDSport) {
        for(Sport i : sport){
            if(i.getIDSport() == IDSport){
				return i;
			}
		}
		return null;
    }
}