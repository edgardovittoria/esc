package it.univaq.esc.controller.notifiche;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
import it.univaq.esc.dtoObjects.NotificaDTO;
import it.univaq.esc.dtoObjects.NotificabileDTO;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RestController
@RequestMapping("/notifiche")
@NoArgsConstructor
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
public class GestioneNotificheHandler {

	@Resource(name = "MAPPER_SINGOLO_UTENTE")
	private MapperFactory mapperFactory;
	
	@Autowired
	private FactoryDettagliNotificaStrategy factoryDettagliNotificaStrategy;
	
	@Autowired
	private RegistroNotifiche registroNotifiche;
	
	
	@GetMapping("/dettagliNotifica")
	@CrossOrigin
	public @ResponseBody NotificabileDTO getDettagliNotifica(@RequestParam(name = "idEvento") Integer idEvento, @RequestParam(name = "tipoEventoNotificabile") String tipoEventoNotificabile) {
		
		return factoryDettagliNotificaStrategy.getStrategy(tipoEventoNotificabile).getDettagliNotifica(idEvento);
		
	}
	
	
	@PatchMapping("/impostaNotificaLetta")
	@CrossOrigin
	public @ResponseBody NotificaDTO setNotificaLetta(@RequestBody Map<String, Object> mappaDati) {
		Integer idNotifica = (Integer) mappaDati.get("idNotifica");
		NotificaService notifica = getRegistroNotifiche().getNotificaById(idNotifica);
		
		if(notifica != null) {
			getRegistroNotifiche().impostaNotificaComeLetta(notifica);
			NotificaDTO notificaDTO = getMapperFactory().getNotificaMapper().convertiiNotificaDTO(notifica);
								
			return notificaDTO;
		}
		return null;
	}
}
