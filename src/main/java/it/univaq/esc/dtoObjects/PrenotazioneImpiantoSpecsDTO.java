package it.univaq.esc.dtoObjects;

import java.util.ArrayList;

import java.util.List;


import it.univaq.esc.model.PrenotazioneSpecs;
import it.univaq.esc.model.utenti.Sportivo;

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
    public void impostaValoriDTO(PrenotazioneSpecs specifica) {
        
        // I posti liberi sono da eliminare perché si possono calcolare in automatico
        setPostiLiberi(12);
        
        for(Sportivo invitato : (List<Sportivo>) specifica.getValoriSpecificheExtraPrenotazione().get("invitati")){
            SportivoDTO invitatoDTO = new SportivoDTO();
            invitatoDTO.impostaValoriDTO(invitato);
            this.getInvitati().add(invitatoDTO);
        }
        super.impostaValoriDTO(specifica);
        
    }



    

    
}
