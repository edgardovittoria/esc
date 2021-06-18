package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class FormPrenotaImpianto extends FormPrenotabile{
    

    private List<OrarioAppuntamento> orariSelezionati = new ArrayList<OrarioAppuntamento>();

    private List<ImpiantoSelezionato> impianti = new ArrayList<ImpiantoSelezionato>();

    private List<String> sportiviInvitati;

    private Integer postiLiberi;

    private Integer numeroGiocatoriNonIscritti;

    private List<CheckboxPendingSelezionato> checkboxesPending;




}
