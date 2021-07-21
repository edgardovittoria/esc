package it.univaq.esc.model.catalogoECosti.calcolatori;

import it.univaq.esc.model.Costo;
import it.univaq.esc.model.Valuta;
import it.univaq.esc.model.Valute;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
public class CalcolatoreCostoComposito extends CalcolatoreCosto{
    
   
	@Getter(value = AccessLevel.PRIVATE)
    private List<CalcolatoreCosto> strategieCosto = new ArrayList<CalcolatoreCosto>();


    @Override
    public void aggiungiStrategiaCosto(CalcolatoreCosto calcolatoreCosto){
        this.getStrategieCosto().add(calcolatoreCosto);
    }
    
    /**
     * Applica il costo minore tra quelli applicabili all'appuntamento.
     */
    @Override
    public Costo calcolaCosto(Appuntamento appuntamento) {
    	Costo costo = getStrategieCosto().get(0).calcolaCosto(appuntamento);
        for(CalcolatoreCosto strategia : this.getStrategieCosto()){
            if(strategia.calcolaCosto(appuntamento).isMinoreDi(costo)){
                costo = strategia.calcolaCosto(appuntamento);
            }
        }
        return costo;
    }

    

    
}
