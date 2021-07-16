package it.univaq.esc.creazioneDatiDB;

import groovy.lang.Singleton;
import it.univaq.esc.model.*;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.repository.ValutaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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

		PrenotabileDescrizione desc1 = listinoPrezziDescrizioniPolisportiva.avviaCreazioneNuovoPrenotabile()
				.impostaSport(tennis)
				.impostaTipoPrenotazione(TipiPrenotazione.IMPIANTO.toString())
				.impostaSogliaMassimaPartecipanti(tennis.getNumeroGiocatoriPerIncontro())
				.impostaSogliaMinimaPartecipanti(tennis.getNumeroGiocatoriPerIncontro())
				.impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("10"), valutaEuro),
						Pavimentazione.CEMENTO.toString())
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("50"), valutaEuro),
						Pavimentazione.TERRA_BATTUTA.toString())
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.impostaModalitaPrenotazioneComeSingoloUtente().impostaDescrizione("desc1").build();
		listinoPrezziDescrizioniPolisportiva.aggiungiPrenotabileACatalogo(desc1);
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc1);

		PrenotabileDescrizione desc2 = listinoPrezziDescrizioniPolisportiva.avviaCreazioneNuovoPrenotabile()
				.impostaSport(calcetto)
				.impostaTipoPrenotazione(TipiPrenotazione.IMPIANTO.toString())
				.impostaSogliaMassimaPartecipanti(calcetto.getNumeroGiocatoriPerIncontro())
				.impostaSogliaMinimaPartecipanti(calcetto.getNumeroGiocatoriPerIncontro())
				.impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("10"), valutaEuro),
						Pavimentazione.CEMENTO.toString())
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.impostaModalitaPrenotazioneComeSingoloUtente()
				.impostaDescrizione("desc2")
				.build();
		listinoPrezziDescrizioniPolisportiva.aggiungiPrenotabileACatalogo(desc2);
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc2);

		PrenotabileDescrizione desc3 = this.listinoPrezziDescrizioniPolisportiva.avviaCreazioneNuovoPrenotabile()
				.impostaSport(pallavolo)
				.impostaTipoPrenotazione(TipiPrenotazione.IMPIANTO.toString())
				.impostaSogliaMassimaPartecipanti(pallavolo.getNumeroGiocatoriPerIncontro())
				.impostaSogliaMinimaPartecipanti(pallavolo.getNumeroGiocatoriPerIncontro())
				.impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.CEMENTO.toString())
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("30"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.impostaModalitaPrenotazioneComeSingoloUtente()
				.impostaDescrizione("desc3")
				.build();
		listinoPrezziDescrizioniPolisportiva.aggiungiPrenotabileACatalogo(desc3);
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc3);

		PrenotabileDescrizione desc4 = this.listinoPrezziDescrizioniPolisportiva.avviaCreazioneNuovoPrenotabile()
				.impostaSport(tennis)
				.impostaTipoPrenotazione(TipiPrenotazione.IMPIANTO.toString())
				.impostaSogliaMassimaPartecipanti(tennis.getNumeroGiocatoriPerIncontro())
				.impostaSogliaMinimaPartecipanti(tennis.getNumeroGiocatoriPerIncontro())
				.impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("10"), valutaEuro),
						Pavimentazione.CEMENTO.toString())
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("50"), valutaEuro),
						Pavimentazione.TERRA_BATTUTA.toString())
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.impostaModalitaPrenotazioneComeSquadra()
				.impostaDescrizione("desc4")
				.build();
		listinoPrezziDescrizioniPolisportiva.aggiungiPrenotabileACatalogo(desc4);
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc4);

		PrenotabileDescrizione desc5 = this.listinoPrezziDescrizioniPolisportiva.avviaCreazioneNuovoPrenotabile()
				.impostaSport(calcetto)
				.impostaTipoPrenotazione(TipiPrenotazione.IMPIANTO.toString())
				.impostaSogliaMassimaPartecipanti(calcetto.getNumeroGiocatoriPerIncontro())
				.impostaSogliaMinimaPartecipanti(calcetto.getNumeroGiocatoriPerIncontro())
				.impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("10"), valutaEuro),
						Pavimentazione.CEMENTO.toString())
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.impostaModalitaPrenotazioneComeSquadra()
				.impostaDescrizione("desc5")
				.build();
		listinoPrezziDescrizioniPolisportiva.aggiungiPrenotabileACatalogo(desc5);
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc5);

		PrenotabileDescrizione desc6 = this.listinoPrezziDescrizioniPolisportiva.avviaCreazioneNuovoPrenotabile()
				.impostaSport(pallavolo)
				.impostaTipoPrenotazione(TipiPrenotazione.IMPIANTO.toString())
				.impostaSogliaMassimaPartecipanti(pallavolo.getNumeroGiocatoriPerIncontro())
				.impostaSogliaMinimaPartecipanti(pallavolo.getNumeroGiocatoriPerIncontro())
				.impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.CEMENTO.toString())
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("30"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.impostaModalitaPrenotazioneComeSquadra()
				.impostaDescrizione("desc6")
				.build();
		listinoPrezziDescrizioniPolisportiva.aggiungiPrenotabileACatalogo(desc6);
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc6);

		PrenotabileDescrizione desc7 = this.listinoPrezziDescrizioniPolisportiva.avviaCreazioneNuovoPrenotabile()
				.impostaSport(tennis)
				.impostaTipoPrenotazione(TipiPrenotazione.LEZIONE.toString())
				.impostaSogliaMassimaPartecipanti(1)
				.impostaSogliaMinimaPartecipanti(1)
				.impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("10"), valutaEuro),
						Pavimentazione.CEMENTO.toString())
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("50"), valutaEuro),
						Pavimentazione.TERRA_BATTUTA.toString())
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.impostaModalitaPrenotazioneComeSingoloUtente()
				.impostaDescrizione("desc7")
				.build();
		listinoPrezziDescrizioniPolisportiva.aggiungiPrenotabileACatalogo(desc7);
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc7);

		PrenotabileDescrizione desc8 = this.listinoPrezziDescrizioniPolisportiva.avviaCreazioneNuovoPrenotabile()
				.impostaSport(calcetto)
				.impostaTipoPrenotazione(TipiPrenotazione.LEZIONE.toString())
				.impostaSogliaMassimaPartecipanti(5)
				.impostaSogliaMinimaPartecipanti(1)
				.impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("10"), valutaEuro),
						Pavimentazione.CEMENTO.toString())
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.impostaModalitaPrenotazioneComeSingoloUtente()
				.impostaDescrizione("desc8")
				.build();
		listinoPrezziDescrizioniPolisportiva.aggiungiPrenotabileACatalogo(desc8);
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc8);

		PrenotabileDescrizione desc9 = this.listinoPrezziDescrizioniPolisportiva.avviaCreazioneNuovoPrenotabile()
				.impostaSport(pallavolo)
				.impostaTipoPrenotazione(TipiPrenotazione.LEZIONE.toString())
				.impostaSogliaMassimaPartecipanti(6)
				.impostaSogliaMinimaPartecipanti(1)
				.impostaCostoOrario(new Costo(Float.parseFloat("10"), valutaEuro))
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("20"), valutaEuro),
						Pavimentazione.CEMENTO.toString())
				.impostaCostoPavimentazione(new Costo(Float.parseFloat("30"), valutaEuro),
						Pavimentazione.SINTETICO.toString())
				.impostaModalitaPrenotazioneComeSingoloUtente()
				.impostaDescrizione("desc9")
				.build();
		listinoPrezziDescrizioniPolisportiva.aggiungiPrenotabileACatalogo(desc9);
		listinoPrezziDescrizioniPolisportiva.salvaPrenotabileDescrizioneSulDatabase(desc9);
	}
}
