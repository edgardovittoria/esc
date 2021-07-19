package it.univaq.esc.controller.promuoviPolisportiva;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.model.Costo;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.Valuta;
import it.univaq.esc.model.Valute;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.prenotazioni.TipoPrenotazione;

@Component
@Singleton
public class CreazionePrenotabileDescrizioneCorsoState  extends CreazioneNuovoPrenotabileState{

	public CreazionePrenotabileDescrizioneCorsoState(CatalogoPrenotabili catalogoPrenotabili,
			RegistroSport registroSport) {
		super(catalogoPrenotabili, registroSport);
	}

	@Override
	public PrenotabileDescrizione creaNuovoPrenotabile(FormPrenotabile formDati) {
		PrenotabileDescrizione descrizioneCorso = getCatalogoPrenotabili().avviaCreazioneNuovoPrenotabile(TipoPrenotazione.CORSO.toString())
				.impostaSport(getRegistroSport().getSportByNome(formDati.getSportSelezionato()))
				.impostaTipoPrenotazione(TipoPrenotazione.CORSO)
				.impostaSogliaMassimaPartecipanti(formDati.getNumeroMassimoPartecipanti())
				.impostaSogliaMinimaPartecipanti(formDati.getNumeroMinimoPartecipanti())
				.impostaCostoUnaTantum(
						new Costo(formDati.getCostoPerPartecipante(), new Valuta(Valute.EUR)))
				.impostaModalitaPrenotazioneComeSingoloUtente()
				.impostaDescrizione(formDati.getNomeEvento())
				.build();
		getCatalogoPrenotabili().aggiungiPrenotabileACatalogo(descrizioneCorso);
		getCatalogoPrenotabili().salvaPrenotabileDescrizioneSulDatabase(descrizioneCorso);
		return descrizioneCorso;
	}
}
