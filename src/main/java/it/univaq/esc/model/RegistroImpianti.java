package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.AssertFalse;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.OrarioAppuntamento;
import it.univaq.esc.repository.AppuntamentoRepository;
import it.univaq.esc.repository.ImpiantoRepository;
import it.univaq.esc.repository.ImpiantoSpecsRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Singleton
@Getter @Setter(value = AccessLevel.PRIVATE)
public class RegistroImpianti {

    
    private ImpiantoRepository impiantoRepository;
    private ImpiantoSpecsRepository impiantoSpecsRepository;
    private AppuntamentoRepository appuntamentoRepository;
    private List<Impianto> listaImpiantiPolisportiva = new ArrayList<Impianto>();
    private List<ImpiantoSpecs> listaSpecificheImpianto = new ArrayList<ImpiantoSpecs>();
    private RegistroSport registroSport;

    public RegistroImpianti(ImpiantoRepository impiantoRepository, AppuntamentoRepository appuntamentoRepository, ImpiantoSpecsRepository impiantoSpecsRepository, RegistroSport registroSport) {
    	this.setAppuntamentoRepository(appuntamentoRepository);
    	this.setImpiantoRepository(impiantoRepository);
    	setImpiantoSpecsRepository(impiantoSpecsRepository);
    	setRegistroSport(registroSport);
    	popola();
    }
    
    private void popola(){
    	setListaSpecificheImpianto(impiantoSpecsRepository.findAll());
        this.setListaImpiantiPolisportiva(getImpiantoRepository().findAll());
        for(Impianto impianto : this.getListaImpiantiPolisportiva()){
        	reimpostaSpecificheDell(impianto);
        	popolaCalendarioDell(impianto);
        }
                
    }

    private void reimpostaSpecificheDell(Impianto impianto) {
    	List<ImpiantoSpecs> specifiche = new ArrayList<ImpiantoSpecs>();
    	for(ImpiantoSpecs impiantoSpecs : impianto.getSpecificheImpianto()) {
    		specifiche.add(trovaSpecificaImpiantoNellaListaDiTutteLeSpecifiche(impiantoSpecs));
    	}
    	impianto.setSpecificheImpianto(specifiche);
    }
    
    private ImpiantoSpecs trovaSpecificaImpiantoNellaListaDiTutteLeSpecifiche(ImpiantoSpecs specificaImpianto) {
    	for(ImpiantoSpecs impiantoSpecs : listaSpecificheImpianto) {
    		if(impiantoSpecs.isEqual(specificaImpianto)) {
    			return impiantoSpecs;
    		}
    	}
    	return null;
    }
    
    private void popolaCalendarioDell(Impianto impianto) {
    	List<Appuntamento> appuntamentiImpianto = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : getAppuntamentoRepository().findAll()){
            if(impianto.isEqual(appuntamento.getStrutturaPrenotata())){
                appuntamentiImpianto.add(appuntamento);
            }
        }
        impianto.segnaInCalendarioLaListaDi(appuntamentiImpianto);
    }

    public void aggiungiImpianto(Impianto impiantoDaAggiungere) {
        getListaImpiantiPolisportiva().add(impiantoDaAggiungere);
        registraNellaPolisportivaEventualiNuoveSpecificheRelativeAll(impiantoDaAggiungere);       
        getImpiantoRepository().save(impiantoDaAggiungere);
    }
    
    private void registraNellaPolisportivaEventualiNuoveSpecificheRelativeAll(Impianto nuovoImpianto) {
    	for(ImpiantoSpecs specifica : nuovoImpianto.getSpecificheImpianto()) {
        	if(!esisteNelSistemaLa(specifica)) {
        		aggiungiNellaPolisportivaLa(specifica);
        	}
        }
    }
    
    private void aggiungiNellaPolisportivaLa(ImpiantoSpecs nuovaSpecificaImianto) {
    	listaSpecificheImpianto.add(nuovaSpecificaImianto);
		impiantoSpecsRepository.save(nuovaSpecificaImianto);
    }
    
   public boolean esisteNelSistemaLa(ImpiantoSpecs specificaImpianto) {
	   ImpiantoSpecs specifica = trovaSpecificaImpiantoNellaListaDiTutteLeSpecifiche(specificaImpianto);
	   if(specifica == null) {
		   return false;
	   }
	   return true;
   }

    public void rimuoviImpianto(Impianto impiantoDaRimuovere){
        getListaImpiantiPolisportiva().remove(impiantoDaRimuovere);
        getImpiantoRepository().delete(impiantoDaRimuovere);
    }

    public Impianto getImpiantoByID(Integer idImpianto){
        for(Impianto impianto : this.getListaImpiantiPolisportiva()){
            if(impianto.isSuoQuesto(idImpianto)){
                return impianto;
            }
        }
        return null;
    }
    
    public List<Impianto> filtraImpiantiDisponibiliPerOrario(OrarioAppuntamento orarioAppuntamento,
			List<Impianto> listaImpiantiDaFiltrare){
    	List<Impianto> listaImpiantiDisponibili = new ArrayList<Impianto>();

		for (Impianto impianto : listaImpiantiDaFiltrare) {
			if (impianto.isStrutturaLiberaNell(orarioAppuntamento)) {
				listaImpiantiDisponibili.add(impianto);
			}
		}

		return listaImpiantiDisponibili;
    }
    
    public List<Impianto> filtraImpiantiPerSport(Sport sport, List<Impianto> listaImpiantiDaFiltrare) {
		List<Impianto> impianti = new ArrayList<Impianto>();
		for (Impianto impianto : listaImpiantiDaFiltrare) {
			for (Sport sportPraticabile : impianto.getSportPraticabili()) {
				if (sportPraticabile.isEqual(sport)) {
					impianti.add(impianto);
				}
			}

		}
		return impianti;
	}
    
    public ImpiantoSpecs ottienSpecificaImpiantoDa(String pavimentazione, String sport) {
    	if(esisteSpecificaImpiantoCon(pavimentazione, sport)) {
    		ImpiantoSpecs specifica = trovaSpecificaImpiantoDa(pavimentazione, sport);
    		return specifica;
    	}
    	else {
    		ImpiantoSpecs nuovaSpecifica = creaSpecificaImpiantoDa(pavimentazione, sport);
    		return nuovaSpecifica;
    	}
    	
    }
    
    
    private boolean esisteSpecificaImpiantoCon(String pavimentazione, String nomeSport) {
    	ImpiantoSpecs specifica = trovaSpecificaImpiantoDa(pavimentazione, nomeSport);
    	if(specifica == null) {
    		return false;
    	}
    	return true;
    }
    
    private ImpiantoSpecs trovaSpecificaImpiantoDa(String pavimentazione, String sport) {
    	for(ImpiantoSpecs impiantoSpecs : listaSpecificheImpianto) {
    		if(impiantoSpecs.haQuesta(pavimentazione) && impiantoSpecs.haQuesto(sport)) {
    			return impiantoSpecs;
    		}
    	}
    	return null;
    }
    
    private ImpiantoSpecs creaSpecificaImpiantoDa(String tipoPavimentazione, String nomeSport) {
    	ImpiantoSpecs nuovaSpecifica = new ImpiantoSpecs();
    	nuovaSpecifica.setSportPraticabile(getRegistroSport().getSportByNome(nomeSport));
    	nuovaSpecifica.setTipoPavimentazione(Pavimentazione.trovaPavimentazioneDa(tipoPavimentazione));
    	return nuovaSpecifica;
    }
    
}