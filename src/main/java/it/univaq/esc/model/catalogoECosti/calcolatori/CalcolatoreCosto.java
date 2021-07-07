package it.univaq.esc.model.catalogoECosti.calcolatori;

import org.springframework.stereotype.Component;

import it.univaq.esc.model.Costo;
import it.univaq.esc.model.catalogoECosti.TipoCostoPrenotabile;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import lombok.NoArgsConstructor;


@Component
@NoArgsConstructor
public abstract class CalcolatoreCosto {
    

    public abstract Costo calcolaCosto(Appuntamento appuntamento);

    public Costo calcolaQuotaPartecipazione(Appuntamento appuntamento){
    	if(appuntamento.getMappaCostiAppuntamento().containsKey(TipoCostoPrenotabile.COSTO_UNA_TANTUM.toString())) {
    		return this.calcolaCosto(appuntamento);
    	}
        Costo costo = this.calcolaCosto(appuntamento);
        costo.dividiPer(appuntamento.getUtentiPartecipanti().size());
        return costo;
    };

    public void aggiungiStrategiaCosto(CalcolatoreCosto calcolatoreCosto){
        // Di default non fa nulla
    }
}
