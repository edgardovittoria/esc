package it.univaq.esc.controller.promuoviPolisportiva;

import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(value = AccessLevel.PROTECTED) @Getter(value = AccessLevel.PROTECTED)
public abstract class CreazioneNuovoPrenotabileState {

	private CatalogoPrenotabili catalogoPrenotabili;
	private RegistroSport registroSport;
	
	public CreazioneNuovoPrenotabileState(CatalogoPrenotabili catalogoPrenotabili, RegistroSport registroSport) {
		setCatalogoPrenotabili(catalogoPrenotabili);
		setRegistroSport(registroSport);
	}
	
	public abstract PrenotabileDescrizione creaNuovoPrenotabile(FormPrenotabile formDati);
}
