package it.univaq.esc.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;


@Entity
@Table(name = "prenotazioni")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPrenotazione")
public class Prenotazione {
    @Id
    private int idPrenotazione;
    

    @ManyToOne()
    @JoinColumn()
    private UtentePolisportivaAbstract sportivoPrenotante;

    @Column
    private LocalDateTime oraDataPrenotazione;
  
    
    
    
    @OneToMany(mappedBy = "prenotazioneAssociata")
    //@JoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PrenotazioneSpecs> listaSpecifichePrenotazione = new ArrayList<PrenotazioneSpecs>();

    public Prenotazione(){}

    public Prenotazione(int lastIdPrenotazione){
        setIdPrenotazione(lastIdPrenotazione);
    }

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

    
    public void aggiungiSpecifica(PrenotazioneSpecs specifica){
        this.getListaSpecifichePrenotazione().add(specifica);
    }

    public void setSportivoPrenotante(UtentePolisportivaAbstract sportivoPrenotante) {
        this.sportivoPrenotante = sportivoPrenotante;
    }

    public UtentePolisportivaAbstract getSportivoPrenotante() {
        return this.sportivoPrenotante;
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



    
}