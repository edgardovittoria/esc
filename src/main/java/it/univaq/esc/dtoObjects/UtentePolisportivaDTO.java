package it.univaq.esc.dtoObjects;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UtentePolisportivaDTO {

	private String nome;
	private String cognome;
	private String email;
	private List<String> ruoli;
	private Map<String, Object> attributiExtra;

}
