package it.univaq.esc.controller.promuoviPolisportiva;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
import it.univaq.esc.dtoObjects.FormCreazioneStruttura;
import it.univaq.esc.dtoObjects.StrutturaPolisportivaDTO;
import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Pavimentazione;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.StrutturaPolisportiva;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.notifiche.Notifica;
import it.univaq.esc.model.notifiche.NotificaService;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.notifiche.TipoNotifica;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.TipoRuolo;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RestController
@RequestMapping("/promuoviPolisportiva")
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
public class PromuoviPolisportivaHandler {
	
	private MapperFactory mapperFactory;
	private StrutturaPolisportiva nuovaStruttura;
	private String tipoNuovaStruttura;
	private CreazioneNuovaStrutturaState statoCreazioneNuovaStruttura;
	private StatiCreazioneNuovaStrutturaFactory statiCreazioneNuovaStrutturaFactory;
	private RegistroImpianti registroImpianti;
	private ElementiPrenotazioneFactory elementiPrenotazioneFactory;
	private RegistroNotifiche registroNotifiche;
	private RegistroUtentiPolisportiva registroUtentiPolisportiva;
	private RegistroSport registroSport;

	public PromuoviPolisportivaHandler(RegistroImpianti registroImpianti, RegistroNotifiche registroNotifiche, RegistroUtentiPolisportiva registroUtentiPolisportiva, RegistroSport registroSport, StatiCreazioneNuovaStrutturaFactory statiCreazioneNuovaStrutturaFactory) {
		impostaAttributiControllerDipendentiDa(ModalitaPrenotazione.SINGOLO_UTENTE.toString());
		setRegistroImpianti(registroImpianti);
		setRegistroNotifiche(registroNotifiche);
		setRegistroUtentiPolisportiva(registroUtentiPolisportiva);
		setRegistroSport(registroSport);
		setStatiCreazioneNuovaStrutturaFactory(statiCreazioneNuovaStrutturaFactory);
	}
	
	private void impostaAttributiControllerDipendentiDa(String modalitaPrenotazione) {
		setElementiPrenotazioneFactory(this.chiedeAlContainerLaFactoryStatiRelativaAlla(modalitaPrenotazione));
		setMapperFactory(chiedeAlContainerLaMapperFactoryiRelativaAlla(modalitaPrenotazione));
	}

	private ElementiPrenotazioneFactory chiedeAlContainerLaFactoryStatiRelativaAlla(String modalitaPrenotazione) {
		return BeanUtil.getBean("ELEMENTI_PRENOTAZIONE_" + modalitaPrenotazione, ElementiPrenotazioneFactory.class);
	}

	private MapperFactory chiedeAlContainerLaMapperFactoryiRelativaAlla(String modalitaPrenotazione) {
		return BeanUtil.getBean("MAPPER_" + modalitaPrenotazione, MapperFactory.class);
	}
	
	@RolesAllowed("ROLE_DIRETTORE")
	@GetMapping("/avviaCreazioneStruttura")
	@CrossOrigin
	public Map<String, Object> avviaCreazioneNuovoImpianto(@RequestParam(name = "tipoNuovaStruttura") String tipoNuovaStruttura) {
		impostaAttributiDipendentiDal(tipoNuovaStruttura);
		Map<String, Object> mappaDatiCreazioneNuovaStruttura = statoCreazioneNuovaStruttura.getDatiPerCreazioneNuovaStruttura();
		return mappaDatiCreazioneNuovaStruttura;
		
	}
	
	private void impostaAttributiDipendentiDal(String tipoNuovaStruttura) {
		setTipoNuovaStruttura(tipoNuovaStruttura);
		setStatoCreazioneNuovaStruttura(statiCreazioneNuovaStrutturaFactory.getStatoCreazioneNuovaStrutturaInBaseAl(tipoNuovaStruttura));
	}
	
	
	@RolesAllowed("ROLE_DIRETTORE")
	@PostMapping("/riepilogoCreazioneStruttura")
	@CrossOrigin
	public StrutturaPolisportivaDTO getRiepilogoCreazioneStruttura(@RequestBody StrutturaPolisportivaDTO nuovaStrutturaDTO) {
		StrutturaPolisportiva nuovaStrutturaPolisportiva = getMapperFactory().getStrutturaPolisportivaMapper(tipoNuovaStruttura).convertiDTOInStrutturaPolisportiva(nuovaStrutturaDTO);
		setNuovaStruttura(nuovaStrutturaPolisportiva);
		return nuovaStrutturaDTO;
	}
	
	@RolesAllowed("ROLE_DIRETTORE")
	@PostMapping("/confermaCreazioneStruttura")
	@CrossOrigin
	public ResponseEntity confermaCreazioneStruttura() {
		statoCreazioneNuovaStruttura.creaNuovaStrutturaConfermata(nuovaStruttura);
		return new ResponseEntity(HttpStatus.CREATED);
	}
	
	
	@RolesAllowed("ROLE_DIRETTORE")
	@GetMapping("/messaggioNotificaCreazioneStruttura")
	@CrossOrigin
	public String getMessaggioNotificaCreazioneStruttura() {
		String messaggioNotifica = registroNotifiche.getMessaggioNotificaPerCreazione(nuovaStruttura);
		return messaggioNotifica;
	}
	
	@RolesAllowed("ROLE_DIRETTORE")
	@PostMapping("/invioNotificheCreazioneNuovaStruttura")
	@CrossOrigin
	public ResponseEntity inviaNotificheCreazioneNuovaStruttura(@RequestBody Map<String, String> mappaEmailDirettoreEMessaggioNotifica) {
		for(UtentePolisportiva utente : registroUtentiPolisportiva.getListaUtentiPolisportiva()) {
			statoCreazioneNuovaStruttura.creaNotificaCreazioneNuovaStrutturaConMessaggioPerSingoloUtente(mappaEmailDirettoreEMessaggioNotifica, utente, nuovaStruttura);
		}
		return new ResponseEntity(HttpStatus.CREATED);
	}
	
	
}
