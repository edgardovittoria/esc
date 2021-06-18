package it.univaq.esc.dtoObjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class FormPrenotaImpiantoSquadra extends FormPrenotabile{
	
	
	private List<OrarioAppuntamento> orariSelezionati;
	private List<ImpiantoSelezionato> impianti;
	private List<Integer> squadreInvitate;
	private List<CheckboxPendingSelezionato> checkboxesPending;


}
