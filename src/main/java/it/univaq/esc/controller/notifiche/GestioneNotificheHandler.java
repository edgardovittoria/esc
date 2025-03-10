package it.univaq.esc.controller.notifiche;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
import it.univaq.esc.dtoObjects.NotificaDTO;
import it.univaq.esc.dtoObjects.NotificabileDTO;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifiche")
@NoArgsConstructor
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
public class GestioneNotificheHandler {

	@Resource(name = "MAPPER_SINGOLO_UTENTE")
	private MapperFactory mapperFactory;

	@Autowired
	private FactoryDettagliNotificaStrategy factoryDettagliNotificaStrategy;

	@Autowired
	private RegistroNotifiche registroNotifiche;

	@Autowired
	private RegistroUtentiPolisportiva RegistroUtentiPolisportiva;

	@GetMapping("/dettagliNotifica")
	@CrossOrigin
	public @ResponseBody NotificabileDTO getDettagliNotifica(@RequestParam(name = "idEvento") Integer idEvento,
			@RequestParam(name = "tipoEventoNotificabile") String tipoEventoNotificabile) {

		return factoryDettagliNotificaStrategy.getStrategy(tipoEventoNotificabile).getDettagliNotifica(idEvento);

	}

	@PatchMapping("/impostaNotificaLetta")
	@CrossOrigin
	public @ResponseBody NotificaDTO setNotificaLetta(@RequestBody Map<String, Object> mappaDati) {
		Integer idNotifica = (Integer) mappaDati.get("idNotifica");
		NotificaService notifica = getRegistroNotifiche().getNotificaById(idNotifica);

		impostaMapperFactory(notifica.getModalitaNotifica());

		notifica.impostaComeLetta();
		getRegistroNotifiche().aggiornaNotificaSuDatabase(notifica);
		NotificaDTO notificaDTO = getMapperFactory().getNotificaMapper().convertiInNotificaDTO(notifica);
		return notificaDTO;

	}

	private void impostaMapperFactory(String modalitaPrenotazione) {
		setMapperFactory(BeanUtil.getBean("MAPPER_" + modalitaPrenotazione, MapperFactory.class));
	}

	@GetMapping("/notificheUtente")
	@CrossOrigin
	public @ResponseBody List<NotificaDTO> getNotificheUtente(@RequestParam(name = "email") String email) {
		UtentePolisportiva utente = getRegistroUtentiPolisportiva().trovaUtenteInBaseAllaSua(email);
		return getNotificheInFormatoDTODelloSpecifico(utente);
	}

	private List<NotificaDTO> getNotificheInFormatoDTODelloSpecifico(UtentePolisportiva destinatario) {
		List<NotificaDTO> notificheDtos = new ArrayList<NotificaDTO>();
		for (NotificaService notifica : getRegistroNotifiche().getNotifichePerDestinatario(destinatario)) {
			impostaMapperFactory(notifica.getModalitaNotifica());
			NotificaDTO notificaDTO = getMapperFactory().getNotificaMapper().convertiInNotificaDTO(notifica);

			notificheDtos.add(notificaDTO);
		}

		return notificheDtos;
	}

}
