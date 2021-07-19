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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Singleton
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
public class CreazionePacchettoLezioniScontatoState extends CreazioneNuovoPrenotabileState{

	public CreazionePacchettoLezioniScontatoState(CatalogoPrenotabili catalogoPrenotabili,
			RegistroSport registroSport) {
		super(catalogoPrenotabili, registroSport);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PrenotabileDescrizione creaNuovoPrenotabile(FormPrenotabile formDati) {
		PrenotabileDescrizione pacchettoLezioni = getCatalogoPrenotabili().avviaCreazioneNuovoPrenotabile()
				.impostaTipoPrenotazione(TipoPrenotazione.PACCHETTO_LEZIONI)
				.impostaNumeroDatePacchettoLezioni(formDati.getNumeroDate())
				.impostaCostoScontoPercentuale(
						new Costo(formDati.getScontoPercentuale(), new Valuta(Valute.EUR)))
				.impostaModalitaPrenotazioneComeSingoloUtente()
				.impostaDescrizione(formDati.getNomeEvento())
				.build();
		getCatalogoPrenotabili().aggiungiPrenotabileACatalogo(pacchettoLezioni);
		getCatalogoPrenotabili().salvaPrenotabileDescrizioneSulDatabase(pacchettoLezioni);
		return pacchettoLezioni;
	}

}
