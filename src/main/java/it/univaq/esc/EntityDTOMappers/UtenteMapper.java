package it.univaq.esc.EntityDTOMappers;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.utenti.TipoRuolo;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Singleton
@NoArgsConstructor
public class UtenteMapper extends EntityDTOMapper {

	public UtentePolisportivaDTO convertiInUtentePolisportivaDTO(UtentePolisportiva utenteDaConvertire) {
		UtentePolisportivaDTO utenteDTO = new UtentePolisportivaDTO();

		utenteDTO.setNome(utenteDaConvertire.getNome());
		utenteDTO.setCognome(utenteDaConvertire.getCognome());
		utenteDTO.setEmail(utenteDaConvertire.getEmail());
		utenteDTO.setRuoli(utenteDaConvertire.getRuoli());

		Map<String, Object> mappaAttributi = new HashMap<String, Object>();

		/*
		 * Impostiamo gli attributi extra dello sportivo.
		 */
		if (utenteDaConvertire.is(TipoRuolo.SPORTIVO)) {
			mappaAttributi.put("sportPraticati", utenteDaConvertire.comeSportivo().getSportPraticati());
			mappaAttributi.put("moroso", utenteDaConvertire.comeSportivo().isMoroso());

			List<AppuntamentoDTO> appuntamentiSportivo = new ArrayList<AppuntamentoDTO>();
			for (Appuntamento app : utenteDaConvertire.comeSportivo().getListaAppuntamenti()) {
				impostaMapperFactory(app.getModalitaPrenotazione());
				AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper(app.getTipoPrenotazione().toString())
						.convertiInAppuntamentoDTO(app);
				appuntamentiSportivo.add(appDTO);
			}
			mappaAttributi.put("appuntamentiSportivo", appuntamentiSportivo);

		}

		/*
		 * Impostiamo gli attributi extra dell'istruttore
		 */
		if (utenteDaConvertire.is(TipoRuolo.ISTRUTTORE)) {
			mappaAttributi.put("sportInsegnati", utenteDaConvertire.comeIstruttore().getNomiSportInsegnati());

			List<AppuntamentoDTO> listaAppuntamentiDTO = new ArrayList<AppuntamentoDTO>();
			for (Appuntamento app : utenteDaConvertire.comeIstruttore().getLezioni()) {
				impostaMapperFactory(app.getModalitaPrenotazione());
				AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper(app.getTipoPrenotazione().toString())
						.convertiInAppuntamentoDTO(app);
				listaAppuntamentiDTO.add(appDTO);
			}
			mappaAttributi.put("appuntamentiLezioni", listaAppuntamentiDTO);
		}

		/*
		 * Impostiamo gli attributi extra del manutentore
		 */
		if (utenteDaConvertire.is(TipoRuolo.MANUTENTORE)) {
			List<AppuntamentoDTO> listaAppuntamentiManutentoreDTO = new ArrayList<AppuntamentoDTO>();
			for (Appuntamento app : utenteDaConvertire.comeManutentore().getListaAppuntamenti()) {
				impostaMapperFactory(app.getModalitaPrenotazione());
				AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper(app.getTipoPrenotazione().toString())
						.convertiInAppuntamentoDTO(app);
				listaAppuntamentiManutentoreDTO.add(appDTO);
			}
			mappaAttributi.put("appuntamentiManutentore", listaAppuntamentiManutentoreDTO);
		}

		utenteDTO.setAttributiExtra(mappaAttributi);

		return utenteDTO;
	}

}
