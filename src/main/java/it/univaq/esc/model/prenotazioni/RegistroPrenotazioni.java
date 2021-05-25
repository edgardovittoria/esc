package it.univaq.esc.model.prenotazioni;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import groovy.lang.Singleton;
import it.univaq.esc.model.prenotazioni.utility.FetchDatiPrenotazioniAppuntamentiFunctionsUtlis;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.repository.PrenotazioneRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.description.ModifierReviewable.OfAbstraction;


@Component
@Singleton
@Getter @Setter
public class RegistroPrenotazioni {

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
    private PrenotazioneRepository prenotazioneRepository;
	@Setter(value = AccessLevel.PRIVATE)
    private List<Prenotazione> prenotazioniRegistrate = new ArrayList<Prenotazione>();
	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private RegistroAppuntamenti registroAppuntamenti;
    

    public RegistroPrenotazioni(PrenotazioneRepository prenotazioneRepository, RegistroAppuntamenti registroAppuntamenti){
    	this.setPrenotazioneRepository(prenotazioneRepository);
    	this.setRegistroAppuntamenti(registroAppuntamenti);
    }


    @PostConstruct
    public void popola(){
    	setPrenotazioniRegistrate(getRegistroAppuntamenti().getPrenotazioniAssociateAListaAppuntamenti(getRegistroAppuntamenti().getListaAppuntamenti()));
    	
        FetchDatiPrenotazioniAppuntamentiFunctionsUtlis.rimuoviDoppioniSpecificheLezioniInPrenotazioneCorso(this.getPrenotazioniRegistrate());
    }

    public void aggiungiPrenotazione(Prenotazione prenotazioneDaAggiungere) {
        getPrenotazioniRegistrate().add(prenotazioneDaAggiungere);
        this.getPrenotazioneRepository().save(prenotazioneDaAggiungere);
       
    }

    

    public List<Prenotazione> getPrenotazioniByEmailSportivo(String email){
        List<Prenotazione> prenotazioniSportivo = new ArrayList<Prenotazione>();
        for (Prenotazione prenotazione : this.getPrenotazioniRegistrate()){
            if(((String)prenotazione.getSportivoPrenotante().getProprieta().get("email")).equals(email)){
                prenotazioniSportivo.add(prenotazione);
            }
        }
        return prenotazioniSportivo;
    }

    public void cancellaPrenotazione(Prenotazione prenotazioneDaCancellare){
        //appuntamentoRepository.deleteAll(prenotazioneDaCancellare.getListaAppuntamenti());
        getPrenotazioniRegistrate().remove(prenotazioneDaCancellare);
       getPrenotazioneRepository().delete(prenotazioneDaCancellare);
        
    }

    

    public int getLastIdPrenotazione() {
        if(getPrenotazioniRegistrate().isEmpty()){
            return 0;
        }
        else{
        Prenotazione lastPrenotazione = getPrenotazioniRegistrate().get(getPrenotazioniRegistrate().size()-1);
        return lastPrenotazione.getIdPrenotazione();}
    }

    

    public List<Prenotazione> filtraPrenotazioniPerTipo(List<Prenotazione> listaPrenotazioniDaFiltrare, String tipoPrenotazione){

        List<Prenotazione> prenotazioniFiltrate = new ArrayList<Prenotazione>();
        for(Prenotazione prenotazione : listaPrenotazioniDaFiltrare){
            if(prenotazione.getTipoPrenotazione().equals(tipoPrenotazione)){
                prenotazioniFiltrate.add(prenotazione);
            }
        }
        return prenotazioniFiltrate;
    }
    
    public List<Prenotazione> escludiPrenotazioniPerTipo(List<Prenotazione> listaPrenotazioniDaFiltrare, String tipoPrenotazione){

        List<Prenotazione> prenotazioniFiltrate = new ArrayList<Prenotazione>();
        for(Prenotazione prenotazione : listaPrenotazioniDaFiltrare){
            if(!prenotazione.getTipoPrenotazione().equals(tipoPrenotazione)){
                prenotazioniFiltrate.add(prenotazione);
            }
        }
        return prenotazioniFiltrate;
    }
    
    public Prenotazione getPrenotazioneById(Integer idPrenotazione) {
    	for(Prenotazione prenotazione : this.getPrenotazioniRegistrate()) {
    		if(prenotazione.getIdPrenotazione() == idPrenotazione) {
    			return prenotazione;
    		}
    	}
    	return null;
    }
    
    
    

}