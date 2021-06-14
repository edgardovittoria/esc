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

	private List<String> invitati = new ArrayList<String>();
    private int postiLiberi;
    private Integer idImpiantoPrenotato;
    private String pavimentazioneImpianto;

  
}
