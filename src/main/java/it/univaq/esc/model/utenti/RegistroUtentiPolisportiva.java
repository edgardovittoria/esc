package it.univaq.esc.model.utenti;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.repository.UtentePolisportivaAbstractRepository;


@Component
@Singleton
public class RegistroUtentiPolisportiva {

    @Autowired
    private UtentePolisportivaAbstractRepository utentiRepository;
    
    //private static RegistroSportivi instance = null;
    private List<UtentePolisportivaAbstract> registroSportivi = new ArrayList<UtentePolisportivaAbstract>();


    public RegistroUtentiPolisportiva() {}

    @PostConstruct
    public void popola(){
        outer: for(UtentePolisportivaAbstract utente : utentiRepository.findAll()){
            for(UtentePolisportivaAbstract utenteP : this.getListaSportivi()){
                if(utenteP.getProprieta().get("email").equals(utente.getProprieta().get("email"))){
                    continue outer;
                }
            }
            this.getListaSportivi().add(utente);

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