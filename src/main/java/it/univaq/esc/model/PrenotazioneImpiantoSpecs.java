package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

public class PrenotazioneImpiantoSpecs extends PrenotazioneSpecs {

    private int postiLiberi;
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
