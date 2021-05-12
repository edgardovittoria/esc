package it.univaq.esc.model.costi.calcolatori;

import org.springframework.stereotype.Component;

import it.univaq.esc.model.Appuntamento;


@Component
public abstract class CalcolatoreCosto {
    
    


    public CalcolatoreCosto(){}

    

    public abstract float calcolaCosto(Appuntamento appuntamento);

    public float calcolaQuotaPartecipazione(Appuntamento appuntamento){
        return this.calcolaCosto(appuntamento) / appuntamento.getNumeroPartecipantiTotali();
    };

    public void aggiungiStrategiaCosto(CalcolatoreCosto calcolatoreCosto){
        // Di default non fa nulla
    }
}
