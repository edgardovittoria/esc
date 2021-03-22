package it.univaq.esc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sports")
public class Sport {

    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSport;*/
    @Id
    private String nome;
    @Column
    private int numeroGiocatori;

    public Sport(){}

    public Sport(String nome, int numeroGiocatori){
        this.nome = nome;
        this.numeroGiocatori = numeroGiocatori;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getNumeroGiocatori() {
        return numeroGiocatori;
    }
      
}
