package it.univaq.esc.model.utenti;

import it.univaq.esc.model.Sport;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
public class UtentePolisportivaBuilder {
    
    private UtentePolisportiva utente;

    public UtentePolisportivaBuilder(String nome, String cognome, String email, String password){
    	AnagraficaUtente anagraficaUtente = new AnagraficaUtente();
    	anagraficaUtente.setNome(nome);
    	anagraficaUtente.setCognome(cognome);
    	anagraficaUtente.setEmail(email);
    	anagraficaUtente.setPassword(password);
        this.setUtente(new UtentePolisportiva(anagraficaUtente));
    }


    public UtentePolisportivaBuilder assegnaRuoloSportivo(List<Sport> listaSportPraticati){
        ProfiloSportivo profiloSportivo = new ProfiloSportivo(listaSportPraticati);
        getUtente().aggiungi(profiloSportivo);
        
        return this;
    }

    public UtentePolisportivaBuilder assegnaRuoloIstruttore(List<Sport> listaSportInsegnati){
        ProfiloIstruttore profiloIstruttore = new ProfiloIstruttore(listaSportInsegnati);
        getUtente().aggiungi(profiloIstruttore);
        
        return this;
    }

    public UtentePolisportivaBuilder assegnaRuoloManutentore(){
        ProfiloManutentore profiloManutentore = new ProfiloManutentore();
        getUtente().aggiungi(profiloManutentore);
        
        return this;
    }

    public UtentePolisportivaBuilder assegnaRuoloDirettorePolisportiva(){
        ProfiloDirettore profiloDirettore = new ProfiloDirettore();
        getUtente().aggiungi(profiloDirettore);
        
        return this;
    }
    
    public UtentePolisportivaBuilder aggiungiProfiliA(UtentePolisportiva utenteEsistente) {
    	setUtente(utenteEsistente);
    	
    	return this;
    }

    public UtentePolisportiva build(){
        return this.getUtente();
    }

    
}
