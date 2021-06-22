package it.univaq.esc.model.catalogoECosti.calcolatori;

import java.time.LocalTime;
import java.util.Map;



import static java.time.temporal.ChronoUnit.MINUTES;

import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.catalogoECosti.TipoCostoPrenotabile;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CalcolatoreCostoBase extends CalcolatoreCosto{        
    

    @Override
    public float calcolaCosto(Appuntamento appuntamento) {
        LocalTime oraInizio = appuntamento.getOraInizioAppuntamento();
        LocalTime oraFine = appuntamento.getOraFineAppuntamento();
        Long minutiDurataAppuntamento = MINUTES.between(oraInizio, oraFine);
        Impianto impiantoAppuntamento = appuntamento.getImpiantoPrenotato();
        String pavimentazione = impiantoAppuntamento.getTipoPavimentazione().toString();
        Map<String, Float> mappaCosti = appuntamento.getMappaCostiAppuntamento();
        Float costo;
        if(mappaCosti.containsKey(TipoCostoPrenotabile.COSTO_ORARIO.toString())){
            Float costoOrario = mappaCosti.get(TipoCostoPrenotabile.COSTO_ORARIO.toString());
            Float costoPavimentazione = mappaCosti.get(pavimentazione);
            costo = ((costoOrario + (costoOrario*costoPavimentazione/100))/60)*minutiDurataAppuntamento;
        }
        else{
            Float costoUnaTantum = mappaCosti.get(TipoCostoPrenotabile.COSTO_UNA_TANTUM.toString());
            costo = costoUnaTantum;
        }

        return costo;
    }



   



    
    
}
