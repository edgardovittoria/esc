package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;



public class RegistroPrenotazioni {
   

    private List<Prenotazione> prenotazioniEffettuate = new ArrayList<Prenotazione>();
    private static RegistroPrenotazioni instance = null;

    private RegistroPrenotazioni(){
        
    }

    public void popola(List<Prenotazione> listaPrenotazioni){
        getTutteLePrenotazioni().addAll(listaPrenotazioni);
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

    public static RegistroPrenotazioni getInstance(){
        if(instance == null){
            instance = new RegistroPrenotazioni();
            
            
        }
        return instance;

    }

    

}