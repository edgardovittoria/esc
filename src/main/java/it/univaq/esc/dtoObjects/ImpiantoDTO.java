package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

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
