package it.univaq.esc.dtoObjects;


import java.util.HashMap;
import java.util.List;


import org.springframework.stereotype.Component;

@Component
public class FormPrenotaImpianto implements IFormPrenotabile {

    private String sportSelezionato = "tennis";

    private OrarioAppuntamento orarioPrenotazione;

   private ImpiantoSelezionato impianto;
    private List<String> sportiviInvitati;

    private Integer postiLiberi;

    private Integer numeroGiocatoriNonIscritti;

    public FormPrenotaImpianto() {
    }

    public ImpiantoSelezionato getImpianto() {
        return impianto;
    }

    public void setImpianto(ImpiantoSelezionato impianto) {
        this.impianto = impianto;
    }

    public OrarioAppuntamento getOrarioPrenotazione() {
        return orarioPrenotazione;
    }

    public void setOrarioPrenotazione(OrarioAppuntamento orarioPrenotazione) {
        this.orarioPrenotazione = orarioPrenotazione;
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
        mappaValori.put("dataPrenotazione", this.getOrarioPrenotazione().getDataPrenotazione());
        mappaValori.put("oraInizio", this.getOrarioPrenotazione().getOraInizio());
        mappaValori.put("oraFine", this.getOrarioPrenotazione().getOraFine());
        mappaValori.put("impianto", this.getImpianto().getIdImpianto());
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