package it.univaq.esc.model.utenti;

import java.util.List;
import java.util.Map;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue(value = "Direttore")
@NoArgsConstructor
public class DirettorePolisportiva extends RuoloUtentePolisportivaDecorator{

    public DirettorePolisportiva(UtentePolisportivaAbstract utenteDaDecorare){
        super(utenteDaDecorare);
    }

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
