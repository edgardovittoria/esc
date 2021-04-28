package it.univaq.esc.model.costi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.Sport;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCosto;
@Entity
public class PrenotabileDescrizione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String tipoPrenotazione;
    @ManyToOne
    @JoinColumn
    private Sport sportAssociato;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CostoPrenotabile> listaCosti = new ArrayList<CostoPrenotabile>();

    PrenotabileDescrizione(){}

    
    public List<CostoPrenotabile> getListaCosti() {
        return listaCosti;
    }


    public void setListaCosti(List<CostoPrenotabile> listaCosti) {
        this.listaCosti = listaCosti;
    }

    public void aggiungiCosto(CostoPrenotabile costoDaAggiungere){
        this.getListaCosti().add(costoDaAggiungere);
    }

    public Sport getSportAssociato() {
        return sportAssociato;
    }


    public void setSportAssociato(Sport sportAssociato) {
        this.sportAssociato = sportAssociato;
    }


    public String getTipoPrenotazione() {
        return tipoPrenotazione;
    }
    public void setTipoPrenotazione(String tipoPrenotazione) {
        this.tipoPrenotazione = tipoPrenotazione;
    }    

    public Map<String, Float> getMappaCosti(){
        Map<String, Float> mappaCosti = new HashMap<String, Float>();
        for(CostoPrenotabile costo : this.getListaCosti()){
            mappaCosti.putAll(costo.getMappaCosto());
        }
        return mappaCosti;
    }
}
