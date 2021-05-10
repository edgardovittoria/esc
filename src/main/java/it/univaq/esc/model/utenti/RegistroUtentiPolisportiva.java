package it.univaq.esc.model.utenti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Sport;
import it.univaq.esc.repository.UtentePolisportivaAbstractRepository;


@Component
@Singleton
public class RegistroUtentiPolisportiva {

    @Autowired
    private UtentePolisportivaAbstractRepository utentiRepository;
    
    
    private List<UtentePolisportivaAbstract> registroSportivi = new ArrayList<UtentePolisportivaAbstract>();


    public RegistroUtentiPolisportiva() {}

    /**
     * Viene invocato subito dopo l'istanziazione del registro utenti, per popolare quest'ultimo 
     * con tutti gli utenti presenti nel database
     */
    @PostConstruct
    public void popola(){
        this.getListaUtenti().addAll(this.utentiRepository.findAll());
        if(!this.getListaUtenti().isEmpty()){
        List<Integer> listaindiciDaEliminare = new ArrayList<Integer>();
        for(UtentePolisportivaAbstract utente : this.getListaUtenti().subList(0, this.getListaUtenti().size() - 1)){
            int j=0;
            for(UtentePolisportivaAbstract utenteSuccessivo : this.getListaUtenti().subList(this.getListaUtenti().indexOf(utente)+1, this.getListaUtenti().size())){
                if(utente.getProprieta().get("email").equals(utenteSuccessivo.getProprieta().get("email"))){
                    j++;
                }
            }
            if(j>0){
                listaindiciDaEliminare.add(this.getListaUtenti().indexOf(utente));
            }

        }
        List<UtentePolisportivaAbstract> utentiDaEliminare = new ArrayList<UtentePolisportivaAbstract>();
        for(Integer index : listaindiciDaEliminare){
            utentiDaEliminare.add(this.getListaUtenti().get(index));
        }
        this.getListaUtenti().removeAll(utentiDaEliminare);
    }
        
    }


    /**
     * Ritorna la lista di tutti gli utenti registrati alla polisportiva
     * @return la lista degli utenti registrati alla polisportiva
     */
    public List<UtentePolisportivaAbstract> getListaUtenti() {
        return this.registroSportivi;
    }


    /**
     * Restituisce il repository degli utenti per la loro gestione nel database
     * @return repository degli utenti
     */
    private UtentePolisportivaAbstractRepository getUtentiRepository(){
        return this.utentiRepository;
    }

    /**
     * Registra l'utente passato nel sistema della polisportiva (registro e database)
     * @param utente utente da registrare
     */
    public void registraUtente(UtentePolisportivaAbstract utente){
        getListaUtenti().add(utente);
        utentiRepository.save(utente);
    }

    /**
     * Restituisce l'utente associato alla mail passata come parametro
     * @param emailUtente email dell'utente da ricercare
     * @return l'utente associato alla email passata come parametro, se registrato
     */
    public UtentePolisportivaAbstract getUtenteByEmail(String emailUtente){
        for(UtentePolisportivaAbstract sportivo : getListaUtenti()){
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
        for(UtentePolisportivaAbstract utente : this.getListaUtenti()){
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
        this.getListaUtenti().remove(utente);
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
        List<UtentePolisportivaAbstract> istruttori = new ArrayList<UtentePolisportivaAbstract>();
        for(UtentePolisportivaAbstract istruttore : this.getListaUtentiByRuolo(TipoRuolo.ISTRUTTORE)){
            for(Sport sportInsegnato : (List<Sport>)istruttore.getProprieta().get("sportInsegnati")){
                if (sportInsegnato.getNome().equals(sportDiCuiTrovareGliIstruttori.getNome())) {
                    istruttori.add(istruttore);
                }
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

}