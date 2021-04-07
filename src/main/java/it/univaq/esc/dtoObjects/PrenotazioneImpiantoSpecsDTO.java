package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import it.univaq.esc.model.Sportivo;

public class PrenotazioneImpiantoSpecsDTO extends PrenotazioneSpecsDTO {
    
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
 

   

    @Override
    public void impostaValoriSpecificheExtraPrenotazioneDTO(Map<String, Object> mappaValori) {
        setPostiLiberi((int)mappaValori.get("postiLiberi"));
        for(Sportivo invitato : (List<Sportivo>) mappaValori.get("invitati")){
            SportivoDTO invitatoDTO = new SportivoDTO();
            invitatoDTO.impostaValoriDTO(invitato);
            this.getInvitati().add(invitatoDTO);
        }
        
    }

    @Override
    public Map<String, Object> getValoriSpecificheExtraPrenotazioneDTO() {
        HashMap<String, Object> mappaValori = new  HashMap<String, Object>();
        mappaValori.put("postiLiberi", this.getPostiLiberi());
        mappaValori.put("invitati", this.getInvitati());
        return mappaValori;
    }


    

    
}
