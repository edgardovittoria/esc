package it.univaq.esc.model.utenti;

import java.util.List;


import it.univaq.esc.model.Sport;

public class UtentePolisportivaBuilder {
    
    private UtentePolisportivaAbstract utente;

    public UtentePolisportivaBuilder(){
        this.setUtente(new UtentePolisportiva());
    }

    private UtentePolisportivaAbstract getUtente() {
        return utente;
    }

    private void setUtente(UtentePolisportivaAbstract utente) {
        this.utente = utente;
    }

    public UtentePolisportivaBuilder assegnaRuoloSportivo(){
        this.setUtente(new Sportivo(utente));
        return this;
    }

    public UtentePolisportivaBuilder assegnaRuoloIstruttore(List<Sport> listaSportInsegnati){
        this.setUtente(new Istruttore(utente, listaSportInsegnati));
        return this;
    }

    public UtentePolisportivaBuilder assegnaRuoloManutentore(){
        this.setUtente(new Manutentore(utente));
        return this;
    }

    public UtentePolisportivaBuilder assegnaRuoloDirettorePolisportiva(){
        this.setUtente(new DirettorePolisportiva(utente));
        return this;
    }

    public UtentePolisportivaAbstract build(){
        return this.getUtente();
    }

    
}
