package it.univaq.esc.model.costi.calcolatori;

import org.springframework.stereotype.Component;

import it.univaq.esc.model.Appuntamento;
import lombok.NoArgsConstructor;


@Component
@NoArgsConstructor
public abstract class CalcolatoreCosto {
    

    public abstract float calcolaCosto(Appuntamento appuntamento);

    public float calcolaQuotaPartecipazione(Appuntamento appuntamento){
        return this.calcolaCosto(appuntamento) / appuntamento.getNumeroPartecipantiMassimo();
    };

    public void aggiungiStrategiaCosto(CalcolatoreCosto calcolatoreCosto){
        // Di default non fa nulla
    }
}
