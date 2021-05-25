package it.univaq.esc.model.prenotazioni;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.event.InternalFrameAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.prenotazioni.utility.FetchDatiPrenotazioniAppuntamentiFunctionsUtlis;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.repository.AppuntamentoRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Singleton
@Setter @Getter
public class RegistroAppuntamenti {

	@Setter(value = AccessLevel.PRIVATE)
    private AppuntamentoRepository appuntamentoRepository;
    
	@Setter(value = AccessLevel.PRIVATE)
    private List<Appuntamento> listaAppuntamenti = new ArrayList<Appuntamento>();


    
    public RegistroAppuntamenti(AppuntamentoRepository appuntamentoRepository){
    	this.setAppuntamentoRepository(appuntamentoRepository);
    }

    @PostConstruct
    private void popola(){
        setListaAppuntamenti(getAppuntamentoRepository().findAll());
        for(Appuntamento appuntamento : this.getListaAppuntamenti()){
            CalcolatoreCostoBase calcBase = new CalcolatoreCostoBase();
            CalcolatoreCostoComposito calcComposito = new CalcolatoreCostoComposito();
            calcComposito.aggiungiStrategiaCosto(calcBase);
            appuntamento.setCalcolatoreCosto(calcComposito);
        }
        
    }


   
    public void salvaAppuntamento(Appuntamento appuntamento){
        this.getListaAppuntamenti().add(appuntamento);
        this.getAppuntamentoRepository().save(appuntamento);
    }

    public void aggiornaAppuntamento(Appuntamento appuntamento){
        this.getAppuntamentoRepository().save(appuntamento);
    }


    public void salvaListaAppuntamenti(List<Appuntamento> listaAppuntamenti){
        this.getListaAppuntamenti().addAll(listaAppuntamenti);
        this.getAppuntamentoRepository().saveAll(listaAppuntamenti);
    }



    public Appuntamento getAppuntamentoBySpecificaAssociata(PrenotazioneSpecs prenotazioneSpecs){
        return this.getAppuntamentoRepository().findByPrenotazioneSpecsAppuntamento_Id(prenotazioneSpecs.getId());
    }

   
    public List<Appuntamento> getAppuntamentiSottoscrivibiliPerTipo(String tipoPrenotazione, UtentePolisportivaAbstract utentePerCuiTrovareAppuntamenti){	
        List<Appuntamento> appuntamentiFiltrati = this.filtraAppuntamentiPerDataOra(this.getListaAppuntamenti(), LocalDateTime.now());
        appuntamentiFiltrati = this.filtraAppuntamentiPerTipoPrenotazione(appuntamentiFiltrati, tipoPrenotazione);
        appuntamentiFiltrati = this.filtraAppuntamentiPending(appuntamentiFiltrati);
        appuntamentiFiltrati = this.escludiAppuntamentiPerUtenteCreatore(appuntamentiFiltrati, utentePerCuiTrovareAppuntamenti);
        appuntamentiFiltrati = this.escludiAppuntamentiPerPartecipante(appuntamentiFiltrati, utentePerCuiTrovareAppuntamenti);

        return appuntamentiFiltrati;
    }

    public List<Appuntamento> getAppuntamentiPerPartecipanteNonCreatore(UtentePolisportivaAbstract partecipanteNonCreatore){
        List<Appuntamento> appuntamenti = this.getListaAppuntamenti();
        appuntamenti = this.filtraAppuntamentiPerPartecipante(appuntamenti, partecipanteNonCreatore);
        appuntamenti = this.escludiAppuntamentiPerUtenteCreatore(appuntamenti, partecipanteNonCreatore);

        return appuntamenti;
    }


    private List<Appuntamento> filtraAppuntamentiPerDataOra(List<Appuntamento> listaAppuntamentiDaFiltrare, LocalDateTime dataOra){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(appuntamento.getDataOraInizioAppuntamento().isAfter(dataOra)){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }

    private List<Appuntamento> filtraAppuntamentiPerTipoPrenotazione(List<Appuntamento> listaAppuntamentiDaFiltrare, String tipoPrenotazione){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(appuntamento.getTipoPrenotazione().equals(tipoPrenotazione)){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }

    private List<Appuntamento> filtraAppuntamentiPending(List<Appuntamento> listaAppuntamentiDaFiltrare){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(appuntamento.isPending()){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }

    private List<Appuntamento> filtraAppuntamentiPerUtenteCreatore(List<Appuntamento> listaAppuntamentiDaFiltrare, UtentePolisportivaAbstract utenteCreatore){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(((String)appuntamento.creatoDa().getProprieta().get("email")).equals((String)utenteCreatore.getProprieta().get("email"))){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }
    
    public List<Appuntamento> escludiAppuntamentiDiCorsi(List<Appuntamento> listaAppuntamentiDaFiltrare){
    	List<Appuntamento> appuntamentiNonCorsi = new ArrayList<Appuntamento>();
    	for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
    		if(!appuntamento.appartieneA().equals(TipiPrenotazione.CORSO.toString())){
    			appuntamentiNonCorsi.add(appuntamento);
    		}
    	}
    	
    	return appuntamentiNonCorsi;
    }
    
    public List<Appuntamento> filtraAppuntamentiDiCorsi(List<Appuntamento> listaAppuntamentiDaFiltrare){
    	List<Appuntamento> appuntamentiCorsi = new ArrayList<Appuntamento>();
    	for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
    		if(appuntamento.appartieneA().equals(TipiPrenotazione.CORSO.toString())){
    			appuntamentiCorsi.add(appuntamento);
    		}
    	}
    	
    	return appuntamentiCorsi;
    }

    private List<Appuntamento> escludiAppuntamentiPerUtenteCreatore(List<Appuntamento> listaAppuntamentiDaFiltrare, UtentePolisportivaAbstract utenteCreatore){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(!((String)appuntamento.creatoDa().getProprieta().get("email")).equals((String)utenteCreatore.getProprieta().get("email"))){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }

    private List<Appuntamento> escludiAppuntamentiPerPartecipante(List<Appuntamento> listaAppuntamentiDaFiltrare, UtentePolisportivaAbstract utenteDiCuiVerificarePartecipazione){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(!appuntamento.utenteIsPartecipante(utenteDiCuiVerificarePartecipazione)){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }

    public List<Appuntamento> filtraAppuntamentiPerPartecipante(List<Appuntamento> listaAppuntamentiDaFiltrare, UtentePolisportivaAbstract utenteDiCuiVerificarePartecipazione){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(appuntamento.utenteIsPartecipante(utenteDiCuiVerificarePartecipazione)){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }
    
    public Appuntamento getAppuntamentoById(Integer idAppuntamento){
        for(Appuntamento appuntamento: this.getListaAppuntamenti()){
            if(appuntamento.getIdAppuntamento() == idAppuntamento){
                return appuntamento;
            }
        }
        return null;
    }
    
    public List<Appuntamento> getAppuntamentiByPrenotazioneId(Integer idPrenotazione){
    	List<Appuntamento> appuntamenti = new ArrayList<Appuntamento>();
    	for(Appuntamento appuntamento : this.getListaAppuntamenti()) {
    		if(appuntamento.getIdPrenotazione() == idPrenotazione) {
    			appuntamenti.add(appuntamento);
    		}
    	}
    	return appuntamenti;
    }
    
    public List<Prenotazione> getPrenotazioniAssociateAListaAppuntamenti(List<Appuntamento> listaAppuntamenti){
    	return FetchDatiPrenotazioniAppuntamentiFunctionsUtlis.getPrenotazioniAssociateAListaAppuntamenti(listaAppuntamenti);
    }

}
