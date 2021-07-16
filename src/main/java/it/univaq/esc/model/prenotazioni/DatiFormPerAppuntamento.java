package it.univaq.esc.model.prenotazioni;

import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DatiFormPerAppuntamento {
	
	private LocalDate dataAppuntamento;
	private LocalTime oraInizio;
	private LocalTime oraFine;
	private boolean pending = false;
	private PrenotabileDescrizione descrizioneEvento;
	private List<UtentePolisportiva> invitati = new ArrayList<UtentePolisportiva>();
	private List<Squadra> squadreInvitate = new ArrayList<Squadra>();
	private Impianto impiantoPrenotato;
	private UtentePolisportiva istruttore;
	private Integer numeroPartecipantiNonIscritti;
}
