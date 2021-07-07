package it.univaq.esc.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter(value = AccessLevel.PRIVATE)
@Getter
public class Valuta {

	@Id
	@Enumerated
	private Valute valuta;
	
	public void imposta(Valute valuta) {
		setValuta(valuta);
	}
	
	public String getValoreComeStringa() {
		return getValuta().toString();
	}
}
