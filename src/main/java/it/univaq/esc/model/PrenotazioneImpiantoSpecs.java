package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name = "prenotazioneImpiantoSpecs")
@DiscriminatorValue("prenotazioneSpecsImpianto")
public class PrenotazioneImpiantoSpecs extends Prenotabile {


    @Column
    private int postiLiberi;
    @OneToMany()
    @JoinColumn()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sportivo> invitati = new ArrayList<Sportivo>();

    @OneToOne(targetEntity = Prenotabile.class, cascade = CascadeType.ALL)
    private IPrenotabile prenotazioneSpecsBase;

    public PrenotazioneImpiantoSpecs(IPrenotabile prenotazioneSpecsBase) {
        this.setPrenotazioneSpecsBase(prenotazioneSpecsBase);
    }

    public PrenotazioneImpiantoSpecs(){}

    public int getPostiLiberi() {
        return this.postiLiberi;
    }

    public void setPostiLiberi(int postiLiberi) {
        this.postiLiberi = postiLiberi;
    }

    public List<Sportivo> getListaInvitati() {
        return this.invitati;
    }

    public void invitaSportivi(List<Sportivo> listaSportiviDaInvitare) {
        getListaInvitati().addAll(listaSportiviDaInvitare);
    }



    /**
     * @return IPrenotabile return the prenotazioneSpecsBase
     */
    public IPrenotabile getPrenotazioneSpecsBase() {
        return prenotazioneSpecsBase;
    }

    /**
     * @param prenotazioneSpecsBase the prenotazioneSpecsBase to set
     */
    public void setPrenotazioneSpecsBase(IPrenotabile prenotazioneSpecsBase) {
        this.prenotazioneSpecsBase = prenotazioneSpecsBase;
    }


    @Override
    public void impostaValoriSpecifichePrenotazione(HashMap<String, Object> mappaValori) {
        this.getPrenotazioneSpecsBase().impostaValoriSpecifichePrenotazione(mappaValori);
        for(String chiave : mappaValori.keySet()){
            switch (chiave) {
                case "invitati":
                    this.invitaSportivi((List<Sportivo>)mappaValori.get(chiave));
                    break;
                case "postiLiberi" : 
                    this.setPostiLiberi((Integer)mappaValori.get(chiave));
                default:
                    break;
            }
        }

    }

    @Override
    public HashMap<String, Object> getValoriSpecifichePrenotazione() {
        HashMap<String, Object> mappaValori = this.getPrenotazioneSpecsBase().getValoriSpecifichePrenotazione();
        mappaValori.put("invitati", this.getListaInvitati());
        mappaValori.put("postiLiberi", this.getPostiLiberi());

        return mappaValori;
    }

    @Override
    public String getTipoSpecifica() {
        return TipiPrenotazione.IMPIANTO.toString();
    }

}
