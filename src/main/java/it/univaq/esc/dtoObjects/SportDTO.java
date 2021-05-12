package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.Sport;

public class SportDTO implements IModelToDTO{
    
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

    @Override
    public void impostaValoriDTO(Object modelDaConvertire){
        Sport sport = (Sport)modelDaConvertire;
        this.nome = sport.getNome();
        this.postiLiberi = sport.getNumeroGiocatori();
    }
    
}
