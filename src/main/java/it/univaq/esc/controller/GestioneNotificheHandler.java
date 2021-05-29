package it.univaq.esc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import it.univaq.esc.dtoObjects.NotificabileDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@RestController
@RequestMapping("/notifiche")
@NoArgsConstructor
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
public class GestioneNotificheHandler {

	@Autowired
	private RegistroPrenotazioni RegistroPrenotazioni;
	
	@Autowired
	private RegistroAppuntamenti RegistroAppuntamenti;
	
	@GetMapping("/dettagliNotifica")
	@CrossOrigin
	public @ResponseBody NotificabileDTO getDettagliNotifica(@RequestParam(name = "idEvento") Long idEvento) {
		Prenotazione prenotazione = getRegistroPrenotazioni().getPrenotazioneById(idEvento);
 
		PrenotazioneDTO prenDTO = new PrenotazioneDTO();
		Map<String, Object> mappaPrenotazione = new HashMap<String, Object>();
		mappaPrenotazione.put("prenotazione", prenotazione);
		List<Appuntamento> appuntamentiPrenotazione = getRegistroAppuntamenti().getAppuntamentiByPrenotazioneId(idEvento);
		mappaPrenotazione.put("appuntamentiPrenotazione", appuntamentiPrenotazione);
		prenDTO.impostaValoriDTO(mappaPrenotazione);
		
		return prenDTO;
	}
}
