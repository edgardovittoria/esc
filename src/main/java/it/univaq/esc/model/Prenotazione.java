package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    

    @ManyToOne()
    @JoinColumn()
    private Sportivo sportivoPrenotante;
  
    @Column
    private String tipoPrenotazione;

    @ManyToOne()
    @JoinColumn()
    private Sport sportAssociato;
    
    
    @OneToOne(targetEntity = PrenotazioneSpecs.class, cascade = CascadeType.ALL)
    private List<PrenotazioneSpecs> listaSpecifichePrenotazione = new ArrayList<PrenotazioneSpecs>();

    @Transient
    private Calendario calendarioPrenotazione = new Calendario();

    public Prenotazione(){}

    public Prenotazione(int lastIdPrenotazione, PrenotazioneSpecs prenotazioneSpecs) {
        setIdPrenotazione(lastIdPrenotazione);
        //aggiungiQuotaPartecipazione(sportivoPrenotante, 0, false);
        this.getListaSpecifichePrenotazione().add(prenotazioneSpecs);
    }

    public int getIdPrenotazione(){
        return this.idPrenotazione;
    }

    private void setIdPrenotazione(int lastIdPrenotazione) {
        this.idPrenotazione = lastIdPrenotazione + 1;
    }

    

    public void setSportivoPrenotante(Sportivo sportivoPrenotante) {
        this.sportivoPrenotante = sportivoPrenotante;
    }

    public Sportivo getSportivoPrenotante() {
        return this.sportivoPrenotante;
    }

    
    public void setSportAssociato(Sport sport){
        this.sportAssociato = sport;
    }

    public Sport getSportAssociato(){
        return this.sportAssociato;
    }
    

    public void setCalendario(Calendario datePrenotate){
        this.calendarioPrenotazione = datePrenotate;
    }

    public void setCalendarioSpecifica(Calendario calendario, PrenotazioneSpecs specifica){
        specifica.impostaCalendario(calendario);
    }

    public void setImpiantoSpecifica(Impianto impianto, PrenotazioneSpecs specifica){
        specifica.setImpiantoPrenotato(impianto);
    }


    public Calendario getCalendarioPrenotazione() {
        return this.calendarioPrenotazione;
    }

    public List<Appuntamento> getListaAppuntamenti(){
        return this.getCalendarioPrenotazione().getListaAppuntamenti();
    }

    

    public List<PrenotazioneSpecs> getListaSpecifichePrenotazione() {
        return listaSpecifichePrenotazione;
    }

    public void setListaSpecifichePrenotazione(List<PrenotazioneSpecs> listaSpecifichePrenotazione) {
        this.listaSpecifichePrenotazione = listaSpecifichePrenotazione;
    }

    public void impostaValoriListaSpecifichePrenotazione(List<Map<String, Object>> listaMappeValori){
        for(PrenotazioneSpecs specifica : this.getListaSpecifichePrenotazione()){
            specifica.impostaValoriSpecificheExtraPrenotazione(listaMappeValori.get(this.getListaSpecifichePrenotazione().indexOf(specifica)));
        }
    }

    public void impostaValoriSpecificheExtraSingolaPrenotazioneSpecs(Map<String, Object> mappaValori, PrenotazioneSpecs specifica){
        specifica.impostaValoriSpecificheExtraPrenotazione(mappaValori);
    }

    public Object getSingolaSpecificaExtra(String etichettaSpecifica, PrenotazioneSpecs specifica){
        return specifica.getValoriSpecificheExtraPrenotazione().get(etichettaSpecifica);
    }

    public Map<String, Object> getSpecificheExtraSingolaPrenotazioneSpecs(PrenotazioneSpecs specifica){
        return specifica.getValoriSpecificheExtraPrenotazione();
    }

    public void setTipoPrenotazione(String tipoPrenotazione){
        this.tipoPrenotazione = tipoPrenotazione;
    }

    public String getTipoPrenotazione(){
        return this.tipoPrenotazione;
    }

    public void impostaCalendarioPrenotazioneDaSpecifiche(){
        for(PrenotazioneSpecs specifica : this.getListaSpecifichePrenotazione()){
            this.calendarioPrenotazione.unisciCalendario(specifica.getCalendarioPrenotazioneSpecs());
        }
    }

    public void aggiungiPartecipanteAPrenotazioneSpecs(Sportivo partecipante, PrenotazioneSpecs specificaPrenotazione){
        specificaPrenotazione.aggiungiPartecipante(partecipante);
    }

    
}