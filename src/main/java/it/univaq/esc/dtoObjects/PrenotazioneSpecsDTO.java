package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor
public abstract class PrenotazioneSpecsDTO implements IModelToDTO{

    private Long idPrenotazioneSpecsDTO;

    private boolean confermata = false;

    private float costo;

    // private Manutentore responsabilePrenotazione;

    private SportDTO sportAssociato;
    private String tipoSpecifica;
        

    @Override
    public void impostaValoriDTO(Object prenotazioneSpecs){
    	PrenotazioneSpecs specs = (PrenotazioneSpecs)prenotazioneSpecs;
        SportDTO sportAssociato = new SportDTO();
        sportAssociato.impostaValoriDTO(specs.getSportAssociato());
        this.setSportAssociato(sportAssociato);
        this.setIdPrenotazioneSpecsDTO(specs.getId());
        this.setTipoSpecifica(specs.getTipoPrenotazione());
        this.setCosto(specs.getCosto());
    };
    

    
}
