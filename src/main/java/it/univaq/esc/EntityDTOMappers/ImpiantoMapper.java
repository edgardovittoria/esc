package it.univaq.esc.EntityDTOMappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.StrutturaPolisportivaDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.StrutturaPolisportiva;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class ImpiantoMapper extends StrutturaPolisportivaMapper{

	
	@Override
	public StrutturaPolisportivaDTO convertiInStrutturaPolisportivaDTO(StrutturaPolisportiva strutturaPolisportiva) {
		StrutturaPolisportivaDTO impiantoDTO = super.convertiInStrutturaPolisportivaDTO(strutturaPolisportiva);
        Impianto impiantoDaConvertire = (Impianto) strutturaPolisportiva;
		impiantoDTO.setIndoor(impiantoDaConvertire.isIndoor());
        impiantoDTO.setPavimentazione(impiantoDaConvertire.getTipoPavimentazione().toString());
        for(Sport sport : impiantoDaConvertire.getSportPraticabili()){
            SportDTO sportDTO = getMapperFactory().getSportMapper().convertiInSportDTO(sport);
            impiantoDTO.getSportPraticabili().add(sportDTO);
        }   
        return impiantoDTO;
	}
	
	
	
	@Override
	public StrutturaPolisportiva convertiDTOInStrutturaPolisportiva(StrutturaPolisportivaDTO strutturaDTO) {
		Impianto impianto = new Impianto();
		impianto.setIndoor(strutturaDTO.isIndoor());
		impianto.setSpecificheImpianto(ottieniSpecificheImpiantoDa(strutturaDTO.getPavimentazione(), strutturaDTO.getSportPraticabili()));
		//TODO impostare gli appuntamenti del calendario impianto.
		return impianto;
	}
	
	
	private List<ImpiantoSpecs> ottieniSpecificheImpiantoDa(String pavimentazione, List<SportDTO> sportDTO){
		List<ImpiantoSpecs> specifiche = new ArrayList<ImpiantoSpecs>();
		List<String> nomiSport = getNomiSportDellaLista(sportDTO);
		for(String sport : nomiSport) {
			specifiche.add(getRegistroImpianti().ottienSpecificaImpiantoDa(pavimentazione, sport));
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
