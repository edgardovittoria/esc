package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ImpiantoDTO {

    private int idImpianto;
    private boolean indoor;
    private String pavimentazione;
    private List<SportDTO> sportPraticabili = new ArrayList<SportDTO>();
    private List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();

       
    
}
