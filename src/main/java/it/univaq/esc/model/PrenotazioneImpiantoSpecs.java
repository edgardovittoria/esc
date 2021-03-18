package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity(name = "prenotazioneImpiantoSpecs")
public class PrenotazioneImpiantoSpecs extends PrenotazioneSpecs {

    @Column
    private int postiLiberi;
    @OneToMany()
    @JoinColumn()
    private List<Sportivo> invitati = new ArrayList<Sportivo>();

    public PrenotazioneImpiantoSpecs() {
    }

    public int getPostiLiberi() {
        return this.postiLiberi;
    }

    public void setPostiLiberi(int postiLiberi){
        this.postiLiberi = postiLiberi;
    }

    public List<Sportivo> getListaInvitati(){
        return this.invitati;
    }

    public void invitaSportivi(List<Sportivo> listaSportiviDaInvitare) {
        getListaInvitati().addAll(listaSportiviDaInvitare);
    }

    
    
}
