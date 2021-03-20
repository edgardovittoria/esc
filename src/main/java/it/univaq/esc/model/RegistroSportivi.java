package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;



public class RegistroSportivi {
    
    private static RegistroSportivi instance = null;
    private List<Sportivo> registroSportivi = new ArrayList<Sportivo>();


    private RegistroSportivi() {}

    public static RegistroSportivi getInstance() {
        if(instance == null) {
            instance = new RegistroSportivi();
        }
        return instance;
    }

    public List<Sportivo> getListaSportivi() {
        return this.registroSportivi;
    }

    public void registraSportivo(Sportivo sportivoDaRegistrare) {
        getListaSportivi().add(sportivoDaRegistrare);
    }

    public Sportivo getSportivoDaEmail(String emailSportivo){
        for(Sportivo sportivo : getListaSportivi()){
            if(sportivo.getEmail().equals(emailSportivo)){
                return sportivo;
            }
        }
        return null;
    }
}