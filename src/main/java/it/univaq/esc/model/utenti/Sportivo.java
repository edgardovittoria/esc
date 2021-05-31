package it.univaq.esc.model.utenti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import it.univaq.esc.model.Sport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "Sportivo")
@Getter @Setter
@NoArgsConstructor
public class Sportivo extends RuoloUtentePolisportivaDecorator{

    
    @ManyToMany()
    @JoinTable(name = "sport_praticati_sportivi",
                joinColumns = {@JoinColumn(name="email")},
                inverseJoinColumns = {@JoinColumn(name="sport_praticato")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sport> sportPraticatiDalloSportivo = new ArrayList<Sport>();
    
    @Column
    private boolean moroso = false;


    public Sportivo(UtentePolisportivaAbstract utenteDaDecorare){
        super(utenteDaDecorare);
    }

    public Sportivo(UtentePolisportivaAbstract utenteDaDecorare, List<Sport> sportPraticati) {
        super(utenteDaDecorare);
        this.setSportPraticatiDalloSportivo(sportPraticati);
    }

   

    public void aggiungiSporPraticatoDalloSportivo(Sport sportPraticato){
        this.sportPraticatiDalloSportivo.add(sportPraticato);
    }

    @Override
    public void setProprieta(Map<String, Object> mappaProprieta) {
        if(mappaProprieta.containsKey("sportPraticati")){
        this.setSportPraticatiDalloSportivo((List<Sport>)mappaProprieta.get("sportPraticati"));
        this.setMoroso((boolean)mappaProprieta.get("moroso"));
        }
        this.getUtentePolisportiva().setProprieta(mappaProprieta);
    }

    @Override
    public Map<String, Object> getProprieta() {
        Map<String, Object> mappaProprieta = this.getUtentePolisportiva().getProprieta();
        mappaProprieta.put("sportPraticati", this.getSportPraticatiDalloSportivo());
        mappaProprieta.put("moroso", isMoroso());
        return mappaProprieta;
    }

    @Override
    public List<String> getRuoliUtentePolisportiva() {
        List<String> listaRuoli = this.getUtentePolisportiva().getRuoliUtentePolisportiva();
        listaRuoli.add(TipoRuolo.SPORTIVO.toString());

        return listaRuoli;
    }

    
    
    
}
