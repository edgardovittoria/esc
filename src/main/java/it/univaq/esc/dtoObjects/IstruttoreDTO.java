package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.Sport;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

public class IstruttoreDTO {
    
    private String nome;
    private String cognome;
    private String email;
    private List<String> sportPraticati = new ArrayList<String>();
    private List<String> sportInsegnati = new ArrayList<String>();

    public IstruttoreDTO(){}

    public List<String> getSportInsegnati() {
        return sportInsegnati;
    }

    public void setSportInsegnati(List<String> sportInsegnati) {
        this.sportInsegnati = sportInsegnati;
    }

    public List<String> getSportPraticati() {
        return sportPraticati;
    }

    public void setSportPraticati(List<String> sportPraticati) {
        this.sportPraticati = sportPraticati;
    }

    
    public void aggiungiSportPraticato(String nomeSport){
        if(!this.getSportPraticati().contains(nomeSport)){
            this.getSportPraticati().add(nomeSport);
        }
    }

    public void aggiungiSportInsegnato(String nomeSport){
        if(!this.getSportInsegnati().contains(nomeSport)){
            this.getSportInsegnati().add(nomeSport);
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void impostaValoriDTO(UtentePolisportivaAbstract sportivo){
        this.setNome((String)sportivo.getProprieta().get("nome"));
        this.setCognome((String)sportivo.getProprieta().get("cognome"));
        this.setEmail((String)sportivo.getProprieta().get("email"));
        
        for(Sport sport : (List<Sport>)sportivo.getProprieta().get("sportPraticati")){
            this.aggiungiSportPraticato(sport.getNome());
        }
        
       
        for(Sport sport : (List<Sport>)sportivo.getProprieta().get("sportInsegnati")){
            this.aggiungiSportInsegnato(sport.getNome());
        }
    }


}
