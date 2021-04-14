package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.repository.SportivoRepository;
import it.univaq.esc.repository.UtentePolisportivaAbstractRepository;


@Component
@Singleton
public class RegistroSportivi {

    @Autowired
    private UtentePolisportivaAbstractRepository utentiRepository;
    
    //private static RegistroSportivi instance = null;
    private List<UtentePolisportivaAbstract> registroSportivi = new ArrayList<UtentePolisportivaAbstract>();


    public RegistroSportivi() {}

    @PostConstruct
    public void popola(){
        for(UtentePolisportivaAbstract utente : utentiRepository.findAll()){
            if(utente.getRuoliUtentePolisportiva().contains(TipoRuolo.SPORTIVO.toString())){
                this.getListaSportivi().add(utente);
            }
        }
    }


    public List<UtentePolisportivaAbstract> getListaSportivi() {
        return this.registroSportivi;
    }

    public void registraSportivo(Sportivo sportivoDaRegistrare) {
        getListaSportivi().add(sportivoDaRegistrare);
    }

    public UtentePolisportivaAbstract getSportivoDaEmail(String emailSportivo){
        for(UtentePolisportivaAbstract sportivo : getListaSportivi()){
            if(((String)sportivo.getProprieta().get("email")).equals(emailSportivo)){
                return sportivo;
            }
        }
        return null;
    }
}