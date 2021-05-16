package it.univaq.esc.dtoObjects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice.This;

@Getter @Setter @NoArgsConstructor
public class OrarioAppuntamento {
    

    private Integer id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataPrenotazione;

    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime oraInizio;

    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime oraFine;


    /**
     * @return Date return the dataPrenotazione
     */
    public String getDataPrenotazioneToString() {
        return dataPrenotazione.toString();
    }


    /**
     * @param dataPrenotazione the dataPrenotazione to set
     */
    public void setDataPrenotazioneFromString(String dataPrenotazione) {
        this.dataPrenotazione = LocalDate.parse(dataPrenotazione, DateTimeFormatter.ofPattern ( "yyyy-MM-dd'T'HH:mm:ss.SSSX" ));
    }
    

    /**
     * @param oraInizio the oraInizio to set
     */
    public void setOraInizioFromString(String oraInizio) {
        this.oraInizio = LocalTime.parse(oraInizio, DateTimeFormatter.ofPattern ( "yyyy-MM-dd'T'HH:mm:ss.SSSX" ));
    }


    /**
     * @param oraFine the oraFine to set
     */
    public void setOraFineFromString(String oraFine) {
        this.oraFine = LocalTime.parse(oraFine, DateTimeFormatter.ofPattern ( "yyyy-MM-dd'T'HH:mm:ss.SSSX" ));
    }

   
}
