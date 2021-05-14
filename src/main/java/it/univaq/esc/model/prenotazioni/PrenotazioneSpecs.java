package it.univaq.esc.model.prenotazioni;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;

import it.univaq.esc.model.Sport;
import it.univaq.esc.model.costi.PrenotabileDescrizione;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;






@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PrenotazioneSpecs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private boolean confermata = false;
    @Column
    private boolean pending = false;
    @Column
    private float costo;

    
    
    // @Column
    // protected String tipoPrenotazione;

    // @ManyToOne()
    // @JoinColumn()
    // private Sport sportAssociato;
    @ManyToOne
    @JoinColumn
    private PrenotabileDescrizione specificaDescription;

    
   

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prenotazione_ID", nullable = false)
    private Prenotazione prenotazioneAssociata;


    public PrenotazioneSpecs(){}
    

    public PrenotabileDescrizione getSpecificaDescription(){
        return this.specificaDescription;
    }

    public void setSpecificaDescrtiption(PrenotabileDescrizione specificaDescription){
        this.specificaDescription = specificaDescription;
    }

    public void setPending(boolean pending){
        this.pending = pending;
    }

    public boolean isPending(){
        return this.pending;
    }

    public void setConfermata() {
        this.confermata = true;
    }

    public void setNonConfermata() {
        this.confermata = false;
    }

    public boolean isConfermata() {
        return this.confermata;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    // public void setSportAssociato(Sport sport){
    //     this.sportAssociato = sport;
    // }

    public Sport getSportAssociato(){
        return this.getSpecificaDescription().getSportAssociato();
    }

    public Long getIDPrenotazioneSpecs(){
        return this.id;
    }

    

    public abstract void impostaValoriSpecificheExtraPrenotazione(Map<String, Object> mappaValori);
    
    public abstract Map<String, Object> getValoriSpecificheExtraPrenotazione();

    public Prenotazione getPrenotazioneAssociata() {
        return prenotazioneAssociata;
    }

    public void setPrenotazioneAssociata(Prenotazione prenotazioneAssociata) {
        this.prenotazioneAssociata = prenotazioneAssociata;
    }

    public abstract String getTipoPrenotazione();

    public Integer getNumeroGiocatori(){
        return this.getSportAssociato().getNumeroGiocatori();
    }

    public UtentePolisportivaAbstract getSportivoPrenotante(){
        return this.getPrenotazioneAssociata().getSportivoPrenotante();
    }

    //protected abstract void setTipoPrenotazione();


    }