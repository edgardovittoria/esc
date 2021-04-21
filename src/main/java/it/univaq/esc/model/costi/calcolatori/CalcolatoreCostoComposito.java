package it.univaq.esc.model.costi.calcolatori;

import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.Appuntamento;



public class CalcolatoreCostoComposito extends CalcolatoreCosto{
    
   
    private List<CalcolatoreCosto> strategieCosto = new ArrayList<CalcolatoreCosto>();

  
    public CalcolatoreCostoComposito() {}

    private List<CalcolatoreCosto> getListaStrategieCosto(){
        return this.strategieCosto;
    }

    @Override
    public void aggiungiStrategiaCosto(CalcolatoreCosto calcolatoreCosto){
        this.getListaStrategieCosto().add(calcolatoreCosto);
    }
    @Override
    public float calcolaCosto(Appuntamento appuntamento) {
        float costo = 2000;
        for(CalcolatoreCosto strategia : this.getListaStrategieCosto()){
            if(strategia.calcolaCosto(appuntamento)<costo){
                costo = strategia.calcolaCosto(appuntamento);
            }
        }
        return costo;
    }

    
}
