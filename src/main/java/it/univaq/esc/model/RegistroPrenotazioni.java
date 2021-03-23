package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.univaq.esc.repository.PrenotazioneRepository;


@Component
public class RegistroPrenotazioni {

    @Autowired PrenotazioneRepository prenotazioneRepository;

    private List<Prenotazione> prenotazioniEffettuate = new ArrayList<Prenotazione>();
    //private static RegistroPrenotazioni instance = null;

    public RegistroPrenotazioni(){}

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public RegistroPrenotazioni registroPrenotazioniSingleton(){
        return new RegistroPrenotazioni();
    }

    @PostConstruct
    public void popola(){
        getTutteLePrenotazioni().addAll(prenotazioneRepository.findAll());
    }

    public void aggiungiPrenotazione(Prenotazione prenotazioneDaAggiungere) {
        getTutteLePrenotazioni().add(prenotazioneDaAggiungere);
    }

    public List<Prenotazione> getTutteLePrenotazioni(){
        return this.prenotazioniEffettuate;
    }

    public void cancellaPrenotazione(Prenotazione prenotazioneDaCancellare){
        getTutteLePrenotazioni().remove(prenotazioneDaCancellare);
    }

    public void cancellaListaPrenotazioni(List<Prenotazione> listaPrenotazioniDaCancellare){
        getTutteLePrenotazioni().removeAll(listaPrenotazioniDaCancellare);
    }

    public int getLastIdPrenotazione() {
        if(getTutteLePrenotazioni().isEmpty()){
            return 0;
        }
        else{
        Prenotazione lastPrenotazione = getTutteLePrenotazioni().get(getTutteLePrenotazioni().size()-1);
        return lastPrenotazione.getIdPrenotazione();}
    }

    /*public static RegistroPrenotazioni getInstance(){
        if(instance == null){
            instance = new RegistroPrenotazioni();
            
            
        }
        return instance;

    }*/

    

}