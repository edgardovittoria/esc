package it.univaq.esc.model;

import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;
@Entity
@DiscriminatorValue(value = "Istruttore")
public class Istruttore extends RuoloUtentePolisportivaDecorator{

    @OneToMany
    @JoinColumn
    private List<Sport> sportInsegnati;

    @Transient
    private Calendario calendarioLezioni = new Calendario();


    

    public Istruttore(List<Sport> sportInsegnati){
        this.setSportInsegnati(sportInsegnati);
    }

    private Calendario getCalendarioLezioni() {
        return calendarioLezioni;
    }

    private void setCalendarioLezioni(Calendario calendarioLezioni) {
        this.calendarioLezioni = calendarioLezioni;
    }

    @Override
    public void setProprieta(Map<String, Object> mappaProprieta) {
        this.getSportInsegnati().addAll((List<Sport>)mappaProprieta.get("sportInsegnati"));
        this.getCalendarioLezioni().unisciCalendario((Calendario)mappaProprieta.get("calendarioLezioni"));
        this.getUtentePolisportiva().setProprieta(mappaProprieta);
    }

    private List<Sport> getSportInsegnati() {
        return sportInsegnati;
    }

    private void setSportInsegnati(List<Sport> sportInsegnati) {
        this.sportInsegnati = sportInsegnati;
    }

    @Override
    public Map<String, Object> getProprieta() {
        Map<String, Object> mappaProprieta = this.getUtentePolisportiva().getProprieta();
        mappaProprieta.put("sportInsegnati", this.getSportInsegnati());
        mappaProprieta.put("calendarioLezioni", this.getCalendarioLezioni());

        return mappaProprieta;
    }

    @Override
    public List<String> getRuoliUtentePolisportiva() {
        List<String> listaRuoli = this.getUtentePolisportiva().getRuoliUtentePolisportiva();
        listaRuoli.add(TipoRuolo.ISTRUTTORE.toString());

        return listaRuoli;
    }
    
}
