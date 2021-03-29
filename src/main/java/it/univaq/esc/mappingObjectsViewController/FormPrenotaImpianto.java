package it.univaq.esc.mappingObjectsViewController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class FormPrenotaImpianto implements IFormPrenotabile {

    private String sport;
    private LocalDate dataPrenotazione;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private Integer impianto;
    private List<String> sportiviInvitati;

    public FormPrenotaImpianto() {
    }

    /**
     * @return String return the sport
     */
    public String getSport() {
        return sport;
    }

    /**
     * @param sport the sport to set
     */
    public void setSport(String sport) {
        this.sport = sport;
    }

    /**
     * @return Date return the dataPrenotazione
     */
    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }

    /**
     * @param dataPrenotazione the dataPrenotazione to set
     */
    public void setDataPrenotazione(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
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
    public void setOraInizio(LocalTime oraInizio) {
        this.oraInizio = oraInizio;
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
    public void setOraFine(LocalTime oraFine) {
        this.oraFine = oraFine;
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
    public void setImpianto(Integer impianto) {
        this.impianto = impianto;
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
        mappaValori.put("sport", this.getSport());
        mappaValori.put("dataPrenotazione", this.getDataPrenotazione());
        mappaValori.put("oraInizio", this.getOraInizio());
        mappaValori.put("oraFine", this.getOraFine());
        mappaValori.put("impianto", this.getImpianto());
        mappaValori.put("invitati", this.getSportiviInvitati());

        return mappaValori;
    }

}