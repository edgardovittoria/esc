package it.univaq.esc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
import it.univaq.esc.dtoObjects.SquadraDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RestController
@RequestMapping("/aggiornaOpzioni")
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
public class GestioneProfiloHandler {

	@Resource(name = "MAPPER_SINGOLO_UTENTE")
	private MapperFactory mapperFactory;

	
	
	@Autowired
	private RegistroUtentiPolisportiva registroUtentiPolisportiva;

	@Autowired
	private RegistroPrenotazioni registroPrenotazioni;

	@Autowired
	private RegistroAppuntamenti registroAppuntamenti;

	@Autowired
	private RegistroSquadre registroSquadre;

	/**
	 * Ricava uno sportivo registrato nel sistema a partire dalla email associata,
	 * resituendolo nel relativo formato DTO.
	 * 
	 * @param email email dello sportivo da ricavare
	 * @return sportivo associato alla email passata come parametro
	 */
	@GetMapping("/sportivo")
	@CrossOrigin
	public @ResponseBody UtentePolisportivaDTO getDatiSportivo(@RequestParam(name = "email") String email) {
		UtentePolisportivaDTO sportivoDTO = getMapperFactory().getUtenteMapper()
				.convertiInUtentePolisportivaDTO(registroUtentiPolisportiva.trovaUtenteInBaseAllaSua(email));
		
		return sportivoDTO;
	}



	
	@GetMapping("/squadreSportivo")
	@CrossOrigin
	public List<SquadraDTO> getSquadreSportivoMembro(@RequestParam(name = "email") String email){
		UtentePolisportiva sportivo = getRegistroUtentiPolisportiva().trovaUtenteInBaseAllaSua(email);
		return getSquadreDiCuiUtenteMembro(sportivo);
	}
	
	
	
	
	private List<SquadraDTO> getSquadreDiCuiUtenteMembro(UtentePolisportiva membro) {
		List<SquadraDTO> listaSquadreDTO = new ArrayList<SquadraDTO>();
		for (Squadra squadra : getRegistroSquadre().getSquadrePerMembro(membro)) {
			SquadraDTO squadraDTO = getMapperFactory().getSquadraMapper().convertiInSquadraDTO(squadra);
			listaSquadreDTO.add(squadraDTO);
		}

		return listaSquadreDTO;
	}
	
	private void impostaMapperFactory(String modalitaPrenotazione) {
		setMapperFactory(BeanUtil.getBean("MAPPER_" + modalitaPrenotazione, MapperFactory.class));
	}

}
