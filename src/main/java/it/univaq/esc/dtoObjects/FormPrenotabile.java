package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class FormPrenotabile {

	private String sportSelezionato;

	private List<OrarioAppuntamento> orariSelezionati = new ArrayList<OrarioAppuntamento>();

	private List<ImpiantoSelezionato> impianti = new ArrayList<ImpiantoSelezionato>();

	private List<IstruttoreSelezionato> istruttori = new ArrayList<IstruttoreSelezionato>();

	private List<String> sportiviInvitati;

	private Integer postiLiberi;

	private Integer numeroGiocatoriNonIscritti;

	private List<Integer> squadreInvitate;

	private List<CheckboxPendingSelezionato> checkboxesPending;

	//private Integer idSquadraPrenotante;

	private Integer numeroMinimoPartecipanti;

	private Integer numeroMassimoPartecipanti;

	private Float costoPerPartecipante;

}