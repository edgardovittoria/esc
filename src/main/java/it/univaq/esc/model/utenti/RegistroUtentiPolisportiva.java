package it.univaq.esc.model.utenti;

import static org.hamcrest.CoreMatchers.nullValue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.xml.fastinfoset.util.LocalNameQualifiedNamesMap;

import groovy.lang.Singleton;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.repository.UtentePolisportivaAbstractRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


@Component
@Singleton
@Getter @Setter
public class RegistroUtentiPolisportiva {

	@Setter(value = AccessLevel.PRIVATE)
	@Getter(value = AccessLevel.PRIVATE)
    private UtentePolisportivaAbstractRepository utentiRepository; 
    
    @Setter(value = AccessLevel.PRIVATE)
    private List<UtentePolisportivaAbstract> listaUtentiPolisportiva = new ArrayList<UtentePolisportivaAbstract>();
    
    @Setter(value = AccessLevel.PRIVATE)
	@Getter(value = AccessLevel.PRIVATE)
    private RegistroAppuntamenti registroAppuntamenti;


    public RegistroUtentiPolisportiva(UtentePolisportivaAbstractRepository utentePolisportivaAbstractRepository, RegistroAppuntamenti registroAppuntamenti) {
    	this.setUtentiRepository(utentePolisportivaAbstractRepository);
    	setRegistroAppuntamenti(registroAppuntamenti);
    	popola();
    }

    /**
     * Viene invocato subito dopo l'istanziazione del registro utenti, per popolare quest'ultimo 
     * con tutti gli utenti presenti nel database
     */
    public void popola(){
        this.setListaUtentiPolisportiva(this.utentiRepository.findAll());
        if(!this.getListaUtentiPolisportiva().isEmpty()){
        List<Integer> listaindiciDaEliminare = new ArrayList<Integer>();
        for(UtentePolisportivaAbstract utente : this.getListaUtentiPolisportiva().subList(0, this.getListaUtentiPolisportiva().size() - 1)){
            int j=0;
            for(UtentePolisportivaAbstract utenteSuccessivo : this.getListaUtentiPolisportiva().subList(this.getListaUtentiPolisportiva().indexOf(utente)+1, this.getListaUtentiPolisportiva().size())){
                if(utente.getProprieta().get("email").equals(utenteSuccessivo.getProprieta().get("email"))){
                    j++;
                }
            }
            if(j>0){
                listaindiciDaEliminare.add(this.getListaUtentiPolisportiva().indexOf(utente));
            }

        }
        List<UtentePolisportivaAbstract> utentiDaEliminare = new ArrayList<UtentePolisportivaAbstract>();
        for(Integer index : listaindiciDaEliminare){
            utentiDaEliminare.add(this.getListaUtentiPolisportiva().get(index));
        }
        this.getListaUtentiPolisportiva().removeAll(utentiDaEliminare);
    }
        /*
         * Popoliamo il calendario degli sportivi
         */
        for(UtentePolisportivaAbstract sportivo : getListaUtentiByRuolo(TipoRuolo.SPORTIVO)){
        	List<Appuntamento> listaAppuntamenti = getRegistroAppuntamenti().getAppuntamentiPerPartecipante(sportivo);
        	if(!listaAppuntamenti.isEmpty()) {
        		Calendario calendario = new Calendario();
        		calendario.setListaAppuntamenti(listaAppuntamenti);
        		Map<String, Object> mappaDatiSportivo = new HashMap<String, Object>();
        		mappaDatiSportivo.put("calendarioAppuntamentiSportivo", calendario);
        		sportivo.setProprieta(mappaDatiSportivo);
        	}
         }
        
        /*
         * Popoliamo il calendario degli istruttori
         */
        for(UtentePolisportivaAbstract istruttore : getListaUtentiByRuolo(TipoRuolo.ISTRUTTORE)){
        	List<Appuntamento> listaLezioni = getRegistroAppuntamenti().getListaLezioniPerIstruttore(istruttore);
        	if(!listaLezioni.isEmpty()) {
        		Calendario calendarioIstruttore = new Calendario();
        		calendarioIstruttore.setListaAppuntamenti(listaLezioni);
        		Map<String, Object> mappaDatiIstruttore = new HashMap<String, Object>();
        		mappaDatiIstruttore.put("calendarioLezioni", calendarioIstruttore);
        		istruttore.setProprieta(mappaDatiIstruttore);
        	}
        }
        
        /*
         * Popoliamo il calendario dei manutentori
         */
        for(UtentePolisportivaAbstract manutentore : getListaUtentiByRuolo(TipoRuolo.MANUTENTORE)){
        	List<Appuntamento> listaAppuntamenti = getRegistroAppuntamenti().getListaAppuntamentiManutentore(manutentore);
        	if(!listaAppuntamenti.isEmpty()) {
        		Calendario calendarioManutentore = new Calendario();
        		calendarioManutentore.setListaAppuntamenti(listaAppuntamenti);
        		Map<String, Object> mappaDatiManutentore = new HashMap<String, Object>();
        		mappaDatiManutentore.put("calendarioManutentore", calendarioManutentore);
        		manutentore.setProprieta(mappaDatiManutentore);
        	}
        }
        
    }
    

