package it.univaq.esc.model.costi;

import java.util.HashMap;
import java.util.Map;

import it.univaq.esc.model.Sport;

public class SpecificaDescBuilder {
    
    private SpecificaDesc specifica;


    public SpecificaDescBuilder(){
        
    }


    public SpecificaDescBuilder creaNuovaSpecificaDesc(String tipoPrenotazione, Sport sport){
        this.setSpecifica(new SpecificaDescCostoOrario());
        this.getSpecifica().setTipoPrenotazione(tipoPrenotazione);
        this.getSpecifica().setSport(sport);

        return this;
    }

    private SpecificaDesc getSpecifica() {
        return specifica;
    }


    private void setSpecifica(SpecificaDesc specifica) {
        this.specifica = specifica;
    }

    public  SpecificaDescBuilder impostaCostoOrario(float costoOrario){
        this.getSpecifica().setCosto(costoOrario, TipoSpecificaDesc.COSTO_ORARIO.toString());

        return this;
    }

    public  SpecificaDescBuilder impostaCostoPavimentazione(String tipoPavimentazione ,float costoPavimentazione){
        SpecificaDescCostoPavimentazione specPavimentazione = new SpecificaDescCostoPavimentazione();
        Map<String, Object> valori = new HashMap<String, Object>();
        valori.put("tipoPavimentazione", tipoPavimentazione);
        specPavimentazione.impostaProprieta(valori);
        specPavimentazione.setSpecifica(this.getSpecifica());
        this.setSpecifica(specPavimentazione);
        this.getSpecifica().setCosto(costoPavimentazione, TipoSpecificaDesc.COSTO_PAVIMENTAZIONE.toString());

        return this;
    }


    public SpecificaDesc build(){
        return this.getSpecifica();
    }


}
