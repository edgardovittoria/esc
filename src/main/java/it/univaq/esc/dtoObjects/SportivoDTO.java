package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.Sportivo;

public class SportivoDTO {

    private String nome;
    private String cognome;
    private String email;

    public SportivoDTO(){}

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

    public void impostaValoriDTO(Sportivo sportivo){
        this.nome = sportivo.getNome();
        this.cognome = sportivo.getCognome();
        this.email = sportivo.getEmail();
    }

    
    
}
