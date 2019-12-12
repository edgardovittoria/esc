package it.univaq.esc.model;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.repository.SportRepository;

@Component
public class RegistroSport {
    
    private List<Sport> sport;
	private static RegistroSport RegistroSportInstance;

	@Autowired
	private SportRepository sp;


	private RegistroSport(){
		
	}

	public static RegistroSport getInstance() {
		if(RegistroSportInstance == null){
			RegistroSportInstance = new RegistroSport();
		}
		return RegistroSportInstance;
		
	}

	@PostConstruct
	private void inizializzaSports(){
		sport = sp.findAll();
	}

	
	public List<Sport> getAllSport(){
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

	public Sport getSport(String description) {
        for(Sport i : sport){
            if(i.getSportDescription().equals(description)){
				return i;
			}
		}
		return null;
	}

    
    public Sport getSportDes(@Autowired String etichetta) {    	
       for(Sport i : sport){
            if(i.getSportDescription().equals(etichetta) ){
				return i;
			}
		}
		return null;
	}
	
	public void addSport(Sport sport) {
		this.sport.add(sport);
		
	}
}