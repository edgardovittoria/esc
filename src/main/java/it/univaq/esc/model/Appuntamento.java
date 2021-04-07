package it.univaq.esc.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityReference;


@Entity
@Table(name = "appuntamenti")
public class Appuntamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idAppuntamento;
    @Column
    private LocalDateTime dataOraInizioAppuntamento;
    @Column
    private LocalDateTime dataOraFineAppuntamento;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "PrenotazioneSpecs_ID", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private PrenotazioneSpecs prenotazioneSpecsAppuntamento;


    public Appuntamento(){}

    public Appuntamento(LocalDateTime dataOraInizioAppuntamento, LocalDateTime dataOraFineAppuntamento){
        setDataOraInizioAppuntamento(dataOraInizioAppuntamento);
        setDataOraFineAppuntamento(dataOraFineAppuntamento);
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

   public Integer getIdImpiantoPrenotato(){
       return this.getPrenotazioneSpecsAppuntamento().getIdImpiantoPrenotato();
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
    


}