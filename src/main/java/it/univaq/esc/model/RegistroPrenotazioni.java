package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.repository.AppuntamentoRepository;
import it.univaq.esc.repository.PrenotazioneRepository;


@Component
@Singleton
public class RegistroPrenotazioni {

    @Autowired 
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;

    private List<Prenotazione> prenotazioniEffettuate = new ArrayList<Prenotazione>();
    //private static RegistroPrenotazioni instance = null;

    public RegistroPrenotazioni(){}


    @PostConstruct
    public void popola(){
        getTutteLePrenotazioni().addAll(prenotazioneRepository.findAll());
        for(Prenotazione prenotazione : this.getTutteLePrenotazioni()){
            prenotazione.getCalendarioPrenotazione().setListaAppuntamenti(appuntamentoRepository.findByPrenotazioneAppuntamento_IdPrenotazione(prenotazione.getIdPrenotazione()));
        }

    }

    public void aggiungiPrenotazione(Prenotazione prenotazioneDaAggiungere) {
        getTutteLePrenotazioni().add(prenotazioneDaAggiungere);
        this.prenotazioneRepository.save(prenotazioneDaAggiungere);
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