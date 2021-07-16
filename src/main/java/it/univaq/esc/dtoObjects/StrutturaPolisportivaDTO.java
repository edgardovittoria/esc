package it.univaq.esc.dtoObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class StrutturaPolisportivaDTO {

    private int idStruttura;
    private boolean indoor;
    private String pavimentazione;
    private List<SportDTO> sportPraticabili = new ArrayList<SportDTO>();
    private List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();

}
