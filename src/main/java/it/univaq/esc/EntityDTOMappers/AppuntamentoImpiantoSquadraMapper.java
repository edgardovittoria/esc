package it.univaq.esc.EntityDTOMappers;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.OrarioAppuntamentoDTO;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpiantoSquadra;
import it.univaq.esc.model.prenotazioni.DatiFormPerAppuntamento;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.Squadra;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Singleton
@NoArgsConstructor
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
public class AppuntamentoImpiantoSquadraMapper extends AppuntamentoMapper{

	@Override
		public AppuntamentoDTO convertiInAppuntamentoDTO(Appuntamento appuntamentoDaConvertire) {
			AppuntamentoDTO appuntamentoDTO = super.convertiInAppuntamentoDTO(appuntamentoDaConvertire);
			
			AppuntamentoImpiantoSquadra appuntamentoImpiantoSquadra = (AppuntamentoImpiantoSquadra) appuntamentoDaConvertire;
						
			appuntamentoDTO.setInvitati(getListaNomiSquadreDallaLista(appuntamentoImpiantoSquadra.getSquadreInvitate()));
			appuntamentoDTO.setSquadrePartecipanti(getIdSquadrePartecipantiDallaLista(appuntamentoImpiantoSquadra.getSquadrePartecipanti()));
			
			return appuntamentoDTO;
		}
	
	
	private List<String> getListaNomiSquadreDallaLista(List<Squadra> squadreInvitate) {
		List<String> listaNomiSquadreInvitate = new ArrayList<String>();
		squadreInvitate.forEach((squadra) -> listaNomiSquadreInvitate.add(squadra.getNome()));
		return listaNomiSquadreInvitate;
	}
	
	private List<Integer> getIdSquadrePartecipantiDallaLista(List<Squadra> squadrePartecipanti){
		List<Integer> listaIdSquadrePartecipanti = new ArrayList<Integer>();
		squadrePartecipanti.forEach((squadra) -> listaIdSquadrePartecipanti.add(squadra.getIdSquadra()));
		return listaIdSquadrePartecipanti;
	}
	
	@Override
	public DatiFormPerAppuntamento getDatiFormPerAppuntamentoUsando(FormPrenotabile formDati,
			OrarioAppuntamentoDTO orario) {
		DatiFormPerAppuntamento datiFormPerAppuntamento = super.getDatiFormPerAppuntamentoUsando(formDati, orario);

		datiFormPerAppuntamento.setDescrizioneEvento(
				trovaPrenotabileDescrizioneImpiantoInModalitaSquadraRelativaA(formDati.getSportSelezionato()));

		datiFormPerAppuntamento.setSquadreInvitate(getListaSquadreIinvitateAPartireDa(formDati.getSquadreInvitate()));

		datiFormPerAppuntamento.setPending(
				trovaNellaListaCheckboxesValorePendingRelativoAOrario(formDati.getCheckboxesPending(), orario));

		return datiFormPerAppuntamento;
	}
	
	private PrenotabileDescrizione trovaPrenotabileDescrizioneImpiantoInModalitaSquadraRelativaA(
			String nomeSportSelezionato) {
		Sport sportSelezionato = getRegistroSport().getSportByNome(nomeSportSelezionato);
		PrenotabileDescrizione descrizioneEventoPrenotabile = getCatalogoPrenotabili()
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						TipiPrenotazione.IMPIANTO.toString(), sportSelezionato,
						ModalitaPrenotazione.SQUADRA);

		return descrizioneEventoPrenotabile;
	}
	
	private List<Squadra> getListaSquadreIinvitateAPartireDa(List<Integer> listaIdSquadre) {
		List<Squadra> squadreInvitate = new ArrayList<Squadra>();
		for (Integer idSquadraInvitata : listaIdSquadre) {
			squadreInvitate.add(getRegistroSquadre().getSquadraById(idSquadraInvitata));
		}
		return squadreInvitate;
	}
	
}
