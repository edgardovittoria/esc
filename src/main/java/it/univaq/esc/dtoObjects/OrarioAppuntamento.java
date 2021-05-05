package it.univaq.esc.dtoObjects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

public class OrarioAppuntamento {
    

    private Integer id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataPrenotazione;

    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime oraInizio;

    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime oraFine;


    public OrarioAppuntamento(){}

     public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return Date return the dataPrenotazione
     */
    public String getDataPrenotazione() {
        return dataPrenotazione.toString();
    }

    public LocalDate getLocalDataPrenotazione(){
        return dataPrenotazione;
    }

    /**
     * @param dataPrenotazione the dataPrenotazione to set
     */
    public void setDataPrenotazione(String dataPrenotazione) {
        this.dataPrenotazione = LocalDate.parse(dataPrenotazione, DateTimeFormatter.ofPattern ( "yyyy-MM-dd'T'HH:mm:ss.SSSX" ));
    }

    /**
     * @return LocalTime return the oraInizio
     */
    public LocalTime getOraInizio() {
        return oraInizio;
    }

    /**
     * @param oraInizio the oraInizio to set
     */
    public void setOraInizio(String oraInizio) {
        this.oraInizio = LocalTime.parse(oraInizio, DateTimeFormatter.ofPattern ( "yyyy-MM-dd'T'HH:mm:ss.SSSX" ));
    }

    /**
     * @return LocalTime return the oraFine
     */
    public LocalTime getOraFine() {
        return oraFine;
    }

    /**
     * @param oraFine the oraFine to set
     */
    public void setOraFine(String oraFine) {
        this.oraFine = LocalTime.parse(oraFine, DateTimeFormatter.ofPattern ( "yyyy-MM-dd'T'HH:mm:ss.SSSX" ));
    }
}
