package it.univaq.esc.dtoObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DatiAvviaPrenotazione {

	private String email;
	private String tipoPrenotazione;
	private String modalitaPrenotazione;
	private Integer idSquadra;
	private String nomePrenotabile;
}
