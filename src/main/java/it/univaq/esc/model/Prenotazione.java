package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name = "prenotazioni")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPrenotazione")
public class Prenotazione {
    @Id
    private int idPrenotazione;
    @Column
    private boolean confermata = false;
    @Column
    private float costo;
  
    @OneToMany
    @JoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<QuotaPartecipazione> quoteDiPartecipazione = new ArrayList<QuotaPartecipazione>();    
    @OneToOne(cascade = CascadeType.ALL)
    private PrenotazioneSpecs prenotazioneSpecs;

    @Transient
    private Calendario calendarioPrenotazione = new Calendario();

    public Prenotazione(){}

    public Prenotazione(int lastIdPrenotazione, PrenotazioneSpecs prenotazioneSpecs) {
        setIdPrenotazione(lastIdPrenotazione);
        //aggiungiQuotaPartecipazione(sportivoPrenotante, 0, false);
        this.prenotazioneSpecs = prenotazioneSpecs;
    }

    public int getIdPrenotazione(){
        return this.idPrenotazione;
    }

    private void setIdPrenotazione(int lastIdPrenotazione) {
        this.idPrenotazione = lastIdPrenotazione + 1;
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

    public void setCalendario(Calendario datePrenotate){
        this.calendarioPrenotazione = datePrenotate;
    }

    public Calendario getCalendarioPrenotazione() {
        return this.calendarioPrenotazione;
    }

    public void aggiungiQuotaPartecipazione(Sportivo sportivoDaAssociare, float costo, boolean isPagata){
        QuotaPartecipazione quotaDaAggiungere = new QuotaPartecipazione(isPagata, costo);
        quotaDaAggiungere.setSportivoAssociato(sportivoDaAssociare);
        getListaQuotePartecipazione().add(quotaDaAggiungere);
    }

    public List<QuotaPartecipazione> getListaQuotePartecipazione(){
        return this.quoteDiPartecipazione;
    }

    public PrenotazioneSpecs getPrenotazioneSpecs() {
        return prenotazioneSpecs;
    }

    public void setPrenotazioneSpecs(PrenotazioneSpecs prenotazioneSpecs) {
        this.prenotazioneSpecs = prenotazioneSpecs;
    }

    
    

    
}