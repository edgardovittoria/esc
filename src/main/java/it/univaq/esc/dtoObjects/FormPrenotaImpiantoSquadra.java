package it.univaq.esc.dtoObjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class FormPrenotaImpiantoSquadra extends FormPrenotabile{
	
	private String sportSelezionato;
	private List<OrarioAppuntamento> orariSelezionati;
	private List<ImpiantoSelezionato> impianti;
	private List<Integer> squadreInvitate;
	private List<CheckboxPendingSelezionato> checkboxesPending;

	@Override
	public Map<String, Object> getValoriForm() {
		HashMap<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("sport", this.getSportSelezionato());
        mappaValori.put("listaOrariAppuntamenti", this.getOrariSelezionati());
        mappaValori.put("impianti", this.getImpianti());
        mappaValori.put("squadreInvitate", getSquadreInvitate());
        mappaValori.put("checkboxesPending", getCheckboxesPending());

        return mappaValori;
	}

}
