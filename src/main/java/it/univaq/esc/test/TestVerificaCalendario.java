package it.univaq.esc.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import it.univaq.esc.model.RegistroPrenotazioni;

@Component
public class TestVerificaCalendario {
    
    @Autowired
    private RegistroPrenotazioni regPrenotazioni;

    public TestVerificaCalendario(){};

    public void test(){
        System.out.println("-----------------------DATI SULLE PRENOTAZIONI--------------------");
        // for(Prenotazione prenotazione : regPrenotazioni.getTutteLePrenotazioni()){
        //     System.out.println("Prenotazione ID: " + prenotazione.getIdPrenotazione());
        //     System.out.println("Numero appuntamenti: " + prenotazione.getCalendarioPrenotazione().getListaAppuntamenti().size());
        //     System.out.println("-----------lista appuntamenti------------");
        //     for(Appuntamento appuntamento : prenotazione.getCalendarioPrenotazione().getListaAppuntamenti()){
        //         System.out.println("Appuntamento ID: " + appuntamento.getIdAppuntamento());
        //         System.out.println("Impianto prenotato ID: " + appuntamento.getIdImpiantoPrenotato());
        //         System.out.println("Data  e ora inizio: " + appuntamento.getDataOraInizioAppuntamento().toString());
        //         System.out.println("Data  e ora fine: " + appuntamento.getDataOraFineAppuntamento().toString());
        //         System.out.println("Istanza PrenotazioneSpecs relativa: " + appuntamento.getPrenotazioneSpecsAppuntamento().toString());
        //         System.out.println("");
        //     }
        //     }
        }
    }
