package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor
public abstract class PrenotazioneSpecsDTO {

    private Integer idPrenotazioneSpecsDTO;

    private boolean confermata = false;

    private float costo;

    // private Manutentore responsabilePrenotazione;

    private SportDTO sportAssociato;
    private String tipoSpecifica;
    private String modalitaPrenotazione;
       
    
}
