package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class PrenotazioneImpiantoSpecs extends PrenotazioneSpecs {


    
    @OneToMany()
    @JoinColumn()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sportivo> invitati = new ArrayList<Sportivo>();

    @Column
    private int numeroGiocatoriNonRegistratiAssociati = 0;


 
    public PrenotazioneImpiantoSpecs(){}

    

    public List<Sportivo> getListaInvitati() {
        return this.invitati;
    }

    public void invitaSportivi(List<Sportivo> listaSportiviDaInvitare) {
        getListaInvitati().addAll(listaSportiviDaInvitare);
    }

    

    /**
     * @return int return the numeroGiocatoriNonRegistratiAssociati
     */
    public int getNumeroGiocatoriNonRegistratiAssociati() {
        return numeroGiocatoriNonRegistratiAssociati;
    }

    /**
     * @param numeroGiocatoriNonRegistratiAssociati the numeroGiocatoriNonRegistratiAssociati to set
     */
    public void setNumeroGiocatoriNonRegistratiAssociati(int numeroGiocatoriNonRegistratiAssociati) {
        this.numeroGiocatoriNonRegistratiAssociati = numeroGiocatoriNonRegistratiAssociati;
    }

    @Override
    public void impostaValoriSpecificheExtraPrenotazione(Map<String, Object> mappaValori) {
        for(String chiave : mappaValori.keySet()){
            switch (chiave) {
                case "invitati":
                    this.invitaSportivi((List<Sportivo>)mappaValori.get(chiave));
                    break;
                case "numeroGiocatoriNonIscritti" : 
                    this.setNumeroGiocatoriNonRegistratiAssociati((Integer)mappaValori.get(chiave));
                default:
                    break;
            }
        }
        
    }

    @Override
    public Map<String, Object> getValoriSpecificheExtraPrenotazione() {
        HashMap<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("invitati", this.getListaInvitati());
        mappaValori.put("numeroGiocatoriNonIscritti", this.getNumeroGiocatoriNonRegistratiAssociati());

        return mappaValori;
    }

}
