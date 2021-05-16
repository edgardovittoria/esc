package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class FormPrenotaImpianto implements IFormPrenotabile{
    private String sportSelezionato = "tennis";

    private List<OrarioAppuntamento> orariSelezionati = new ArrayList<OrarioAppuntamento>();

    private List<ImpiantoSelezionato> impianti = new ArrayList<ImpiantoSelezionato>();

    private List<String> sportiviInvitati;

    private Integer postiLiberi;

    private Integer numeroGiocatoriNonIscritti;

    private List<CheckboxPendingSelezionato> checkboxesPending;




    @Override
    public HashMap<String, Object> getValoriForm() {
        HashMap<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("sport", this.getSportSelezionato());
        mappaValori.put("listaOrariAppuntamenti", this.getOrariSelezionati());
        mappaValori.put("impianti", this.getImpianti());
        mappaValori.put("invitati", this.getSportiviInvitati());
        mappaValori.put("postiLiberi", this.getPostiLiberi());
        mappaValori.put("numeroPartecipantiNonIscritti", this.getNumeroGiocatoriNonIscritti());
        mappaValori.put("checkboxesPending", this.getCheckboxesPending());

        return mappaValori;
    }


}
