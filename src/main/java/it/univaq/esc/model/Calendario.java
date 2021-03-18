package it.univaq.esc.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "calendario")
public class Calendario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCalendario;
    @OneToMany()
    @JoinColumn()
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
        List<Appuntamento> lista = new ArrayList<Appuntamento>();
        lista.add(appuntamentoDaAggiungere);
        Calendario calendarioDaVerificare = new Calendario(lista);
        if(!this.sovrapponeA(calendarioDaVerificare)){
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
        
        for(Appuntamento appuntamento : this.getListaAppuntamenti()){
            if(!calendariSiSovrappongono){
            for(Appuntamento appuntamentoCalendarioDaVerificare : calendarioDiCuiVerificareSovrapposizione.getListaAppuntamenti()){
                if(!calendariSiSovrappongono){
                    calendariSiSovrappongono = appuntamento.sovrapponeA(appuntamentoCalendarioDaVerificare);
                }
            }
        }
        
    }

        return calendariSiSovrappongono;
    }

    public void unisciCalendario(Calendario calendarioDaUnire){
        if(!this.sovrapponeA(calendarioDaUnire)){
            this.getListaAppuntamenti().addAll(calendarioDaUnire.getListaAppuntamenti());
        }
    }

}