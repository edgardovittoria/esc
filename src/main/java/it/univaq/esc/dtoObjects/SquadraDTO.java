package it.univaq.esc.dtoObjects;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SquadraDTO {

	private Integer idSquadra;
	private String nome;
	private SportDTO sport;
	private List<UtentePolisportivaDTO> membri;
	private List<UtentePolisportivaDTO> amministratori;
	private List<AppuntamentoDTO> appuntamenti;

}
