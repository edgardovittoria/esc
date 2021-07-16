package it.univaq.esc.EntityDTOMappers;

import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.IstruttoreSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamentoDTO;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoLezione;
import it.univaq.esc.model.prenotazioni.DatiFormPerAppuntamento;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Singleton
@NoArgsConstructor
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
public class AppuntamentoLezioneMapper extends AppuntamentoMapper{

	@Override
		public AppuntamentoDTO convertiInAppuntamentoDTO(Appuntamento appuntamentoDaConvertire) {
			
			AppuntamentoDTO appuntamentoDTO = super.convertiInAppuntamentoDTO(appuntamentoDaConvertire);
			AppuntamentoLezione appuntamentoLezione = (AppuntamentoLezione) appuntamentoDaConvertire;
			
			appuntamentoDTO.setIstruttore(appuntamentoLezione.getNominativoIstruttore());
			
			return appuntamentoDTO;
		}
	
	@Override
	public DatiFormPerAppuntamento getDatiFormPerAppuntamentoUsando(FormPrenotabile formDati,
			OrarioAppuntamentoDTO orario) {
		DatiFormPerAppuntamento datiFormPerAppuntamento = super.getDatiFormPerAppuntamentoUsando(formDati, orario);
		
		datiFormPerAppuntamento.setDescrizioneEvento(trovaPrenotabileDescrizioneLezioneInModalitaSingoloUtenteRelativaA(formDati.getSportSelezionato()));
		datiFormPerAppuntamento.setIstruttore(getIstruttoreAssociatoAllOrarioDallaListaIstruttori(orario, formDati.getIstruttori()));
		
		return datiFormPerAppuntamento;
	}
	
	private PrenotabileDescrizione trovaPrenotabileDescrizioneLezioneInModalitaSingoloUtenteRelativaA(
			String nomeSportSelezionato) {
		Sport sportSelezionato = getRegistroSport().getSportByNome(nomeSportSelezionato);
		PrenotabileDescrizione descrizioneEventoPrenotabile = getCatalogoPrenotabili()
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						TipiPrenotazione.LEZIONE.toString(), sportSelezionato,
						ModalitaPrenotazione.SINGOLO_UTENTE);

		return descrizioneEventoPrenotabile;
	}
	
	private UtentePolisportiva getIstruttoreAssociatoAllOrarioDallaListaIstruttori(OrarioAppuntamentoDTO orario, List<IstruttoreSelezionato> listaIstruttoriSelezionati) {
			IstruttoreSelezionato istruttoreSelezionato = null;
			for(IstruttoreSelezionato istruttore : listaIstruttoriSelezionati) {
				if(istruttore.getIdSelezione() == orario.getId()) {
					istruttoreSelezionato = istruttore;
				}
			}
			return getRegistroUtentiPolisportiva().trovaUtenteInBaseAllaSua(istruttoreSelezionato.getIstruttore());
	}
	
}
