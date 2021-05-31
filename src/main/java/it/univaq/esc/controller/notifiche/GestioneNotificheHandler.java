package it.univaq.esc.controller.notifiche;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import it.univaq.esc.dtoObjects.NotificabileDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/notifiche")
@AllArgsConstructor
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
public class GestioneNotificheHandler {

	private FactoryDettagliNotificaStrategy factoryDettagliNotificaStrategy;
	
	
	@GetMapping("/dettagliNotifica")
	@CrossOrigin
	public @ResponseBody NotificabileDTO getDettagliNotifica(@RequestParam(name = "idEvento") Integer idEvento, @RequestParam(name = "tipoEventoNotificabile") String tipoEventoNotificabile) {
		
		return factoryDettagliNotificaStrategy.getStrategy(tipoEventoNotificabile).getDettagliNotifica(idEvento);
		
	}
}
