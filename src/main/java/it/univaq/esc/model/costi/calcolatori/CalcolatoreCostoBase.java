package it.univaq.esc.model.costi.calcolatori;

import java.time.LocalTime;
import java.util.Map;



import static java.time.temporal.ChronoUnit.MINUTES;

import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.PrenotazioneSpecs;


public class CalcolatoreCostoBase extends CalcolatoreCosto{

   public CalcolatoreCostoBase(){}
        
    

    @Override
    public float calcolaCosto(Appuntamento appuntamento) {
        PrenotazioneSpecs prenotazioneSpecs = appuntamento.getPrenotazioneSpecsAppuntamento();
        LocalTime oraInizio = appuntamento.getOraInizioAppuntamento();
        LocalTime oraFine = appuntamento.getOraFineAppuntamento();
        Long minutiDurataAppuntamento = MINUTES.between(oraFine, oraInizio);
        Impianto impiantoAppuntamento = (Impianto)prenotazioneSpecs.getValoriSpecificheExtraPrenotazione().get("impianto");
        String pavimentazione = impiantoAppuntamento.getTipoPavimentazione().toString();
        Map<String, Float> mappaCosti = prenotazioneSpecs.getSpecificaDescription().getMappaCosti();
        Float costoOrario = mappaCosti.get("costoOrario");
        Float costoPavimentazione = mappaCosti.get(pavimentazione);

        Float costo = ((costoOrario + (costoOrario*costoPavimentazione/100))/60)*minutiDurataAppuntamento;

        return costo;
    }



    
    
}
