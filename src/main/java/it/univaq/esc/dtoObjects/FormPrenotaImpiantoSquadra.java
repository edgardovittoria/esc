package it.univaq.esc.dtoObjects;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FormPrenotaImpiantoSquadra extends FormPrenotabile {

	private List<OrarioAppuntamento> orariSelezionati;
	private List<ImpiantoSelezionato> impianti;
	private List<Integer> squadreInvitate;
	private List<CheckboxPendingSelezionato> checkboxesPending;
	private Integer idSquadraPrenotante;

}
