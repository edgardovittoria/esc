package it.univaq.esc.model.costi;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.Sport;
import it.univaq.esc.repository.SpecificaDescRepository;
@Component
@Singleton
public class ListinoPrezziDescrizioniPolisportiva {

    @Autowired
    private SpecificaDescRepository specificaDescRepository;

    
    private SpecificaDescBuilder specificaDescBuilder;


    private List<SpecificaDesc> listaDescrizioniPrezzi = new ArrayList<SpecificaDesc>();

    public ListinoPrezziDescrizioniPolisportiva(){}


    @PostConstruct
    public void popola(){
        outer: for(SpecificaDesc spec : this.getSpecificaDescRepository().findAll()){
            for(SpecificaDesc specifica : this.getListaDescrizioniPrezzi()){
                if(specifica.getTipoPrenotazione().equals(spec.getTipoPrenotazione()) && specifica.getSport().getNome().equals(spec.getSport().getNome())){
                    continue outer;
                }
            }
            this.getListaDescrizioniPrezzi().add(spec);
        }
    }


    private SpecificaDescBuilder getSpecificaDescBuilder() {
        return specificaDescBuilder;
    }

    private void setSpecificaDescBuilder(SpecificaDescBuilder specificaDescBuilder){
        this.specificaDescBuilder = specificaDescBuilder;
    }

    private SpecificaDescRepository getSpecificaDescRepository() {
        return specificaDescRepository;
    }


    public List<SpecificaDesc> getListaDescrizioniPrezzi() {
        return listaDescrizioniPrezzi;
    }

    private void setListaDescrizioniPrezzi(List<SpecificaDesc> listaDescrizioniPrezzi) {
        this.listaDescrizioniPrezzi = listaDescrizioniPrezzi;
    }

    public SpecificaDesc getSpecificaDescByTipoPrenotazioneESport(String tipoPrenotazione, Sport sportAssociatoPrenotazione){
        for(SpecificaDesc spec : this.getListaDescrizioniPrezzi()){
            if(spec.getTipoPrenotazione().equals(tipoPrenotazione) && spec.getSport().getNome().equals(sportAssociatoPrenotazione.getNome())){
                return spec;
            }
        }
        return null;
    }

    public void setCostoSpecificaDesc(String tipoPrenotazioneSpecifica, String tipoSpecificaDesc, Sport sportAssociatoSpecifica,float valoreCostoDaImpostare){
        SpecificaDesc spec = this.getSpecificaDescByTipoPrenotazioneESport(tipoPrenotazioneSpecifica, sportAssociatoSpecifica);
        spec.setCosto(valoreCostoDaImpostare, tipoSpecificaDesc);
    }

    public ListinoPrezziDescrizioniPolisportiva aggiunginuovaSpecificaDesc(String tipoPrenotazione, Sport sport){
        this.setSpecificaDescBuilder(new SpecificaDescBuilder());
        this.getSpecificaDescBuilder().creaNuovaSpecificaDesc(tipoPrenotazione, sport);

        return this;
    }

    public ListinoPrezziDescrizioniPolisportiva impostaPrezzoOrario(float prezzoOrario){
        this.getSpecificaDescBuilder().impostaCostoOrario(prezzoOrario);
        return this;
    }

    public ListinoPrezziDescrizioniPolisportiva impostaPrezzoPavimentazione(String tipoPavimentazione ,float prezzoPavimentazione){
        this.getSpecificaDescBuilder().impostaCostoPavimentazione(tipoPavimentazione, prezzoPavimentazione);
        return this;
    }

    

    public SpecificaDesc ritornaSpecificaCreata(){
        this.listaDescrizioniPrezzi.add(this.getSpecificaDescBuilder().build());
        this.getSpecificaDescRepository().save(this.getSpecificaDescBuilder().build());
        return this.getSpecificaDescBuilder().build();
        
    }
}
