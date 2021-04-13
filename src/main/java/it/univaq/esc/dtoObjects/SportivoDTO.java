package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.Sportivo;
import it.univaq.esc.model.UtentePolisportivaAbstract;

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

    public void impostaValoriDTO(UtentePolisportivaAbstract sportivo){
        this.nome = (String)sportivo.getProprieta().get("nome");
        this.cognome = (String)sportivo.getProprieta().get("cognome");
        this.email = (String)sportivo.getProprieta().get("email");
    }

    
    
}
