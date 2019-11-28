package it.univaq.Model;

public class RegistroSport {
    
    private ArrayList<Sport> sport;
	private static RegistroSport RegistroSportInstance;

	private RegistroSportivi(){
		this.sport = new ArrayList<Sport>();
	}

	public static RegistroSportivi getInstance() {
		if(RegistroSportInstance == null){
			RegistroSportInstance = new RegistroSportivi();
		}
		return RegistroSportInstance;
		
	}
	
	public Sportivo getAllSport(){
		return this.sport;
    }
    
    public Sport getSport(int IDSport) {
        for(Sport i : sport){
            if(i.getIDSport = IDSport){
				return i;
			}
        }
    }
}