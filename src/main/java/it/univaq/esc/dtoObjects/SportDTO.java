package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.Sport;

public class SportDTO {
    
    private String nome;
    private int postiLiberi;

    public SportDTO(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPostiLiberi() {
        return postiLiberi;
    }

    public void setPostiLiberi(int postiLiberi) {
        this.postiLiberi = postiLiberi;
    }

    public void impostaValoriDTO(Sport sport){
        this.nome = sport.getNome();
        this.postiLiberi = sport.getNumeroGiocatori();
    }
    
}
