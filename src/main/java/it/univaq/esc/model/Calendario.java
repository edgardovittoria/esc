package it.univaq.esc.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Component;




@Component
public class Calendario {
    
    
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
        System.out.println("DATA_INIZIO : "+dataOraInizioAppuntamento);
        
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

    public boolean sovrapponeA(LocalDateTime oraInizio, LocalDateTime oraFine){
        boolean calendarioSiSovrappone = false;
        
        for(Appuntamento appuntamento : this.getListaAppuntamenti()){
            if(!calendarioSiSovrappone){
                calendarioSiSovrappone = appuntamento.sovrapponeA(oraInizio, oraFine);
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