package it.univaq.esc.utility;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.Sportivo;
import it.univaq.esc.services.IOpzioniPrenotazione;
import it.univaq.esc.services.ImpiantoService;
import it.univaq.esc.services.IstruttoreService;
import it.univaq.esc.services.OpzioniPrenotazioneImpianto;
import it.univaq.esc.services.PrenotazioneService;
import it.univaq.esc.services.SportService;
import it.univaq.esc.services.SportivoService;

@Component
public class SimpleFactory {

	private static SimpleFactory factoryInstance;
	private IOpzioniPrenotazione opzioniPrenotazione;
	
	private SimpleFactory() {}
	
	public static SimpleFactory getInstance() {
		if(factoryInstance == null) {
			factoryInstance = new SimpleFactory();
		}
		
		return factoryInstance;
	}
	
	public Prenotazione getNuovaPrenotazione(int lastIDPrenotazione, Sportivo sportivoPrenotante) {
		Prenotazione prenotazione = new Prenotazione(lastIDPrenotazione, sportivoPrenotante);
		return prenotazione;
	}
	
	public void setStrategy(String etichetta) {
		switch (etichetta) {
		case "impianto":
			this.opzioniPrenotazione = new OpzioniPrenotazioneImpianto();
			break;

		default:
			break;
		}
	}
	
	public IOpzioniPrenotazione getOpzioni(ImpiantoService impiantoService, SportivoService sportivoService, SportService sportService, IstruttoreService istruttoreService, PrenotazioneService prenotazioneService, Model opzioni) {
		return this.opzioniPrenotazione.getOpzioni(impiantoService, sportivoService, sportService, istruttoreService, prenotazioneService);
	}
	
	
	
//	public IPrenotabile getIPrenotabile(String etich) {
//		IPrenotabile servizioPrenotato = null;
//		switch (etich) {
//		case "Lezione":
//			IPrenotabile lezione = new Lezione();
//			servizioPrenotato = lezione;
//			break;
//			
//		default:
//			break;
//		}
//		return servizioPrenotato;
//	}
	
}
