package it.univaq.esc.model.prenotazioni;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "prenotazioni")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPrenotazione")
@Getter @Setter @NoArgsConstructor
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


    public Prenotazione(int lastIdPrenotazione){
        setIdPrenotazione(lastIdPrenotazione +1);
    }

    public Prenotazione(int lastIdPrenotazione, PrenotazioneSpecs prenotazioneSpecs) {
        setIdPrenotazione(lastIdPrenotazione);
        //aggiungiQuotaPartecipazione(sportivoPrenotante, 0, false);
        this.getListaSpecifichePrenotazione().add(prenotazioneSpecs);
    }

    
    public void aggiungiSpecifica(PrenotazioneSpecs specifica){
        this.getListaSpecifichePrenotazione().add(specifica);
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


    public String getTipoPrenotazione(){
    
        return this.getListaSpecifichePrenotazione().get(0).getTipoPrenotazione();
    }

    
}