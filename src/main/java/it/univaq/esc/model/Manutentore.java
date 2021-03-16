package it.univaq.esc.model;

public class Manutentore {
    private String nome;
    private String cognome;
    private String email;

    public Manutentore(String nome, String cognome, String email){
        setNome(nome);
        setCognome(cognome);
        setEmail(email);
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
}