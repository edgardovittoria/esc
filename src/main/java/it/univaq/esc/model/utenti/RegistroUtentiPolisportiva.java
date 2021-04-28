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
        this.getListaSportivi().addAll(this.utentiRepository.findAll());
        if(!this.getListaSportivi().isEmpty()){
        List<Integer> listaindiciDaEliminare = new ArrayList<Integer>();
        for(UtentePolisportivaAbstract utente : this.getListaSportivi().subList(0, this.getListaSportivi().size() - 1)){
            int j=0;
            for(UtentePolisportivaAbstract utenteSuccessivo : this.getListaSportivi().subList(this.getListaSportivi().indexOf(utente)+1, this.getListaSportivi().size())){
                if(utente.getProprieta().get("email").equals(utenteSuccessivo.getProprieta().get("email"))){
                    j++;
                }
            }
            if(j>0){
                listaindiciDaEliminare.add(this.getListaSportivi().indexOf(utente));
            }

        }
        List<UtentePolisportivaAbstract> utentiDaEliminare = new ArrayList<UtentePolisportivaAbstract>();
        for(Integer index : listaindiciDaEliminare){
            utentiDaEliminare.add(this.getListaSportivi().get(index));
        }
        this.getListaSportivi().removeAll(utentiDaEliminare);
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