    /**
     * Registra l'utente passato nel sistema della polisportiva (registro e database)
     * @param utente utente da registrare
     */
    public void registraUtente(UtentePolisportivaAbstract utente){
        getListaUtentiPolisportiva().add(utente);
        utentiRepository.save(utente);
    }

    /**
     * Restituisce l'utente associato alla mail passata come parametro
     * @param emailUtente email dell'utente da ricercare
     * @return l'utente associato alla email passata come parametro, se registrato
     */
    public UtentePolisportivaAbstract getUtenteByEmail(String emailUtente){
        for(UtentePolisportivaAbstract sportivo : getListaUtentiPolisportiva()){
            if(((String)sportivo.getProprieta().get("email")).equals(emailUtente)){
                return sportivo;
            }
        }
        return null;
    }


    /**
     * Ritorna una lista degli utenti che hanno nella polisportiva il ruolo passato come parametro
     * @param ruolo ruolo che devono avere gli utenti ricercati
     * @return
     */
    public List<UtentePolisportivaAbstract> getListaUtentiByRuolo(TipoRuolo ruolo){
        List<UtentePolisportivaAbstract> listaUtentiByRuolo = new ArrayList<UtentePolisportivaAbstract>();
        for(UtentePolisportivaAbstract utente : this.getListaUtentiPolisportiva()){
            if(utente.getRuoliUtentePolisportiva().contains(ruolo.toString())){
                listaUtentiByRuolo.add(utente);
            }
        }
        return listaUtentiByRuolo;
    }

    /**
     * Elimina utente polisportiva passato come parametro dal registro e dal database.
     * @param utente utente da eliminare
     */
    public void eliminaUtente(UtentePolisportivaAbstract utente){
        this.getListaUtentiPolisportiva().remove(utente);
        this.getUtentiRepository().delete(utente);
    }


    /**
     * Elimina utente polisportiva associato alla email passata come parametro
     * @param emailUtente email dell'utente da eliminare
     */
    public void eliminaUtente(String emailUtente){
        this.eliminaUtente(this.getUtenteByEmail(emailUtente));
    }


    /**
     * Restituisce la lista di tutti gli istruttori per un determinato sport
     * @param sportDiCuiTrovareGliIstruttori Sport di cui trovare gli istruttori
     * @return lista di tutti gli istruttori associati allo sport passato come parametro
     */
    public List<UtentePolisportivaAbstract> getIstruttoriPerSport(Sport sportDiCuiTrovareGliIstruttori){
        return this.filtraIstruttoriPerSport(this.getListaUtentiByRuolo(TipoRuolo.ISTRUTTORE), sportDiCuiTrovareGliIstruttori);
    }
    
    
    
