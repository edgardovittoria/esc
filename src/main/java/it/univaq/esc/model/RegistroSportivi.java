package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.univaq.esc.repository.SportivoRepository;


@Component
public class RegistroSportivi {

    @Autowired
    private SportivoRepository sportivoRepository;
    
    //private static RegistroSportivi instance = null;
    private List<Sportivo> registroSportivi = new ArrayList<Sportivo>();


    public RegistroSportivi() {}

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public RegistroSportivi registroSportiviSingleton(){
        return new RegistroSportivi();
    }

    @PostConstruct
    public void popola(){
        this.getListaSportivi().addAll(sportivoRepository.findAll());
    }

    /*public static RegistroSportivi getInstance() {
        if(instance == null) {
            instance = new RegistroSportivi();
        }
        return instance;
    }*/

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