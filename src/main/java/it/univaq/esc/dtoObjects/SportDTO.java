package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.Sport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SportDTO {
    
    private String nome;
    private Integer postiLiberi;
    private Integer numeroMinimoGiocatoriPerSquadra;



    
}
