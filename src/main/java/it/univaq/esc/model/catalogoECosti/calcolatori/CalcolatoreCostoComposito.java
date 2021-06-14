package it.univaq.esc.model.catalogoECosti.calcolatori;

import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.prenotazioni.Appuntamento;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class CalcolatoreCostoComposito extends CalcolatoreCosto{
    
   
	@Getter(value = AccessLevel.PRIVATE)
    private List<CalcolatoreCosto> strategieCosto = new ArrayList<CalcolatoreCosto>();


    @Override
    public void aggiungiStrategiaCosto(CalcolatoreCosto calcolatoreCosto){
        this.getStrategieCosto().add(calcolatoreCosto);
    }
    @Override
    public float calcolaCosto(Appuntamento appuntamento) {
        float costo = 2000;
        for(CalcolatoreCosto strategia : this.getStrategieCosto()){
            if(strategia.calcolaCosto(appuntamento)<costo){
                costo = strategia.calcolaCosto(appuntamento);
            }
        }
        return costo;
    }

    

    
}
