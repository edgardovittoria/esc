package it.univaq.esc.creazioneDatiDB;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.Costo;
import it.univaq.esc.model.Pavimentazione;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Valuta;
import it.univaq.esc.model.Valute;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.repository.ValutaRepository;
import lombok.AllArgsConstructor;

@Component
@Singleton
@AllArgsConstructor
public class CreaPrenotabiliDescrizioneDB {

	private CatalogoPrenotabili listinoPrezziDescrizioniPolisportiva;
	private ValutaRepository valutaRepository;
	private CreaSportsDB creaSportsDB;
	
	public void creaPrenotabiliDescrizione() {
		Valuta valutaEuro = new Valuta(Valute.EUR);
		valutaRepository.save(valutaEuro);
		
		Sport tennis = creaSportsDB.getSportConNome("tennis");
		Sport pallavolo = creaSportsDB.getSportConNome("pallavolo");
		Sport calcetto = creaSportsDB.getSportConNome("calcetto");
		
		
		 PrenotabileDescrizione desc1 = listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(tennis, TipiPrenotazione.IMPIANTO.toString(),
						tennis.getNumeroGiocatoriPerIncontro(), tennis.getNumeroGiocatoriPerIncontro())
				.nuovoPrenotabile_impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("10"), valutaEuro), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("50"), valutaEuro),
						Pavimentazione.TERRA_BATTUTA.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_impostaDescrizione("desc1")
				.nuovoPrenotabile_salvaPrenotabileInCreazioneNelCatalogo();
		
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc1);

		PrenotabileDescrizione desc2 = this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(calcetto, TipiPrenotazione.IMPIANTO.toString(),
						calcetto.getNumeroGiocatoriPerIncontro(), calcetto.getNumeroGiocatoriPerIncontro())
				.nuovoPrenotabile_impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("10"), valutaEuro), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_impostaDescrizione("desc2")
				.nuovoPrenotabile_salvaPrenotabileInCreazioneNelCatalogo();
		
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc2);

		PrenotabileDescrizione desc3 = this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(pallavolo, TipiPrenotazione.IMPIANTO.toString(),
						pallavolo.getNumeroGiocatoriPerIncontro(), pallavolo.getNumeroGiocatoriPerIncontro())
				.nuovoPrenotabile_impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("30"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_impostaDescrizione("desc3")
				.nuovoPrenotabile_salvaPrenotabileInCreazioneNelCatalogo();
		
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc3);

		PrenotabileDescrizione desc4 = this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(tennis, TipiPrenotazione.IMPIANTO.toString(),
						tennis.getNumeroGiocatoriPerIncontro(), tennis.getNumeroGiocatoriPerIncontro())
				.nuovoPrenotabile_impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("10"), valutaEuro), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("50"), valutaEuro),
						Pavimentazione.TERRA_BATTUTA.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSquadra()
				.nuovoPrenotabile_impostaDescrizione("desc4")
				.nuovoPrenotabile_salvaPrenotabileInCreazioneNelCatalogo();
		
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc4);

		PrenotabileDescrizione desc5 = this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(calcetto, TipiPrenotazione.IMPIANTO.toString(),
						calcetto.getNumeroGiocatoriPerIncontro(), calcetto.getNumeroGiocatoriPerIncontro())
				.nuovoPrenotabile_impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("10"), valutaEuro), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSquadra()
				.nuovoPrenotabile_impostaDescrizione("desc5")
				.nuovoPrenotabile_salvaPrenotabileInCreazioneNelCatalogo();
		
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc5);
		
		

		PrenotabileDescrizione desc6 = this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(pallavolo, TipiPrenotazione.IMPIANTO.toString(),
						pallavolo.getNumeroGiocatoriPerIncontro(), pallavolo.getNumeroGiocatoriPerIncontro())
				.nuovoPrenotabile_impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("30"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSquadra()
				.nuovoPrenotabile_impostaDescrizione("desc6")
				.nuovoPrenotabile_salvaPrenotabileInCreazioneNelCatalogo();
		
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc6);

		PrenotabileDescrizione desc7 = this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(tennis, TipiPrenotazione.LEZIONE.toString(), 1, 1)
				.nuovoPrenotabile_impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("10"), valutaEuro), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("50"), valutaEuro),
						Pavimentazione.TERRA_BATTUTA.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_impostaDescrizione("desc7")
				.nuovoPrenotabile_salvaPrenotabileInCreazioneNelCatalogo();
		 
		 listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc7);

		PrenotabileDescrizione desc8 = this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(calcetto, TipiPrenotazione.LEZIONE.toString(), 1, 5)
				.nuovoPrenotabile_impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("10"), valutaEuro), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_impostaDescrizione("desc8")
				.nuovoPrenotabile_salvaPrenotabileInCreazioneNelCatalogo();
		
		 listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc8);

		PrenotabileDescrizione desc9 = this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(pallavolo, TipiPrenotazione.LEZIONE.toString(), 1, 6)
				.nuovoPrenotabile_impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(new Costo(Float.parseFloat("30"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_impostaDescrizione("desc9")
				.nuovoPrenotabile_salvaPrenotabileInCreazioneNelCatalogo();
		
		 listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc9);
	}
}
