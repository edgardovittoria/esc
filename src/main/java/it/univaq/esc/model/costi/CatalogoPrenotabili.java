package it.univaq.esc.model.costi;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.Sport;
import it.univaq.esc.repository.ImpiantoRepository;
import it.univaq.esc.repository.PrenotabileDescrizioneRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Singleton
@Getter @Setter
public class CatalogoPrenotabili {
    
	@Setter(value = AccessLevel.PRIVATE)
    private List<PrenotabileDescrizione> catalogoPrenotabili;

    @Setter(value = AccessLevel.PRIVATE)
    private PrenotabileDescrizioneBuilder prenotabileDescrizioneBuilder;

    @Setter(value = AccessLevel.PRIVATE)
    private PrenotabileDescrizioneRepository prenotabileDescrizioneRepository;

    CatalogoPrenotabili(PrenotabileDescrizioneRepository prenotabileDescrizioneRepository, PrenotabileDescrizioneBuilder prenotabileDescrizioneBuilder){
        this.setPrenotabileDescrizioneBuilder(prenotabileDescrizioneBuilder);
        this.setPrenotabileDescrizioneRepository(prenotabileDescrizioneRepository);
        popola();
    }



    public PrenotabileDescrizione aggiungiPrenotabileACatalogo(PrenotabileDescrizione prenotabileDescrizioneDaAggiungere){
       this.getCatalogoPrenotabili().add(prenotabileDescrizioneDaAggiungere);
       return prenotabileDescrizioneDaAggiungere;
    }

    
    private void popola(){
        this.setCatalogoPrenotabili(this.getPrenotabileDescrizioneRepository().findAll());
    }

    public CatalogoPrenotabili avviaCreazionePrenotabile(Sport sport, String tipoPrenotazione, Integer sogliaMinimaPartecipanti, Integer sogliaMassimaPartecipanti){
        this.getPrenotabileDescrizioneBuilder().creaNuovaDescrizione()
        	.impostaSport(sport)
        	.impostaTipoPrenotazione(tipoPrenotazione)
        	.impostaSogliaMinimaPartecipanti(sogliaMinimaPartecipanti)
        	.impostaSogliaMassimaPartecipanti(sogliaMassimaPartecipanti);

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
