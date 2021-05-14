package it.univaq.esc.test;

import it.univaq.esc.model.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;

public class RicercaAppuntamentiSottoscrivibili {
    

    private RegistroAppuntamenti registroAppuntamenti;

    private RegistroUtentiPolisportiva registroUtentiPolisportiva;


    public RicercaAppuntamentiSottoscrivibili(){}


    public void test(){
        String emailUtentePerCuiCercareAppuntamentiSottoscrivibili = "pippofranco@bagaglino.com";
        String tipoPrenotazioneAppuntamentiDaRicercare = TipiPrenotazione.IMPIANTO.toString();

        System.out.println(
            this.getRegistroAppuntamenti()
                .getAppuntamentiSottoscrivibiliPerTipo(tipoPrenotazioneAppuntamentiDaRicercare, 
                this.getRegistroUtentiPolisportiva().getUtenteByEmail(emailUtentePerCuiCercareAppuntamentiSottoscrivibili)));
    }


    private RegistroAppuntamenti getRegistroAppuntamenti() {
        return registroAppuntamenti;
    }

    private RegistroUtentiPolisportiva getRegistroUtentiPolisportiva() {
        return registroUtentiPolisportiva;
    }

   

}
