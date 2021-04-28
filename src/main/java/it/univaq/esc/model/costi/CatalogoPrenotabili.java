package it.univaq.esc.model.costi;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.Sport;
import it.univaq.esc.repository.PrenotabileDescrizioneRepository;

@Component
@Singleton
public class CatalogoPrenotabili {
    
    private List<PrenotabileDescrizione> catalogoPrenotabili;

    private PrenotabileDescrizioneBuilder prenotabileDescrizioneBuilder;

    @Autowired
    private PrenotabileDescrizioneRepository prenotabileDescrizioneRepository;

    CatalogoPrenotabili(){
        this.setPrenotabileDescrizioneBuilder(new PrenotabileDescrizioneBuilder());
    }

    public PrenotabileDescrizioneBuilder getPrenotabileDescrizioneBuilder() {
        return prenotabileDescrizioneBuilder;
    }

    private void setPrenotabileDescrizioneBuilder(PrenotabileDescrizioneBuilder prenotabileDescrizioneBuilder) {
        this.prenotabileDescrizioneBuilder = prenotabileDescrizioneBuilder;
    }

    private PrenotabileDescrizioneRepository getPrenotabileDescrizioneRepository() {
        return prenotabileDescrizioneRepository;
    }


    public List<PrenotabileDescrizione> getCatalogoPrenotabili() {
        return catalogoPrenotabili;
    }

    private void setCatalogoPrenotabili(List<PrenotabileDescrizione> catalogoPrenotabili) {
        this.catalogoPrenotabili = catalogoPrenotabili;
    }

    public PrenotabileDescrizione aggiungiPrenotabileACatalogo(PrenotabileDescrizione prenotabileDescrizioneDaAggiungere){
       this.getCatalogoPrenotabili().add(prenotabileDescrizioneDaAggiungere);
       return prenotabileDescrizioneDaAggiungere;
    }

    @PostConstruct
    public void popola(){
        this.setCatalogoPrenotabili(this.getPrenotabileDescrizioneRepository().findAll());
    }

    public CatalogoPrenotabili avviaCreazionePrenotabile(Sport sport, String tipoPrenotazione){
        this.getPrenotabileDescrizioneBuilder().creaNuovoDescrizione(sport, tipoPrenotazione);

        return this;
    }

    public CatalogoPrenotabili impostaCostoOrario(Float costo){
        this.getPrenotabileDescrizioneBuilder().impostaCostoOrario(costo);

        return this;
    }

    public CatalogoPrenotabili impostaCostoUnaTantum(Float costo){
        this.getPrenotabileDescrizioneBuilder().impostaCostoUnaTantum(costo);

        return this;
    }

    public CatalogoPrenotabili impostaCostoPavimentazione(Float costo, String tipoPavimentazione){
        this.getPrenotabileDescrizioneBuilder().impostaCostoPavimentazione(costo, tipoPavimentazione);

        return this;
    }

    public PrenotabileDescrizione salvaPrenotabileInCreazione(){
       return this.aggiungiPrenotabileACatalogo(this.getPrenotabileDescrizioneRepository().save(this.getPrenotabileDescrizioneBuilder().build()));

    }


    public PrenotabileDescrizione getPrenotabileDescrizioneByTipoPrenotazioneESport(String tipoPrenotazione, Sport sport){
        for(PrenotabileDescrizione desc : this.getCatalogoPrenotabili()){
            if(desc.getSportAssociato().getNome().equals(sport.getNome()) && desc.getTipoPrenotazione().equals(tipoPrenotazione)){
                return desc;
            }
        }
        return null;
    }


}
