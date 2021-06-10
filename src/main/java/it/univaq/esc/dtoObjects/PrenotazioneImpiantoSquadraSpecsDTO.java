package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PrenotazioneImpiantoSquadraSpecsDTO extends PrenotazioneSpecsDTO{

	private List<Integer> squadreInvitate = new ArrayList<Integer>();
    private int postiLiberi;
    private Integer idImpiantoPrenotato;
    private String pavimentazioneImpianto;

   

    @Override
    public void impostaValoriDTO(Object specifica) {
    	PrenotazioneSpecs specs = (PrenotazioneSpecs)specifica;
    	
        // I posti liberi sono da eliminare perché si possono calcolare in automatico
        setPostiLiberi(12);
        
        for(Squadra invitato : (List<Squadra>) specs.getValoriSpecificheExtraPrenotazione().get("squadreInvitate")){
            
            this.getSquadreInvitate().add((Integer)invitato.getIdSquadra());
        }
        
        this.setIdImpiantoPrenotato(((Impianto)specs.getValoriSpecificheExtraPrenotazione().get("impianto")).getIdImpianto());
        this.setPavimentazioneImpianto(((Impianto)specs.getValoriSpecificheExtraPrenotazione().get("impianto")).getTipoPavimentazione().toString());
        super.impostaValoriDTO(specifica);
        
    }
}
