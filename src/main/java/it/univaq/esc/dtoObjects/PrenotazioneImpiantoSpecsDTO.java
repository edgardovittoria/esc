package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

public class PrenotazioneImpiantoSpecsDTO {
    
    private List<SportivoDTO> invitati = new ArrayList<SportivoDTO>();
    private int postiLiberi;

    public PrenotazioneImpiantoSpecsDTO(){}

    public List<SportivoDTO> getInvitati() {
        return invitati;
    }

    public void setInvitati(List<SportivoDTO> invitati) {
        this.invitati = invitati;
    }

    public int getPostiLiberi() {
        return postiLiberi;
    }

    public void setPostiLiberi(int postiLiberi) {
        this.postiLiberi = postiLiberi;
    }

    
}
