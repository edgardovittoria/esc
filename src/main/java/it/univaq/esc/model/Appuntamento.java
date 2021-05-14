package it.univaq.esc.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.costi.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;


@Entity
@Table(name = "appuntamenti")
public class Appuntamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idAppuntamento;
    @Column
    private LocalDateTime dataOraInizioAppuntamento;
    @Column
    private LocalDateTime dataOraFineAppuntamento;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "PrenotazioneSpecs_ID", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private PrenotazioneSpecs prenotazioneSpecsAppuntamento;

    @Transient
    private CalcolatoreCosto calcolatoreCosto;

    @ManyToMany()
    @JoinColumn()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<UtentePolisportivaAbstract> partecipanti = new ArrayList<UtentePolisportivaAbstract>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<QuotaPartecipazione> quotePartecipazione = new ArrayList<QuotaPartecipazione>();


    public Appuntamento(){}

    public List<QuotaPartecipazione> getQuotePartecipazione() {
        return quotePartecipazione;
    }

    private void aggiungiQuotaPartecipazione(UtentePolisportivaAbstract sportivo){
        QuotaPartecipazione quota = new QuotaPartecipazione();
        quota.setCosto(this.getCalcolatoreCosto().calcolaQuotaPartecipazione(this));
        quota.setSportivoAssociato(sportivo);
        quota.setPagata(false);
        this.quotePartecipazione.add(quota);
    }

    public Appuntamento(LocalDateTime dataOraInizioAppuntamento, LocalDateTime dataOraFineAppuntamento, PrenotazioneSpecs specificaPrenotazione){
        setDataOraInizioAppuntamento(dataOraInizioAppuntamento);
        setDataOraFineAppuntamento(dataOraFineAppuntamento);
        setPrenotazioneSpecsAppuntamento(specificaPrenotazione);
    }

    public Impianto getImpiantoPrenotato(){
        return (Impianto)this.getPrenotazioneSpecsAppuntamento().getValoriSpecificheExtraPrenotazione().get("impianto");
    }

    private CalcolatoreCosto getCalcolatoreCosto(){
        return this.calcolatoreCosto;
    } 

    public void calcolaCosto(){
        this.getPrenotazioneSpecsAppuntamento().setCosto(getCalcolatoreCosto().calcolaCosto(this));
    }

    public void setCalcolatoreCosto(CalcolatoreCosto calcolatoreCosto){
        this.calcolatoreCosto = calcolatoreCosto;
    }

    public List<UtentePolisportivaAbstract> getListaPartecipanti() {
        return this.partecipanti;
    }

    public void aggiungiPartecipante(UtentePolisportivaAbstract sportivoPartecipante) {
        getListaPartecipanti().add(sportivoPartecipante);
        this.aggiungiQuotaPartecipazione(sportivoPartecipante);
    }
    

    /**
     * @return LocalDateTime return the dataOraInizioAppuntamento
     */
    public LocalDateTime getDataOraInizioAppuntamento() {
        return dataOraInizioAppuntamento;
    }

    /**
     * @param dataOraInizioAppuntamento the dataOraInizioAppuntamento to set
     */
    public void setDataOraInizioAppuntamento(LocalDateTime dataOraInizioAppuntamento) {
        this.dataOraInizioAppuntamento = dataOraInizioAppuntamento;
    }

    /**
     * @return LocalDateTime return the dataOraFineAppuntamento
     */
    public LocalDateTime getDataOraFineAppuntamento() {
        return dataOraFineAppuntamento;
    }

    /**
     * @param dataOraFineAppuntamento the dataOraFineAppuntamento to set
     */
    public void setDataOraFineAppuntamento(LocalDateTime dataOraFineAppuntamento) {
        this.dataOraFineAppuntamento = dataOraFineAppuntamento;
    }


    public LocalDate getDataAppuntamento(){
        return this.dataOraFineAppuntamento.toLocalDate();
    }

    public LocalTime getOraInizioAppuntamento(){
        return this.dataOraInizioAppuntamento.toLocalTime();
    }

    public LocalTime getOraFineAppuntamento(){
        return this.dataOraFineAppuntamento.toLocalTime();
    }

   


    /**
     * Verifica se l'appuntamento passato come parametro si sovrappone a quello sul quale è richiamato.
     * 
     * @param appuntamentoDaVerificareSovrapposizioneOrari appuntamento di cui verificare la sovrapposizione
     * @return true se i due appuntamenti si sovrappongono, false altrimenti
     */
    public boolean sovrapponeA(Appuntamento appuntamentoDaVerificareSovrapposizioneOrari){
        if(this.getDataOraInizioAppuntamento().isBefore(appuntamentoDaVerificareSovrapposizioneOrari.getDataOraInizioAppuntamento()) && this.getDataOraFineAppuntamento().isAfter(appuntamentoDaVerificareSovrapposizioneOrari.getDataOraInizioAppuntamento())){
            return true;
        }
        else if(this.getDataOraInizioAppuntamento().isBefore(appuntamentoDaVerificareSovrapposizioneOrari.getDataOraFineAppuntamento()) && this.getDataOraFineAppuntamento().isAfter(appuntamentoDaVerificareSovrapposizioneOrari.getDataOraFineAppuntamento())){
            return true;
        }
        else if(this.getDataOraInizioAppuntamento().equals(appuntamentoDaVerificareSovrapposizioneOrari.getDataOraInizioAppuntamento())){
            return true;
        }
        return false;
    } 

    public boolean sovrapponeA(LocalDateTime oraInizio, LocalDateTime oraFine){
        if(this.getDataOraInizioAppuntamento().isBefore(oraInizio) && this.getDataOraFineAppuntamento().isAfter(oraInizio)){
            return true;
        }
        else if(this.getDataOraInizioAppuntamento().isBefore(oraFine) && this.getDataOraFineAppuntamento().isAfter(oraFine)){
            return true;
        }
        else if(this.getDataOraInizioAppuntamento().equals(oraInizio)){
            return true;
        }
        return false;
    } 

   

    /**
     * @return Prenotazione return the prenotazioneAppuntamento
     */
    public PrenotazioneSpecs getPrenotazioneSpecsAppuntamento() {
        return prenotazioneSpecsAppuntamento;
    }

    /**
     * @param prenotazioneAppuntamento the prenotazioneAppuntamento to set
     */
    public void setPrenotazioneSpecsAppuntamento(PrenotazioneSpecs prenotazioneSpecsAppuntamento) {
        this.prenotazioneSpecsAppuntamento = prenotazioneSpecsAppuntamento;
    }

    public Integer getIdAppuntamento(){
        return this.idAppuntamento;
    }
    

    public Integer getNumeroPartecipantiTotali(){
        return this.getPrenotazioneSpecsAppuntamento().getNumeroGiocatori();
    }

    public String getTipoPrenotazione(){
        return this.getPrenotazioneSpecsAppuntamento().getTipoPrenotazione();
    }

    public boolean isPending(){
        return this.getPrenotazioneSpecsAppuntamento().isPending();
    }

    public void setPending(boolean pending){
        this.prenotazioneSpecsAppuntamento.setPending(pending);
    }

    /**
     * Restituisce l'utente che ha creato la Prenotazione da cui è scaturito questo appuntamento.
     * @return Utente che ha creato la prenotazione per questo appuntamento.
     */
    public UtentePolisportivaAbstract creatoDa(){
        return this.getPrenotazioneSpecsAppuntamento().getSportivoPrenotante();
    }

    /**
     * Indica se un utente passato come parametro partecipa o meno all'appuntamento.        
     * @param utenteDaVerificarePartecipazione utente di cui verificare la partecipazione all'appuntamento
     * @return true se l'utente è un partecipante, false altrimenti 
     */
    public boolean utenteIsPartecipante(UtentePolisportivaAbstract utenteDaVerificarePartecipazione){
        for(UtentePolisportivaAbstract partecipante : this.getListaPartecipanti()){
            if(((String)partecipante.getProprieta().get("email")).equals(((String)utenteDaVerificarePartecipazione.getProprieta().get("email")))){
                return true;
            }
        }
        return false;
    }

}