package it.univaq.esc.model;

public class Sport {

    private String nome;
    private int numeroGiocatori;

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
