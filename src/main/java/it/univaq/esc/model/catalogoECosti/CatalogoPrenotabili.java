package it.univaq.esc.model.catalogoECosti;

import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.Costo;
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

    public PrenotabileDescrizioneBuilder avviaCreazioneNuovoPrenotabile() {
    	 return getPrenotabileDescrizioneBuilder().creaNuovaDescrizione();
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
    
    public PrenotabileDescrizione getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(String tipoPrenotazione, Sport sport, ModalitaPrenotazione modalitaPrenotazione){
        for(PrenotabileDescrizione desc : this.getCatalogoPrenotabili()){
            if(desc.getSportAssociato().getNome().equals(sport.getNome()) && desc.getTipoPrenotazione().equals(tipoPrenotazione) && desc.getModalitaPrenotazione().isEqual(modalitaPrenotazione)){
                return desc;
            }
        }
        return null;
    }


}
