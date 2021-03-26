package it.univaq.esc.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;



// @Entity
// @Table(name = "calendario")
@Component
public class Calendario {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private int idPrenotazione;
    // @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn
    // @LazyCollection(LazyCollectionOption.FALSE)
    private List<Appuntamento> listaAppuntamenti = new ArrayList<Appuntamento>();

    /**
     * Crea calendario appuntamenti vuoto
     */
    public Calendario(){}


    /**
     * Crea calendario segnando gli appuntamenti passati come parametro
     * @param listaAppuntamentiDaSegnareSulCalendario lista di appuntamenti da segnare sul calendario
     */
    public Calendario(List<Appuntamento> listaAppuntamentiDaSegnareSulCalendario){
        this.setListaAppuntamenti(listaAppuntamentiDaSegnareSulCalendario);
    }

    /**
     * @return List<LocalDateTime> return the listaDate
     */
    public List<Appuntamento> getListaAppuntamenti() {
        return this.listaAppuntamenti;
    }

    /**
     * @param listaDate the listaDate to set
     */
    public void setListaAppuntamenti(List<Appuntamento> listaDate) {
        this.listaAppuntamenti = listaDate;
    }


    public void aggiungiAppuntamento(Appuntamento appuntamentoDaAggiungere){
        if(!this.sovrapponeA(appuntamentoDaAggiungere)){
        this.getListaAppuntamenti().add(appuntamentoDaAggiungere);
    }
    }

    public void aggiungiAppuntamento(LocalDateTime dataOraInizioAppuntamento, LocalDateTime dataOraFineAppuntamento, Prenotazione prenotazioneRelativaAppuntamento, Impianto impiantoRelativoAppuntamento){
        Appuntamento appuntamentoDaAggiungere = new Appuntamento();
        appuntamentoDaAggiungere.setDataOraInizioAppuntamento(dataOraInizioAppuntamento);
        appuntamentoDaAggiungere.setDataOraFineAppuntamento(dataOraFineAppuntamento);
        appuntamentoDaAggiungere.setImpiantoAppuntamento(impiantoRelativoAppuntamento);
        appuntamentoDaAggiungere.setPrenotazioneAppuntamento(prenotazioneRelativaAppuntamento);
        if(!this.sovrapponeA(appuntamentoDaAggiungere)){
            this.getListaAppuntamenti().add(appuntamentoDaAggiungere);
        }
        
    }


    /**
     * Verifica se due calendari si sovrappongono almeno in un appuntamento
     * @param calendarioDiCuiVerificareSovrapposizione calendario di cui verificare la sovrapposizione
     * @return true se i calendari si sovrappongono almeno su un appuntamento, false altrimenti.
     */
    public boolean sovrapponeA(Calendario calendarioDiCuiVerificareSovrapposizione){
        boolean calendariSiSovrappongono = false;
        
        for(Appuntamento appuntamento : calendarioDiCuiVerificareSovrapposizione.getListaAppuntamenti()){
            if(!calendariSiSovrappongono){
                calendariSiSovrappongono = this.sovrapponeA(appuntamento);
            }
        }
        return calendariSiSovrappongono;
    }


    /**
     * Verifica se un appuntamento si sovrappone con quelli del calendario in oggetto
     * @param appuntamentoDiCuiVerificareSovrapposizione appuntamento di cui verificare la sovrapposizione
     * @return true se il calendario si sovrappone almeno su un appuntamento con quello da verificare, false altrimenti.
     */
    private boolean sovrapponeA(Appuntamento appuntamentoDiCuiVerificareSovrapposizione){
        boolean calendarioSiSovrappone = false;
        
        for(Appuntamento appuntamento : this.getListaAppuntamenti()){
            if(!calendarioSiSovrappone){
                calendarioSiSovrappone = appuntamento.sovrapponeA(appuntamentoDiCuiVerificareSovrapposizione);
            }
        }
        return calendarioSiSovrappone;
    }


    public void unisciCalendario(Calendario calendarioDaUnire){
        if(!this.sovrapponeA(calendarioDaUnire)){
            this.getListaAppuntamenti().addAll(calendarioDaUnire.getListaAppuntamenti());
        }
    }

}