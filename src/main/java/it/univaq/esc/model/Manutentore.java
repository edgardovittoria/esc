package it.univaq.esc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "manutentori")
public class Manutentore {
    @Column
    private String nome;
    @Column
    private String cognome;
    @Id
    private String email;
    @OneToOne()
    private Calendario calendario;

    public Manutentore(){}

    public Manutentore(String nome, String cognome, String email, Calendario calendario){
        setNome(nome);
        setCognome(cognome);
        setEmail(email);
        this.calendario = calendario;
    }

    public String getNome(){
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    
}