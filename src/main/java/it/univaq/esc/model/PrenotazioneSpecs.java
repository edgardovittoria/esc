package it.univaq.esc.model;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

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
import javax.persistence.Transient;

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

    @ManyToOne()
    @JoinColumn()
    private Manutentore responsabilePrenotazione;
    

    @ManyToMany()
    @JoinColumn()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sportivo> partecipanti = new ArrayList<Sportivo>();

    @Transient
    private Calendario calendarioPrenotazioneSpecs = new Calendario();

    @OneToMany
    @JoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<QuotaPartecipazione> quoteDiPartecipazione = new ArrayList<QuotaPartecipazione>();    

    @ManyToOne
    @JoinColumn
    private Impianto impiantoPrenotato;



    

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

    public void setImpiantoPrenotato(Impianto impiantoPrenotato){
        this.impiantoPrenotato = impiantoPrenotato;
    }

    public Impianto getImpiantoPrenotato(){
        return this.impiantoPrenotato;
    }

    public Integer getIdImpiantoPrenotato(){
        return this.getImpiantoPrenotato().getIdImpianto();
    }

    public List<Sportivo> getListaPartecipanti() {
        return this.partecipanti;
    }

    public void aggiungiPartecipante(Sportivo sportivoPartecipante) {
        getListaPartecipanti().add(sportivoPartecipante);
    }

    public void aggiungiQuotaPartecipazione(Sportivo sportivoDaAssociare, float costo, boolean isPagata){
        QuotaPartecipazione quotaDaAggiungere = new QuotaPartecipazione(isPagata, costo);
        quotaDaAggiungere.setSportivoAssociato(sportivoDaAssociare);
        getListaQuotePartecipazione().add(quotaDaAggiungere);
    }

    public List<QuotaPartecipazione> getListaQuotePartecipazione(){
        return this.quoteDiPartecipazione;
    }

    public void impostaCalendario(Calendario calendarioDaImpostare){
        this.calendarioPrenotazioneSpecs = calendarioDaImpostare;
    }

    public Calendario getCalendarioPrenotazioneSpecs(){
        return this.calendarioPrenotazioneSpecs;
    }

    public List<Appuntamento> getListaAppuntamentiPrenotazioneSpecs(){
        return this.getCalendarioPrenotazioneSpecs().getListaAppuntamenti();
    }

    public abstract void impostaValoriSpecificheExtraPrenotazione(Map<String, Object> mappaValori);
    
    public abstract Map<String, Object> getValoriSpecificheExtraPrenotazione();

    }