package it.univaq.esc.EntityDTOMappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.utenti.TipoRuolo;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class UtenteMapper extends EntityDTOMapper{
	
	

	

	public UtentePolisportivaDTO convertiInUtentePolisportivaDTO(UtentePolisportivaAbstract utenteDaConvertire) {
		UtentePolisportivaDTO utenteDTO = new UtentePolisportivaDTO();
		
		utenteDTO.setNome((String)utenteDaConvertire.getProprieta().get("nome"));
		utenteDTO.setCognome((String) utenteDaConvertire.getProprieta().get("cognome"));
		utenteDTO.setEmail((String) utenteDaConvertire.getProprieta().get("email"));
		utenteDTO.setRuoli(utenteDaConvertire.getRuoliUtentePolisportiva());

		Map<String, Object> mappaAttributi = new HashMap<String, Object>();

		/*
		 * Impostiamo gli attributi extra dello sportivo.
		 */
		if (utenteDTO.getRuoli().contains(TipoRuolo.SPORTIVO.toString())) {
			List<String> sportPraticati = new ArrayList<String>();
			for (Sport sport : (List<Sport>) utenteDaConvertire.getProprieta().get("sportPraticati")) {
				sportPraticati.add(sport.getNome());
			}
			mappaAttributi.put("sportPraticati", sportPraticati);
			mappaAttributi.put("moroso", (Boolean) utenteDaConvertire.getProprieta().get("moroso"));
			mappaAttributi.put("appuntamentiSportivo", utenteDaConvertire.getProprieta().get("calendarioAppuntamentiSportivo"));

		}

		/*
		 * Impostiamo gli attributi extra dell'istruttore
		 */
		if (utenteDTO.getRuoli().contains(TipoRuolo.ISTRUTTORE.toString())) {
			List<String> sportInsegnati = new ArrayList<String>();
			for (Sport sport : (List<Sport>) utenteDaConvertire.getProprieta().get("sportInsegnati")) {
				sportInsegnati.add(sport.getNome());
			}
			mappaAttributi.put("sportInsegnati", sportInsegnati);

			List<AppuntamentoDTO> listaAppuntamentiDTO = new ArrayList<AppuntamentoDTO>();
			for (Appuntamento app : ((Calendario) utenteDaConvertire.getProprieta().get("calendarioLezioni"))
					.getListaAppuntamenti()) {
				AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper().convertiInAppuntamentoDTO(app);
				listaAppuntamentiDTO.add(appDTO);
			}
			mappaAttributi.put("appuntamentiLezioni", listaAppuntamentiDTO);
		}

		/*
		 * Impostiamo gli attributi extra del manutentore
		 */
		if (utenteDTO.getRuoli().contains(TipoRuolo.MANUTENTORE.toString())) {
			List<AppuntamentoDTO> listaAppuntamentiManutentoreDTO = new ArrayList<AppuntamentoDTO>();
			for (Appuntamento app : ((Calendario) utenteDaConvertire.getProprieta().get("calendarioManutentore"))
					.getListaAppuntamenti()) {
				AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper().convertiInAppuntamentoDTO(app);
				listaAppuntamentiManutentoreDTO.add(appDTO);
			}
			mappaAttributi.put("appuntamentiManutentore", listaAppuntamentiManutentoreDTO);
		}

		utenteDTO.setAttributiExtra(mappaAttributi);

		return utenteDTO;
	}
	
	
	
}
