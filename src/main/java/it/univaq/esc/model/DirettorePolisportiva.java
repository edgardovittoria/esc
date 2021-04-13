package it.univaq.esc.model;

import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue(value = "Direttore")
public class DirettorePolisportiva extends RuoloUtentePolisportivaDecorator{

    @Override
    public void setProprieta(Map<String, Object> mappaProprieta) {
        this.getUtentePolisportiva().setProprieta(mappaProprieta);
        
    }

    @Override
    public Map<String, Object> getProprieta() {
       return this.getUtentePolisportiva().getProprieta();
    }

    @Override
    public List<String> getRuoliUtentePolisportiva() {
        List<String> listaRuoli = this.getUtentePolisportiva().getRuoliUtentePolisportiva();
        listaRuoli.add(TipoRuolo.DIRETTORE.toString());

        return listaRuoli;
    }
    
}
