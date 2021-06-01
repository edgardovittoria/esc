package it.univaq.esc.dtoObjects;

import java.util.ArrayList;

import java.util.List;

import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PrenotazioneImpiantoSpecsDTO extends PrenotazioneSpecsDTO {
    
    private List<String> invitati = new ArrayList<String>();
    private int postiLiberi;
    private Integer idImpiantoPrenotato;
    private String pavimentazioneImpianto;

   

    @Override
    public void impostaValoriDTO(Object specifica) {
    	PrenotazioneSpecs specs = (PrenotazioneSpecs)specifica;
    	
        // I posti liberi sono da eliminare perché si possono calcolare in automatico
        setPostiLiberi(12);
        
        for(UtentePolisportivaAbstract invitato : (List<UtentePolisportivaAbstract>) specs.getValoriSpecificheExtraPrenotazione().get("invitati")){
            
            this.getInvitati().add((String)invitato.getProprieta().get("email"));
        }
        
        this.setIdImpiantoPrenotato(((Impianto)specs.getValoriSpecificheExtraPrenotazione().get("impianto")).getIdImpianto());
        this.setPavimentazioneImpianto(((Impianto)specs.getValoriSpecificheExtraPrenotazione().get("impianto")).getTipoPavimentazione().toString());
        super.impostaValoriDTO(specifica);
        
    }



    

    
}
