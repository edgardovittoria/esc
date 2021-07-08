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
    	for(PrenotabileDescrizione prenDescrizione : getCatalogoPrenotabili()) {
    		if (prenDescrizione.isEqual(prenotabileDescrizione)) {
				getCatalogoPrenotabili().remove(prenotabileDescrizione);
			}
    	}
    	getPrenotabileDescrizioneRepository().delete(prenotabileDescrizione);
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

    public CatalogoPrenotabili nuovoPrenotabile_impostaCostoOrario(Costo costo){
        this.getPrenotabileDescrizioneBuilder().impostaCostoOrario(costo);

        return this;
    }

    public CatalogoPrenotabili nuovoPrenotabile_impostaCostoUnaTantum(Costo costo){
        this.getPrenotabileDescrizioneBuilder().impostaCostoUnaTantum(costo);

        return this;
    }
    
    public CatalogoPrenotabili nuovoPrenotabile_impostaDescrizione(String descrizioneEvento) {
    	getPrenotabileDescrizioneBuilder().impostaDescrizione(descrizioneEvento);
    	return this;
    }

    public CatalogoPrenotabili nuovoPrenotabile_impostaCostoPavimentazione(Costo costo, String tipoPavimentazione){
        this.getPrenotabileDescrizioneBuilder().impostaCostoPavimentazione(costo, tipoPavimentazione);

        return this;
    }

    public PrenotabileDescrizione nuovoPrenotabile_salvaPrenotabileInCreazioneNelCatalogo(){
       return this.aggiungiPrenotabileACatalogo(this.getPrenotabileDescrizioneBuilder().build());

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
    
    public PrenotabileDescrizione getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(String tipoPrenotazione, Sport sport, String modalitaPrenotazione){
        for(PrenotabileDescrizione desc : this.getCatalogoPrenotabili()){
            if(desc.getSportAssociato().getNome().equals(sport.getNome()) && desc.getTipoPrenotazione().equals(tipoPrenotazione) && desc.getModalitaPrenotazione().equals(modalitaPrenotazione)){
                return desc;
            }
        }
        return null;
    }


}
