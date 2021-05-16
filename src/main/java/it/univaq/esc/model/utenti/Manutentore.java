package it.univaq.esc.model.utenti;

import java.util.List;
import java.util.Map;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import it.univaq.esc.model.Calendario;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "Manutentore")
@Getter @Setter
public class Manutentore extends RuoloUtentePolisportivaDecorator{
    
    @Transient
    private Calendario calendario = new Calendario();

    public Manutentore(UtentePolisportivaAbstract utenteDaDecorare){
        super(utenteDaDecorare);
    }

    public Manutentore(UtentePolisportivaAbstract utenteDaDecorare, Calendario calendario){
        super(utenteDaDecorare);
        this.calendario = calendario;
    }


    public void aggiungiCalendarioNuoviImpegni(Calendario calendario) {
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