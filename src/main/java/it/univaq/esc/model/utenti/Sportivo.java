package it.univaq.esc.model.utenti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import it.univaq.esc.model.Sport;

@Entity
@DiscriminatorValue(value = "Sportivo")
public class Sportivo extends RuoloUtentePolisportivaDecorator{

    
    @ManyToMany()
    @JoinTable(name = "sport_praticati_sportivi",
                joinColumns = {@JoinColumn(name="email")},
                inverseJoinColumns = {@JoinColumn(name="sport_praticato")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sport> sportPraticatiDalloSportivo = new ArrayList<Sport>();

    public Sportivo(){}

    public Sportivo(UtentePolisportivaAbstract utenteDaDecorare){
        super(utenteDaDecorare);
    }

    public Sportivo(UtentePolisportivaAbstract utenteDaDecorare, List<Sport> sportPraticati) {
        super(utenteDaDecorare);
        this.setSportPraticatiDalloSportivo(sportPraticati);
    }

   

    public List<Sport> getSportPraticatiDalloSportivo() {
        return sportPraticatiDalloSportivo;
    }

    public void setSportPraticatiDalloSportivo(List<Sport> sportPraticatiDalloSportivo) {
        this.sportPraticatiDalloSportivo = sportPraticatiDalloSportivo;
    }

    public void aggiungiSporPraticatoDalloSportivo(Sport sportPraticato){
        this.sportPraticatiDalloSportivo.add(sportPraticato);
    }

    @Override
    public void setProprieta(Map<String, Object> mappaProprieta) {
        this.setSportPraticatiDalloSportivo((List<Sport>)mappaProprieta.get("sportPraticati"));
        this.getUtentePolisportiva().setProprieta(mappaProprieta);
    }

    @Override
    public Map<String, Object> getProprieta() {
        Map<String, Object> mappaProprieta = this.getUtentePolisportiva().getProprieta();
        mappaProprieta.put("sportPraticati", this.getSportPraticatiDalloSportivo());
        return mappaProprieta;
    }

    @Override
    public List<String> getRuoliUtentePolisportiva() {
        List<String> listaRuoli = this.getUtentePolisportiva().getRuoliUtentePolisportiva();
        listaRuoli.add(TipoRuolo.SPORTIVO.toString());

        return listaRuoli;
    }

    
    
    
}
