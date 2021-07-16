package it.univaq.esc.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter(value = AccessLevel.PRIVATE)
@Getter
public class Valuta {

	@Id
	@Enumerated(EnumType.STRING)
	private Valute valuta;
	
	public void imposta(Valute valuta) {
		setValuta(valuta);
	}
	
	public String getValoreComeStringa() {
		return getValuta().toString();
	}
}
