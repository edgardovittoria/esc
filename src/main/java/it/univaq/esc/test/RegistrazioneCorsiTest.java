package it.univaq.esc.test;



import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE) @AllArgsConstructor
public class RegistrazioneCorsiTest {

	private RegistroPrenotazioni registroPrenotazioni;
	
	private RegistroAppuntamenti registroAppuntamenti;	
	
	public void test() {
		for(Prenotazione prenotazione : getRegistroPrenotazioni().getPrenotazioniRegistrate()) {
			System.out.println("TIPO: " + prenotazione.getTipoPrenotazione());
			System.out.println("SPECIFICHE: " + prenotazione.getListaSpecifichePrenotazione());
			for(PrenotazioneSpecs prenotazioneSpecs : prenotazione.getListaSpecifichePrenotazione()) {
				if(prenotazioneSpecs.getTipoPrenotazione().equals(TipiPrenotazione.CORSO.toString())) {
					System.out.println("LEZIONI CORSO: " + prenotazioneSpecs.getValoriSpecificheExtraPrenotazione().get("listaLezioniCorso"));
				}
			}
		}
		
		for(Appuntamento appuntamento : getRegistroAppuntamenti().getListaAppuntamenti()) {
			System.out.println("----------Appuntamento-----------------");
			System.out.println("Tipo specifica: " + appuntamento.getTipoPrenotazione());
			System.out.println("Tipo prenotazione:" + appuntamento.getPrenotazionePrincipale().getTipoPrenotazione());
		}
		
		
	}
	
}
