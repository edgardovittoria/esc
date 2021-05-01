package it.univaq.esc.dtoObjects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    private Integer postiLiberi;

    private Integer numeroGiocatoriNonIscritti;

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
        mappaValori.put("postiLiberi", this.getPostiLiberi());
        mappaValori.put("numeroPartecipantiNonIscritti", this.getNumeroGiocatoriNonIscritti());

        return mappaValori;
    }


    /**
     * @return Integer return the postiLiberi
     */
    public Integer getPostiLiberi() {
        return postiLiberi;
    }

    /**
     * @param postiLiberi the postiLiberi to set
     */
    public void setPostiLiberi(Integer postiLiberi) {
        this.postiLiberi = postiLiberi;
    }


    /**
     * @return Integer return the numeroGiocatoriNonIscritti
     */
    public Integer getNumeroGiocatoriNonIscritti() {
        return numeroGiocatoriNonIscritti;
    }

    /**
     * @param numeroGiocatoriNonIscritti the numeroGiocatoriNonIscritti to set
     */
    public void setNumeroGiocatoriNonIscritti(Integer numeroGiocatoriNonIscritti) {
        this.numeroGiocatoriNonIscritti = numeroGiocatoriNonIscritti;
    }

}