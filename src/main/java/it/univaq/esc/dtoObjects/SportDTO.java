package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.Sport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SportDTO implements IModelToDTO{
    
    private String nome;
    private Integer postiLiberi;
    private Integer numeroMinimoGiocatoriPerSquadra;


    @Override
    public void impostaValoriDTO(Object modelDaConvertire){
        Sport sport = (Sport)modelDaConvertire;
        this.nome = sport.getNome();
        this.postiLiberi = sport.getNumeroGiocatoriPerIncontro();
        setNumeroMinimoGiocatoriPerSquadra(sport.getNumeroMinimoGiocatoriPerSquadra());
    }
    
}
