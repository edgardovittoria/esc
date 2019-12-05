package it.univaq.esc;

import java.util.Calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univaq.controller.EffettuaPrenotazioneHandler;
import it.univaq.esc.model.Calcetto;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Impianto1;
import it.univaq.esc.model.Impianto2;
import it.univaq.esc.model.Istruttore;
import it.univaq.esc.model.Lezione;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.RegistroSportivi;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Sportivo;
import it.univaq.esc.model.Tennis;

@SpringBootApplication
public class EscApplication {

	public static void main(String[] args) {
		RegistroSportivi registroSportivi = RegistroSportivi.getInstance();
		Sportivo sportivoProva = new Sportivo();
		sportivoProva.setNome("Giovanni");
		sportivoProva.setCognome("Rossi");
		Sport tennis = Tennis.getInstance();
		Sport calcetto = Calcetto.getInstance();
		Impianto impiantoTennis = Impianto1.getInstance();
		Impianto impiantoCalcetto = Impianto2.getInstance();
		tennis.addImpianto(impiantoTennis);
		calcetto.addImpianto(impiantoCalcetto);
		Istruttore istruttoreTennis = new Istruttore();
		Lezione lezioneTennis = new Lezione();
		lezioneTennis.setImpianto(impiantoTennis);
		Calendar dataLezione = Calendar.getInstance();
		lezioneTennis.setCalendario(dataLezione);
		lezioneTennis.setIstruttore(istruttoreTennis);
		istruttoreTennis.addLezione(lezioneTennis);

		Prenotazione nuovaPrenotazione = new Prenotazione(1, sportivoProva);
		nuovaPrenotazione.setServizioPrenotato(lezioneTennis);
		nuovaPrenotazione.setSportivoPrenotante(sportivoProva);
		sportivoProva.addPrenotazione(nuovaPrenotazione);

		
		
		SpringApplication.run(EscApplication.class, args);
	}

}
