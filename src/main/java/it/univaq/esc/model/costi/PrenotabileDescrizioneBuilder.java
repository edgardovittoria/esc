package it.univaq.esc.model.costi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import it.univaq.esc.model.Sport;

public class PrenotabileDescrizioneBuilder {
    
    private PrenotabileDescrizione prenotabileDescrizione;

    PrenotabileDescrizioneBuilder(){

    }

    private PrenotabileDescrizione getPrenotabileDescrizione() {
        return prenotabileDescrizione;
    }

    private void setPrenotabileDescrizione(PrenotabileDescrizione prenotabileDescrizione) {
        this.prenotabileDescrizione = prenotabileDescrizione;
    }

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
