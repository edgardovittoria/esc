package it.univaq.esc.model.catalogoECosti;

import java.math.BigDecimal;
import java.util.HashMap;

import java.util.Map;

import org.springframework.stereotype.Component;

import it.univaq.esc.model.Costo;
import it.univaq.esc.model.Sport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor @Getter @Setter
public class PrenotabileDescrizioneBuilder {
    
    private PrenotabileDescrizione prenotabileDescrizione;


    public PrenotabileDescrizioneBuilder creaNuovaDescrizione(){
        this.setPrenotabileDescrizione(new PrenotabileDescrizione());
        return this;
    }

    public PrenotabileDescrizioneBuilder impostaSport(Sport sportDaImpostare) {
		this.getPrenotabileDescrizione().setSportAssociato(sportDaImpostare);
		return this;
	}
    
    public PrenotabileDescrizioneBuilder impostaTipoPrenotazione(String tipoPrenotazione) {
    	this.getPrenotabileDescrizione().setTipoPrenotazione(tipoPrenotazione);
    	return this;
    }
    
    public PrenotabileDescrizioneBuilder impostaSogliaMinimaPartecipanti(Integer numeroMinimoPartecipanti) {
    	this.getPrenotabileDescrizione().setMinimoNumeroPartecipanti(numeroMinimoPartecipanti);
    	return this;
	}
    
    public PrenotabileDescrizioneBuilder impostaSogliaMassimaPartecipanti(Integer numeroMassimoPartecipanti) {
		this.getPrenotabileDescrizione().setMassimoNumeroPartecipanti(numeroMassimoPartecipanti);
		return this;
	}
    
    public PrenotabileDescrizioneBuilder impostaModalitaPrenotazioneComeSingoloUtente() {
    	this.getPrenotabileDescrizione().setModalitaPrenotazione(ModalitaPrenotazione.SINGOLO_UTENTE.toString());
    	return this;
    }
    
    public PrenotabileDescrizioneBuilder impostaModalitaPrenotazioneComeSquadra() {
		this.getPrenotabileDescrizione().setModalitaPrenotazione(ModalitaPrenotazione.SQUADRA.toString());
		return this;
	}
    
    public PrenotabileDescrizioneBuilder impostaDescrizione(String descrizioneEvento) {
    	getPrenotabileDescrizione().setDescrizione(descrizioneEvento);
    	
    	return this;
    }
    
    
    public PrenotabileDescrizioneBuilder impostaCostoOrario(Costo costoDaImpostare){
        CostoPrenotabile costoOrario = new CostoPrenotabile();
        costoOrario.setCosto(costoDaImpostare);
        costoOrario.setTipoCosto(TipoCostoPrenotabile.COSTO_ORARIO.toString());
        this.getPrenotabileDescrizione().aggiungiCosto(costoOrario);

        return this;
    }

    public PrenotabileDescrizioneBuilder impostaCostoUnaTantum(Costo costoDaImpostare){
        CostoPrenotabile costoUnaTantum = new CostoPrenotabile();
        costoUnaTantum.setCosto(costoDaImpostare);
        costoUnaTantum.setTipoCosto(TipoCostoPrenotabile.COSTO_UNA_TANTUM.toString());
        this.getPrenotabileDescrizione().aggiungiCosto(costoUnaTantum);

        return this;
    }

    public PrenotabileDescrizioneBuilder impostaCostoPavimentazione(Costo costoDaImpostare, String tipoPavimentazione){
        CostoPrenotabile costoPavimentazione = new CostoPrenotabilePavimentazione();
        costoPavimentazione.setCosto(costoDaImpostare);
        costoPavimentazione.setTipoCosto(TipoCostoPrenotabile.COSTO_PAVIMENTAZIONE.toString());
        Map<String, Object> mappaProprieta = new HashMap<String, Object>();
        mappaProprieta.put("tipoPavimentazione", tipoPavimentazione);
        costoPavimentazione.setProprieta(mappaProprieta);
        this.getPrenotabileDescrizione().aggiungiCosto(costoPavimentazione);

        return this;
    }

    public PrenotabileDescrizione build(){
        return this.getPrenotabileDescrizione();
    }


}
