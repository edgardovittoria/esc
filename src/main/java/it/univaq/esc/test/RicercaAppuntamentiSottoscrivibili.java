package it.univaq.esc.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;

@Component
public class RicercaAppuntamentiSottoscrivibili {
    

    @Autowired
    private RegistroAppuntamenti registroAppuntamenti;

    @Autowired
    private RegistroUtentiPolisportiva registroUtentiPolisportiva;


    public RicercaAppuntamentiSottoscrivibili(){}


    public void test(){
        String emailUtentePerCuiCercareAppuntamentiSottoscrivibili = "pippofranco@bagaglino.com";
        String tipoPrenotazioneAppuntamentiDaRicercare = TipiPrenotazione.IMPIANTO.toString();

        System.out.println(
            this.getRegistroAppuntamenti()
                .getAppuntamentiSottoscrivibiliSingoloUtentePerTipo(tipoPrenotazioneAppuntamentiDaRicercare, 
                this.getRegistroUtentiPolisportiva().getUtenteByEmail(emailUtentePerCuiCercareAppuntamentiSottoscrivibili)));
    }


    private RegistroAppuntamenti getRegistroAppuntamenti() {
        return registroAppuntamenti;
    }

    private RegistroUtentiPolisportiva getRegistroUtentiPolisportiva() {
        return registroUtentiPolisportiva;
    }

   

}
