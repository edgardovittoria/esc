package it.univaq.esc.dtoObjects;

import java.util.ArrayList;

import java.util.List;

import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.PrenotazioneSpecs;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

public class PrenotazioneImpiantoSpecsDTO extends PrenotazioneSpecsDTO {
    
    private List<SportivoDTO> invitati = new ArrayList<SportivoDTO>();
    private int postiLiberi;
    private Integer idImpiantoPrenotato;

    public PrenotazioneImpiantoSpecsDTO(){}

    public Integer getIdImpiantoPrenotato() {
        return idImpiantoPrenotato;
    }

    public void setIdImpiantoPrenotato(Integer impiantoPrenotato) {
        this.idImpiantoPrenotato = impiantoPrenotato;
    }

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
        
        for(UtentePolisportivaAbstract invitato : (List<UtentePolisportivaAbstract>) specifica.getValoriSpecificheExtraPrenotazione().get("invitati")){
            SportivoDTO invitatoDTO = new SportivoDTO();
            invitatoDTO.impostaValoriDTO(invitato);
            this.getInvitati().add(invitatoDTO);
        }
        
        this.setIdImpiantoPrenotato(((Impianto)specifica.getValoriSpecificheExtraPrenotazione().get("impianto")).getIdImpianto());
        super.impostaValoriDTO(specifica);
        
    }



    

    
}
