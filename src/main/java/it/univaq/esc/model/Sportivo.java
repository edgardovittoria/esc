package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sportivi")
public class Sportivo {

    @Column
    private String nome;
    @Column
    private String cognome;
    @Id
    private String email;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "sport_praticati_sportivi",
                joinColumns = {@JoinColumn(name="email")},
                inverseJoinColumns = {@JoinColumn(name="sport_praticato")})
    private List<Sport> sportPraticatiDalloSportivo = new ArrayList<Sport>();

    public Sportivo(){}

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
