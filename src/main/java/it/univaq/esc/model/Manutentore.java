package it.univaq.esc.model;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value = "Manutentore")
public class Manutentore extends RuoloUtentePolisportivaDecorator{
    
    @Transient
    private Calendario calendario = new Calendario();

    public Manutentore(){}

    public Manutentore(Calendario calendario){
        
        this.calendario = calendario;
    }


    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.getCalendario().unisciCalendario(calendario);
    }

    @Override
    public void setProprieta(Map<String, Object> mappaProprieta) {
        this.getCalendario().unisciCalendario((Calendario)mappaProprieta.get("calendarioManutentore"));
        this.getUtentePolisportiva().setProprieta(mappaProprieta);
    }

    @Override
    public Map<String, Object> getProprieta() {
        Map<String, Object> mappaProprieta = this.getUtentePolisportiva().getProprieta();
        mappaProprieta.put("calendarioManutentore", this.getCalendario());

        return mappaProprieta;
    }

    @Override
    public List<String> getRuoliUtentePolisportiva() {
        List<String> listaRuoli = this.getUtentePolisportiva().getRuoliUtentePolisportiva();
        listaRuoli.add(TipoRuolo.MANUTENTORE.toString());

        return listaRuoli;
    }

    
}