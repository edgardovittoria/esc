package it.univaq.esc.model.catalogoECosti;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component(value = "PRENOTABILE_DESCRIZIONE_CON_NUMERO_DATE_BUILDER")
@NoArgsConstructor
@Getter
@Setter
public class PrenotabileDescrizioneConNumeroDateBuilder extends PrenotabileDescrizioneBuilder{

	@Override
		public PrenotabileDescrizioneBuilder creaNuovaDescrizione() {
		setPrenotabileDescrizione(new PrenotabileDescrizioneConNumeroDate());
		return this;
		}
	
	public PrenotabileDescrizioneBuilder impostaNumeroDatePacchettoLezioni(Integer numeroDate) {
		((PrenotabileDescrizioneConNumeroDate) getPrenotabileDescrizione()).setNumeroLezioni(numeroDate);
		return this;
	}
}
