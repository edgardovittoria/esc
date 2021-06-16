package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class QuotaPartecipazioneDTO {

    private float costo;

    private String sportivo;

    private boolean pagata;


}
