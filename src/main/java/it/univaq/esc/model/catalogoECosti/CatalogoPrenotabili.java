package it.univaq.esc.model.catalogoECosti;

import groovy.lang.Singleton;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.TipoPrenotazione;
import it.univaq.esc.repository.PrenotabileDescrizioneRepository;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Singleton
@Getter @Setter(value = AccessLevel.PRIVATE)
@DependsOn("beanUtil")
public class CatalogoPrenotabili {
    
    private List<PrenotabileDescrizione> catalogoPrenotabili;
    private PrenotabileDescrizioneRepository prenotabileDescrizioneRepository;
    private FactoryPrenotabileDescrizioneBuilder factoryPrenotabileDescrizioneBuilder;
    

    CatalogoPrenotabili(PrenotabileDescrizioneRepository prenotabileDescrizioneRepository, FactoryPrenotabileDescrizioneBuilder factoryPrenotabileDescrizioneBuilder){
        this.setPrenotabileDescrizioneRepository(prenotabileDescrizioneRepository);
        setFactoryPrenotabileDescrizioneBuilder(factoryPrenotabileDescrizioneBuilder);
        popola();
    }



    public PrenotabileDescrizione aggiungiPrenotabileACatalogo(PrenotabileDescrizione prenotabileDescrizioneDaAggiungere){
       this.getCatalogoPrenotabili().add(prenotabileDescrizioneDaAggiungere);
       return prenotabileDescrizioneDaAggiungere;
    }

    
    private void popola(){
        this.setCatalogoPrenotabili(this.getPrenotabileDescrizioneRepository().findAll());
    }
    
    public void eliminaPrenotabileDescrizione(PrenotabileDescrizione prenotabileDescrizione) {
    	Integer indiceDaCancellare = 0;
    	for(PrenotabileDescrizione prenDescrizione : getCatalogoPrenotabili()) {
    		if (prenDescrizione.isEqual(prenotabileDescrizione)) {
				indiceDaCancellare = getCatalogoPrenotabili().indexOf(prenDescrizione);
			}
    	}
    	getCatalogoPrenotabili().remove(indiceDaCancellare);
    	getPrenotabileDescrizioneRepository().delete(prenotabileDescrizione);
    } 

    public PrenotabileDescrizioneBuilder avviaCreazioneNuovoPrenotabile(String tipoPrenotazione) {
    	 return getFactoryPrenotabileDescrizioneBuilder().creaPrenotabileDescrizioneBuilderInBaseAl(tipoPrenotazione).creaNuovaDescrizione();
    }
    

    public void salvaPrenotabileDescrizioneSulDatabase(PrenotabileDescrizione prenotabileDescrizione) {
    	getPrenotabileDescrizioneRepository().save(prenotabileDescrizione);
    }

    public PrenotabileDescrizione trovaPrenotabileDescrizioneDalla(String descrizione) {
    	for(PrenotabileDescrizione desc : getCatalogoPrenotabili()) {
    		if(desc.getDescrizione().equals(descrizione)) {
    			return desc;
    		}
    	}
    	return null;
    }
    
    public PrenotabileDescrizione getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(TipoPrenotazione tipoPrenotazione, Sport sport, ModalitaPrenotazione modalitaPrenotazione){
        for(PrenotabileDescrizione desc : this.getCatalogoPrenotabili()){
            if(desc.getSportAssociato().getNome().equals(sport.getNome()) && desc.getTipoPrenotazione().isEqual(tipoPrenotazione) && desc.getModalitaPrenotazione().isEqual(modalitaPrenotazione)){
                return desc;
            }
        }
        return null;
    }
}
