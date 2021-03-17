package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

public class Sportivo {

    private String nome;
    private String cognome;
    private String email;
    private List<Sport> sportPraticatiDalloSportivo = new ArrayList<Sport>();

    public Sportivo(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
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

    public List<Sport> getSportPraticatiDalloSportivo() {
        return sportPraticatiDalloSportivo;
    }

    public void setSportPraticatiDalloSportivo(List<Sport> sportPraticatiDalloSportivo) {
        this.sportPraticatiDalloSportivo = sportPraticatiDalloSportivo;
    }

    public void aggiungiSporPraticatoDalloSportivo(Sport sportPraticato){
        this.sportPraticatiDalloSportivo.add(sportPraticato);
    }
    
    
}
