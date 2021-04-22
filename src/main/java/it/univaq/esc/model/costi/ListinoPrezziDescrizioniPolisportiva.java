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
        this.getListaDescrizioniPrezzi().addAll(this.getSpecificaDescRepository().findAll());
        List<Integer> listaindiciDaEliminare = new ArrayList<Integer>();
        for(SpecificaDesc specifica : this.getListaDescrizioniPrezzi().subList(0, this.getListaDescrizioniPrezzi().size() - 1)){
            int j=0;
            for(SpecificaDesc specificaSuccessivo : this.getListaDescrizioniPrezzi().subList(this.getListaDescrizioniPrezzi().indexOf(specifica)+1, this.getListaDescrizioniPrezzi().size())){
                if(specifica.getTipoPrenotazione().equals(specificaSuccessivo.getTipoPrenotazione()) && specifica.getSport().getNome().equals(specificaSuccessivo.getSport().getNome())){
                    j++;
                }
            }
            if(j>0){
                listaindiciDaEliminare.add(this.getListaDescrizioniPrezzi().indexOf(specifica));
            }

        }
        List<SpecificaDesc> utentiDaEliminare = new ArrayList<SpecificaDesc>();
        for(Integer index : listaindiciDaEliminare){
            utentiDaEliminare.add(this.getListaDescrizioniPrezzi().get(index));
        }
        this.getListaDescrizioniPrezzi().removeAll(utentiDaEliminare);
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
