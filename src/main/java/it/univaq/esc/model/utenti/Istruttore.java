package it.univaq.esc.model.utenti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.Calendario;

import it.univaq.esc.model.Sport;
@Entity
@DiscriminatorValue(value = "Istruttore")
public class Istruttore extends RuoloUtentePolisportivaDecorator{

    @ManyToMany
    @JoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sport> sportInsegnati = new ArrayList<Sport>();

    @Transient
    private Calendario calendarioLezioni = new Calendario();


    public Istruttore(){}

    public Istruttore(UtentePolisportivaAbstract utenteDaDecorare, List<Sport> sportInsegnati){
        super(utenteDaDecorare);
        this.setSportInsegnati(sportInsegnati);
    }

    private Calendario getCalendarioLezioni() {
        return calendarioLezioni;
    }

    private void setCalendarioLezioni(Calendario calendarioLezioniDaAggiungere) {
        this.getCalendarioLezioni().unisciCalendario(calendarioLezioniDaAggiungere);
    }

    @Override
    public void setProprieta(Map<String, Object> mappaProprieta) {
        for(String chiave : mappaProprieta.keySet()){
            switch (chiave) {
                case "sportInsegnati":
                this.getSportInsegnati().addAll((List<Sport>)mappaProprieta.get("sportInsegnati"));
                    break;
                case "calendarioLezioni":
                this.setCalendarioLezioni((Calendario)mappaProprieta.get("calendarioLezioni"));
                break;
                default:
                    break;
            }
        }
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
