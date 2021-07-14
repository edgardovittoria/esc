package it.univaq.esc.EntityDTOMappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.ImpiantoDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class ImpiantoMapper extends EntityDTOMapper{

	

	public ImpiantoDTO convertiInImpiantoDTO(Impianto impiantoDaConvertire) {
		ImpiantoDTO impiantoDTO = new ImpiantoDTO();
        impiantoDTO.setIdImpianto(impiantoDaConvertire.getIdNotificabile());
        impiantoDTO.setIndoor(impiantoDaConvertire.isIndoor());
        impiantoDTO.setPavimentazione(impiantoDaConvertire.getTipoPavimentazione().toString());
        for(Sport sport : impiantoDaConvertire.getSportPraticabili()){
            SportDTO sportDTO = getMapperFactory().getSportMapper().convertiInSportDTO(sport);
            impiantoDTO.getSportPraticabili().add(sportDTO);
        }
        
        for(Appuntamento appuntamento : impiantoDaConvertire.getListaAppuntamenti()){
        	impostaMapperFactory(appuntamento.getModalitaPrenotazione());
        	AppuntamentoDTO appuntamentoDTO = getMapperFactory().getAppuntamentoMapper(appuntamento.getTipoPrenotazione()).convertiInAppuntamentoDTO(appuntamento);
            impiantoDTO.getAppuntamenti().add(appuntamentoDTO);
        }
        
        return impiantoDTO;
	}
	
	
	
	public Impianto convertiDTOInImpianto(ImpiantoDTO impiantoDTODaConvertire) {
		Impianto impianto = new Impianto();
		impianto.setIndoor(impiantoDTODaConvertire.isIndoor());
		impianto.setSpecificheImpianto(trovaSpecificheImpiantoDa(impiantoDTODaConvertire.getPavimentazione(), impiantoDTODaConvertire.getSportPraticabili()));
		//TODO impostare gli appuntamenti del calendario impianto.
		return impianto;
	}
	
	private List<ImpiantoSpecs> trovaSpecificheImpiantoDa(String pavimentazione, List<SportDTO> sportDTO){
		List<ImpiantoSpecs> specifiche = new ArrayList<ImpiantoSpecs>();
		List<String> nomiSport = getNomiSportDellaLista(sportDTO);
		for(String sport : nomiSport) {
			specifiche.add(getRegistroImpianti().trovaSpecificaDa(pavimentazione, sport));
		}
		return specifiche;
	}
	
	private List<String> getNomiSportDellaLista(List<SportDTO> sportsDTO){
		List<String> nomiSport = new ArrayList<String>();
		for(SportDTO sportDTO : sportsDTO) {
			nomiSport.add(sportDTO.getNome());
		}
		return nomiSport;
	}

}
