package it.univaq.esc.model.catalogoECosti;

import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.Sport;
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

    public CatalogoPrenotabili nuovoPrenotabile_avviaCreazione(Sport sport, String tipoPrenotazione, Integer sogliaMinimaPartecipanti, Integer sogliaMassimaPartecipanti){
        this.getPrenotabileDescrizioneBuilder().creaNuovaDescrizione()
        	.impostaSport(sport)
        	.impostaTipoPrenotazione(tipoPrenotazione)
        	.impostaSogliaMinimaPartecipanti(sogliaMinimaPartecipanti)
        	.impostaSogliaMassimaPartecipanti(sogliaMassimaPartecipanti);

        return this;
    }
    
    public CatalogoPrenotabili nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente() {
    	this.getPrenotabileDescrizioneBuilder().impostaModalitaPrenotazioneComeSingoloUtente();
    	return this;
    }
    
    
    public CatalogoPrenotabili nuovoPrenotabile_impostaModalitaPrenotazioneComeSquadra() {
		this.getPrenotabileDescrizioneBuilder().impostaModalitaPrenotazioneComeSquadra();
		return this;
	}

    public CatalogoPrenotabili nuovoPrenotabile_impostaCostoOrario(Float costo){
        this.getPrenotabileDescrizioneBuilder().impostaCostoOrario(costo);

        return this;
    }

    public CatalogoPrenotabili nuovoPrenotabile_impostaCostoUnaTantum(Float costo){
        this.getPrenotabileDescrizioneBuilder().impostaCostoUnaTantum(costo);

        return this;
    }

    public CatalogoPrenotabili nuovoPrenotabile_impostaCostoPavimentazione(Float costo, String tipoPavimentazione){
        this.getPrenotabileDescrizioneBuilder().impostaCostoPavimentazione(costo, tipoPavimentazione);

        return this;
    }

    public PrenotabileDescrizione nuovoPrenotabile_salvaPrenotabileInCreazione(){
       return this.aggiungiPrenotabileACatalogo(this.getPrenotabileDescrizioneRepository().save(this.getPrenotabileDescrizioneBuilder().build()));

    }


    public PrenotabileDescrizione getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(String tipoPrenotazione, Sport sport, String modalitaPrenotazione){
        for(PrenotabileDescrizione desc : this.getCatalogoPrenotabili()){
            if(desc.getSportAssociato().getNome().equals(sport.getNome()) && desc.getTipoPrenotazione().equals(tipoPrenotazione) && desc.getModalitaPrenotazione().equals(modalitaPrenotazione)){
                return desc;
            }
        }
        return null;
    }


}
