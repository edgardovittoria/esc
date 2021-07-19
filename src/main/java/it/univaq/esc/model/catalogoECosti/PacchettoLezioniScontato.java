package it.univaq.esc.model.catalogoECosti;


import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import it.univaq.esc.model.Costo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class PacchettoLezioniScontato extends PrenotabileDescrizione{

	private Integer numeroLezioni;
	private PrenotabileDescrizione descrizioneLezione;
	
	@Override
	public Map<String, Costo> getMappaCosti() {
		Map<String, Costo> mappaCosti = descrizioneLezione.getMappaCosti();
        for(CostoPrenotabile costo : this.getListaCosti()){
            mappaCosti.putAll(costo.getMappaCosto());
        }
        return mappaCosti;
	}
	 
	public void setDescrizioneLezione(PrenotabileDescrizione descLezione) {
		descrizioneLezione = descLezione;
		setMassimoNumeroPartecipanti(descLezione.getMassimoNumeroPartecipanti());
		setMinimoNumeroPartecipanti(descLezione.getMinimoNumeroPartecipanti());
		setSportAssociato(descLezione.getSportAssociato());
	}
}
