package it.univaq.esc.model;

import java.util.ArrayList;

import java.util.List;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PrenotazioneSpecs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private boolean confermata = false;
    @Column
    private float costo;

    
    
    @Column
    protected String tipoPrenotazione;

    @ManyToOne()
    @JoinColumn()
    private Sport sportAssociato;


   

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prenotazione_ID", nullable = false)
    private Prenotazione prenotazioneAssociata;



    

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

    public void setSportAssociato(Sport sport){
        this.sportAssociato = sport;
    }

    public Sport getSportAssociato(){
        return this.sportAssociato;
    }

    public Long getIDPrenotazioneSpecs(){
        return this.id;
    }

    public PrenotazioneSpecs(){
        this.setTipoPrenotazione();
    }

    public abstract void impostaValoriSpecificheExtraPrenotazione(Map<String, Object> mappaValori);
    
    public abstract Map<String, Object> getValoriSpecificheExtraPrenotazione();

    public Prenotazione getPrenotazioneAssociata() {
        return prenotazioneAssociata;
    }

    public void setPrenotazioneAssociata(Prenotazione prenotazioneAssociata) {
        this.prenotazioneAssociata = prenotazioneAssociata;
    }

    public String getTipoPrenotazione(){
        return this.tipoPrenotazione;
    };

    protected abstract void setTipoPrenotazione();


    }