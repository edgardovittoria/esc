package it.univaq.esc.dtoObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class PrenotazioneDTO extends NotificabileDTO{
	
	private Integer idPrenotazione;
	private UtentePolisportivaDTO sportivoPrenotante;
	private List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();
	private Map<String, Object> infoGeneraliEvento = new HashMap<String, Object>();

	public void aggiungiAppuntamento(AppuntamentoDTO appuntamento) {
		this.getAppuntamenti().add(appuntamento);
	}

	

}
