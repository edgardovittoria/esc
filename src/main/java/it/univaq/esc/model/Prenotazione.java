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

@Entity
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    private int idPrenotazione;
    @Column
    private boolean confermata = false;
    @Column
    private float costo;
    @OneToMany()
    @JoinColumn()
    private List<QuotaPartecipazione> quoteDiPartecipazione = new ArrayList<QuotaPartecipazione>();    
    @OneToOne(cascade = CascadeType.ALL)
    private PrenotazioneSpecs prenotazioneSpecs;

    public Prenotazione(){}

    public Prenotazione(int lastIdPrenotazione, Sportivo sportivoPrenotante, PrenotazioneSpecs prenotazioneSpecs) {
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