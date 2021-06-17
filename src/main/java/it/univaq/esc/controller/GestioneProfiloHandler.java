package it.univaq.esc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.NotificaDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.SquadraDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice.This;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.notifiche.Notifica;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;

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
				.convertiInUtentePolisportivaDTO(registroUtentiPolisportiva.getUtenteByEmail(email));
		
		return sportivoDTO;
	}



	
	@GetMapping("/squadreSportivo")
	@CrossOrigin
	public List<SquadraDTO> getSquadreSportivoMembro(@RequestParam(name = "email") String email){
		UtentePolisportivaAbstract sportivo = getRegistroUtentiPolisportiva().getUtenteByEmail(email);
		return getSquadreDiCuiUtenteMembro(sportivo);
	}
	
	
	
	
	private List<SquadraDTO> getSquadreDiCuiUtenteMembro(UtentePolisportivaAbstract membro) {
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
