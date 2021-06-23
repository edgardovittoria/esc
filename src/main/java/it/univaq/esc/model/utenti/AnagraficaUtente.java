package it.univaq.esc.model.utenti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AnagraficaUtente {

	@Column
	private String nome;
	@Column
	private String cognome;
	@Id
	private String email;
	@Column
	private String password;
}
