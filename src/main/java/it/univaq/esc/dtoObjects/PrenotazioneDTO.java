package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PrenotazioneDTO extends NotificabileDTO implements IModelToDTO{
	
	private Integer idPrenotazione;
	private UtentePolisportivaDTO sportivoPrenotante;
	private List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();
	private Map<String, Object> infoGeneraliEvento = new HashMap<String, Object>();

	public void aggiungiAppuntamento(AppuntamentoDTO appuntamento) {
		this.getAppuntamenti().add(appuntamento);
	}

	@Override
	public void impostaValoriDTO(Object modelDaConvertire) {
		Map<String, Object> mappa = (HashMap<String, Object>) modelDaConvertire;
		Prenotazione prenotazione = (Prenotazione) mappa.get("prenotazione");
		List<Appuntamento> listaAppuntamentiPrenotazione = (List<Appuntamento>) mappa.get("appuntamentiPrenotazione");
		this.setSportivoPrenotante(new UtentePolisportivaDTO());
		this.getSportivoPrenotante().impostaValoriDTO(prenotazione.getSportivoPrenotante());
		this.setIdPrenotazione(prenotazione.getIdPrenotazione());
		
		for (Appuntamento app : listaAppuntamentiPrenotazione) {
			AppuntamentoDTO appDTO = new AppuntamentoDTO();
			appDTO.impostaValoriDTO(app);
			this.aggiungiAppuntamento(appDTO);
		}
		if (mappa.containsKey("infoGeneraliEvento")) {
			this.setInfoGeneraliEvento((Map<String, Object>) mappa.get("infoGeneraliEvento"));
		}
		
		setTipoEventoNotificabile(prenotazione.getTipoEventoPrenotabile());

	}

}
