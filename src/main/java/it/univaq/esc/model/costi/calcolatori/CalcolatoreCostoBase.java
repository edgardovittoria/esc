package it.univaq.esc.model.costi.calcolatori;

import java.time.LocalTime;
import java.util.Map;



import static java.time.temporal.ChronoUnit.MINUTES;

import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import lombok.NoArgsConstructor;
import it.univaq.esc.model.costi.TipoCostoPrenotabile;

@NoArgsConstructor
public class CalcolatoreCostoBase extends CalcolatoreCosto{        
    

    @Override
    public float calcolaCosto(Appuntamento appuntamento) {
        PrenotazioneSpecs prenotazioneSpecs = appuntamento.getPrenotazioneSpecsAppuntamento();
        LocalTime oraInizio = appuntamento.getOraInizioAppuntamento();
        LocalTime oraFine = appuntamento.getOraFineAppuntamento();
        Long minutiDurataAppuntamento = MINUTES.between(oraInizio, oraFine);
        Impianto impiantoAppuntamento = (Impianto)prenotazioneSpecs.getValoriSpecificheExtraPrenotazione().get("impianto");
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
