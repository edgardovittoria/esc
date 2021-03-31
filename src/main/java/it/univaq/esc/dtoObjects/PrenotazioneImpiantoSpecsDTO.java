package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.univaq.esc.model.IPrenotabile;
import it.univaq.esc.model.PrenotazioneImpiantoSpecs;
import it.univaq.esc.model.Sportivo;

public class PrenotazioneImpiantoSpecsDTO implements IPrenotabileDTO {
    
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

    /*private void impostaValoriDTO(IPrenotabile prenotazioneImpiantoSpecs){
        setPostiLiberi((int) prenotazioneImpiantoSpecs.getValoriSpecifichePrenotazione().get("postiLiberi"));
        for(Sportivo invitato : (List<Sportivo>) prenotazioneImpiantoSpecs.getValoriSpecifichePrenotazione().get("invitati")){
            SportivoDTO invitatoDTO = new SportivoDTO();
            invitatoDTO.impostaValoriDTO(invitato);
            this.getInvitati().add(invitatoDTO);
        }
    }
    */

    @Override
    public void impostaValoriSpecifichePrenotazioneDTO(HashMap<String, Object> mappaValori) {
        setPostiLiberi((int)mappaValori.get("postiLiberi"));
        for(Sportivo invitato : (List<Sportivo>) mappaValori.get("invitati")){
            SportivoDTO invitatoDTO = new SportivoDTO();
            invitatoDTO.impostaValoriDTO(invitato);
            this.getInvitati().add(invitatoDTO);
        }
        
    }

    @Override
    public HashMap<String, Object> getValoriSpecifichePrenotazioneDTO() {
        HashMap<String, Object> mappaValori = new  HashMap<String, Object>();
        mappaValori.put("postiLiberi", this.getPostiLiberi());
        mappaValori.put("invitati", this.getInvitati());
        return mappaValori;
    }

    
}
