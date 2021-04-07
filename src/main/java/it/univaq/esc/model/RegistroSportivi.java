package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.repository.SportivoRepository;


@Component
@Singleton
public class RegistroSportivi {

    @Autowired
    private SportivoRepository sportivoRepository;
    
    //private static RegistroSportivi instance = null;
    private List<Sportivo> registroSportivi = new ArrayList<Sportivo>();


    public RegistroSportivi() {}

    @PostConstruct
    public void popola(){
        this.getListaSportivi().addAll(sportivoRepository.findAll());
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