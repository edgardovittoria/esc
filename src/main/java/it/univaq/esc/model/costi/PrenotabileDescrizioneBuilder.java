package it.univaq.esc.model.costi;

import java.util.HashMap;

import java.util.Map;

import org.springframework.stereotype.Component;

import it.univaq.esc.model.Sport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor @Getter @Setter
public class PrenotabileDescrizioneBuilder {
    
    private PrenotabileDescrizione prenotabileDescrizione;


    public PrenotabileDescrizioneBuilder creaNuovoDescrizione(Sport sport, String tipoPrenotazione){
        this.setPrenotabileDescrizione(new PrenotabileDescrizione());
        this.getPrenotabileDescrizione().setSportAssociato(sport);
        this.getPrenotabileDescrizione().setTipoPrenotazione(tipoPrenotazione);

        return this;
    }

    public PrenotabileDescrizioneBuilder impostaCostoOrario(Float costoDaImpostare){
        CostoPrenotabile costoOrario = new CostoPrenotabile();
        costoOrario.setCosto(costoDaImpostare);
        costoOrario.setTipoCosto(TipoCostoPrenotabile.COSTO_ORARIO.toString());
        this.getPrenotabileDescrizione().aggiungiCosto(costoOrario);

        return this;
    }

    public PrenotabileDescrizioneBuilder impostaCostoUnaTantum(Float costoDaImpostare){
        CostoPrenotabile costoUnaTantum = new CostoPrenotabile();
        costoUnaTantum.setCosto(costoDaImpostare);
        costoUnaTantum.setTipoCosto(TipoCostoPrenotabile.COSTO_UNA_TANTUM.toString());
        this.getPrenotabileDescrizione().aggiungiCosto(costoUnaTantum);

        return this;
    }

    public PrenotabileDescrizioneBuilder impostaCostoPavimentazione(Float costoDaImpostare, String tipoPavimentazione){
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
