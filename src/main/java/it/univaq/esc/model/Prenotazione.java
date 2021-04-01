package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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

    @ManyToOne()
    @JoinColumn()
    private Sportivo sportivoPrenotante;
  

    @ManyToMany()
    @JoinColumn()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sportivo> partecipanti = new ArrayList<Sportivo>();

    @OneToMany
    @JoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<QuotaPartecipazione> quoteDiPartecipazione = new ArrayList<QuotaPartecipazione>();    
    
    @OneToOne(targetEntity = Prenotabile.class, cascade = CascadeType.ALL)
    private IPrenotabile prenotazioneSpecs;

    @Transient
    private Calendario calendarioPrenotazione = new Calendario();

    public Prenotazione(){}

    public Prenotazione(int lastIdPrenotazione, IPrenotabile prenotazioneSpecs) {
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

    public void setSportivoPrenotante(Sportivo sportivoPrenotante) {
        this.sportivoPrenotante = sportivoPrenotante;
    }

    public Sportivo getSportivoPrenotante() {
        return this.sportivoPrenotante;
    }

    public List<Sportivo> getListaPartecipanti() {
        return this.partecipanti;
    }

    public void aggiungiPartecipante(Sportivo sportivoPartecipante) {
        getListaPartecipanti().add(sportivoPartecipante);
    }

    public void aggiungiListaPartecipanti(List<Sportivo> listaPartecipanti){
        this.getListaPartecipanti().addAll(listaPartecipanti);
    }

    public void setCalendario(Calendario datePrenotate){
        this.calendarioPrenotazione = datePrenotate;
    }

    public Calendario getCalendarioPrenotazione() {
        return this.calendarioPrenotazione;
    }

    public List<Appuntamento> getListaAppuntamenti(){
        return this.getCalendarioPrenotazione().getListaAppuntamenti();
    }

    public void aggiungiQuotaPartecipazione(Sportivo sportivoDaAssociare, float costo, boolean isPagata){
        QuotaPartecipazione quotaDaAggiungere = new QuotaPartecipazione(isPagata, costo);
        quotaDaAggiungere.setSportivoAssociato(sportivoDaAssociare);
        getListaQuotePartecipazione().add(quotaDaAggiungere);
    }

    public List<QuotaPartecipazione> getListaQuotePartecipazione(){
        return this.quoteDiPartecipazione;
    }

    public IPrenotabile getPrenotazioneSpecs() {
        return prenotazioneSpecs;
    }

    public void setPrenotazioneSpecs(IPrenotabile prenotazioneSpecs) {
        this.prenotazioneSpecs = prenotazioneSpecs;
    }

    public void impostaValoriPrenotazioneSpecs(HashMap<String, Object> mappaValori){
        this.getPrenotazioneSpecs().impostaValoriSpecifichePrenotazione(mappaValori);
    }

    public Object getSingolaSpecifica(String etichettaSpecifica){
        return this.getPrenotazioneSpecs().getValoriSpecifichePrenotazione().get(etichettaSpecifica);
    }
    

    
}