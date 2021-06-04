package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor
public class FormPrenotaLezioneDTO extends FormPrenotabile{

    private String sportSelezionato = "tennis";

    private List<OrarioAppuntamento> orariSelezionati = new ArrayList<OrarioAppuntamento>();

    private List<ImpiantoSelezionato> impianti = new ArrayList<ImpiantoSelezionato>();

    private List<IstruttoreSelezionato> istruttori = new ArrayList<IstruttoreSelezionato>();



    @Override
    public HashMap<String, Object> getValoriForm() {
        HashMap<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("sport", this.getSportSelezionato());
        mappaValori.put("listaOrariAppuntamenti", this.getOrariSelezionati());
        mappaValori.put("impianti", this.getImpianti());
        mappaValori.put("istruttori", this.getIstruttori());

        return mappaValori;
    }
    
}
