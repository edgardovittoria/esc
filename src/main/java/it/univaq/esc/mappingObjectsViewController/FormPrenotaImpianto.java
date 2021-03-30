package it.univaq.esc.mappingObjectsViewController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
public class FormPrenotaImpianto implements IFormPrenotabile {

    private String sportSelezionato = "tennis";

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataPrenotazione = LocalDate.now();

    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime oraInizio;

    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime oraFine;

    private Integer impianto;
    private List<String> sportiviInvitati;

    public FormPrenotaImpianto() {
    }

    /**
     * @return String return the sport
     */
    public String getSportSelezionato() {
        return this.sportSelezionato;
    }

    /**
     * @param sport the sport to set
     */
    public void setSportSelezionato(String sport) {
        this.sportSelezionato = sport;
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
        this.dataPrenotazione = LocalDate.parse(dataPrenotazione);
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
        this.oraInizio = LocalTime.parse(oraInizio);
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
        this.oraFine = LocalTime.parse(oraFine);
    }

    /**
     * @return Integer return the impianto
     */
    public Integer getImpianto() {
        return impianto;
    }

    /**
     * @param impianto the impianto to set
     */
    public void setImpianto(String impianto) {
        this.impianto = Integer.parseInt(impianto);
    }

    /**
     * @return List<String> return the sportiviInvitati
     */
    public List<String> getSportiviInvitati() {
        return sportiviInvitati;
    }

    /**
     * @param sportiviInvitati the sportiviInvitati to set
     */
    public void setSportiviInvitati(List<String> sportiviInvitati) {
        this.sportiviInvitati = sportiviInvitati;
    }

    @Override
    public HashMap<String, Object> getValoriForm() {
        
        HashMap<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("sport", this.getSportSelezionato());
        mappaValori.put("dataPrenotazione", this.getDataPrenotazione());
        mappaValori.put("oraInizio", this.getOraInizio());
        mappaValori.put("oraFine", this.getOraFine());
        mappaValori.put("impianto", this.getImpianto());
        mappaValori.put("invitati", this.getSportiviInvitati());

        return mappaValori;
    }

}