    /**
     * Restituisce una lista dei soli isitruttori associati ad un determinato sport passato come parametro, a partire da una
     * lista di istruttori anch'essa passata come parametro.
     * @param listaIstruttoriDaFiltrare lista degli istruttori da filtrare.
     * @param sportDiCuiTrovareGliIstruttori spsort in base al quale filtrare gli istruttori.
     * @return lista dei soli istruttori, appartenenti alla lista passata come parametro, associati allo sport passato come parametro.
     */
    public List<UtentePolisportivaAbstract> filtraIstruttoriPerSport(List<UtentePolisportivaAbstract> listaIstruttoriDaFiltrare, Sport sportDiCuiTrovareGliIstruttori){
    	List<UtentePolisportivaAbstract> istruttori = new ArrayList<UtentePolisportivaAbstract>();
    	for(UtentePolisportivaAbstract istruttore : listaIstruttoriDaFiltrare){
            for(Sport sportInsegnato : (List<Sport>)istruttore.getProprieta().get("sportInsegnati")){
                if (sportInsegnato.getNome().equals(sportDiCuiTrovareGliIstruttori.getNome())) {
                    istruttori.add(istruttore);
                }
            }
            
        }
        return istruttori;
    }
    
    
    /**
     * Filtra una lista di istruttori passata come parametro, sulla base di un calendario passato come parametro.
     * Restituisce i soli istruttori della lista liberi nelle date del calendario passato come parametro.
     * @param listaIstruttoriDaFiltrare lista degli istruttori da filtrare.
     * @param calendarioPerCuiFiltrareGliIstruttori calendario in base al quale filtrare gli istruttori.
     * @return la lista dei soli istruttori liberi nelle date del calendario passato come parametro.
     */
    public  List<UtentePolisportivaAbstract> filtraIstruttorePerCalendario(List<UtentePolisportivaAbstract> listaIstruttoriDaFiltrare, Calendario calendarioPerCuiFiltrareGliIstruttori) {
    	List<UtentePolisportivaAbstract> istruttori = new ArrayList<UtentePolisportivaAbstract>();
		for (UtentePolisportivaAbstract istruttore : listaIstruttoriDaFiltrare) {
			if (!((Calendario) istruttore.getProprieta().get("calendarioLezioni")).sovrapponeA(calendarioPerCuiFiltrareGliIstruttori)) {
				istruttori.add(istruttore);
			}
		}
		return istruttori;
	}
    
    /**
     * Filtra una lista di istruttori passata come parametro, sulla base di un intervallo di tempo passato come parametro.
     * Restituisce i soli istruttori della lista liberi in quel determinato intervallo di tempo.
     * @param listaIstruttoriDaFiltrare lista degli istruttori da filtrare.
     * @param oraInizioOrarioPerCuiFiltrare data e ora di inizio dell'intervallo di tempo per cui filtrare.
     * @param oraFineOrarioPerCuiFiltrare dat e ora di fine dell'intervallo di tempo per cui filtrare.
     * @return la lista dei soli istruttori della lista di partenza, liberi nell'intervallo di tempo passato come parametro.
     */
    public  List<UtentePolisportivaAbstract> filtraIstruttorePerOrario(List<UtentePolisportivaAbstract> listaIstruttoriDaFiltrare, LocalDateTime oraInizioOrarioPerCuiFiltrare, LocalDateTime oraFineOrarioPerCuiFiltrare) {
    	List<UtentePolisportivaAbstract> istruttori = new ArrayList<UtentePolisportivaAbstract>();
		for (UtentePolisportivaAbstract istruttore : listaIstruttoriDaFiltrare) {
			if (!((Calendario) istruttore.getProprieta().get("calendarioLezioni")).sovrapponeA(oraInizioOrarioPerCuiFiltrare, oraFineOrarioPerCuiFiltrare)) {
				istruttori.add(istruttore);
			}
		}
		return istruttori;
	}


    public void aggiornaCalendarioIstruttore(Calendario calendarioDaUnire, UtentePolisportivaAbstract istruttore){
        Calendario calendarioPrecedente = (Calendario)istruttore.getProprieta().get("calendarioLezioni");
        calendarioPrecedente.unisciCalendario(calendarioDaUnire);
        Map<String, Object> mappaProprieta = new HashMap<String, Object>();
        mappaProprieta.put("calendarioLezioni", calendarioPrecedente);
        istruttore.setProprieta(mappaProprieta);
    }
    
    public void aggiornaCalendarioManutentore(Calendario calendarioDaUnire, UtentePolisportivaAbstract manutentore){
        Calendario calendarioPrecedente = (Calendario)manutentore.getProprieta().get("calendarioManutentore");
        calendarioPrecedente.unisciCalendario(calendarioDaUnire);
        Map<String, Object> mappaProprieta = new HashMap<String, Object>();
        mappaProprieta.put("calendarioManutentore", calendarioPrecedente);
        manutentore.setProprieta(mappaProprieta);
    }
    
    public UtentePolisportivaAbstract getManutentoreLibero(Calendario calendarioAppuntamento) {
    	for(UtentePolisportivaAbstract manutentore : getListaUtentiByRuolo(TipoRuolo.MANUTENTORE)) {
    		Calendario calendario = (Calendario)manutentore.getProprieta().get("calendarioManutentore");
    		if(!calendario.sovrapponeA(calendarioAppuntamento)) {
    			return manutentore;
    		}
    	}
    	return null;
    }

}