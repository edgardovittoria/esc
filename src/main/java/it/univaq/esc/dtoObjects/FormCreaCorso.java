package it.univaq.esc.dtoObjects;

import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor
public class FormCreaCorso extends FormPrenotabile{

	private FormPrenotaLezioneDTO formLezione;
	private List<String> invitatiCorso;
	private Integer numeroMinimoPartecipanti;
	private Integer numeroMassimoPartecipanti;
	private Float costoPerPartecipante;
	

}
