package it.univaq.esc.dtoObjects;


import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PrenotazioneLezioneSpecsDTO extends PrenotazioneSpecsDTO {
    
    private String istruttore;
   
    private Integer idImpiantoPrenotato;
    private String pavimentazioneImpianto;

   
    
}

