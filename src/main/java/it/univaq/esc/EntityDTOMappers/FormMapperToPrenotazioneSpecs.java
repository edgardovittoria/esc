package it.univaq.esc.EntityDTOMappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneState;
import it.univaq.esc.dtoObjects.FormPrenotaImpianto;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamento;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneImpiantoSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

@Component
@Singleton
public class FormMapperToPrenotazioneSpecs{

	public PrenotazioneSpecs impostaDatiPrenotazioneImpiantoSpecs(PrenotazioneSpecs specificaDaImpostare ,FormPrenotabile formDati, OrarioAppuntamento orario, EffettuaPrenotazioneState statoEffettuaPrenotazione, Prenotazione prenotazioneAssociata){
		FormPrenotaImpianto formDatiImpianto = (FormPrenotaImpianto) formDati;
		PrenotazioneImpiantoSpecs specificaImpianto = (PrenotazioneImpiantoSpecs) specificaDaImpostare;
		
		PrenotabileDescrizione descrizioneSpecifica = statoEffettuaPrenotazione.getCatalogoPrenotabili()
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						prenotazioneAssociata.getListaAppuntamenti().get(0)
								.getTipoPrenotazione(),
						statoEffettuaPrenotazione.getRegistroSport().getSportByNome(formDatiImpianto.getSportSelezionato()),
						formDati.getModalitaPrenotazione());

		specificaDaImpostare.setPrenotazioneAssociata(prenotazioneAssociata);
		specificaDaImpostare.setSpecificaDescription(descrizioneSpecifica);
		
		
		List<UtentePolisportivaAbstract> invitati = new ArrayList<UtentePolisportivaAbstract>();
		formDatiImpianto.getSportiviInvitati().forEach((email) -> invitati.add(statoEffettuaPrenotazione.getRegistroUtenti().getUtenteByEmail(email)));
		specificaImpianto.setInvitati(invitati);
		
		Integer idImpianto = 0;
		for (ImpiantoSelezionato impianto : formDatiImpianto.getImpianti()) {
			if (impianto.getIdSelezione() == orario.getId()) {
				idImpianto = impianto.getIdImpianto();
			}
		}
		
		specificaImpianto.setImpiantoPrenotato(statoEffettuaPrenotazione.getRegistroImpianti().getImpiantoByID(idImpianto));
		specificaImpianto.setNumeroGiocatoriNonRegistratiAssociati(formDatiImpianto.getNumeroGiocatoriNonIscritti());
		
	
		
		return specificaImpianto;
	}
	
}
