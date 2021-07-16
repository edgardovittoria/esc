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
    @Override
    public Costo calcolaCosto(Appuntamento appuntamento) {
    	Valuta valuta = new Valuta(Valute.EUR);
        Costo costo = new Costo(Float.parseFloat("2000"), valuta);
        for(CalcolatoreCosto strategia : this.getStrategieCosto()){
            if(strategia.calcolaCosto(appuntamento).isMinoreDi(costo)){
                costo = strategia.calcolaCosto(appuntamento);
            }
        }
        return costo;
    }

    

    
}
