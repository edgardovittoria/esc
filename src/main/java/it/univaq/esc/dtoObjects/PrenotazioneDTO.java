package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlSeeAlso;

import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PrenotazioneDTO implements IModelToDTO {

	private SportivoDTO sportivoPrenotante;
	private List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();
	private Map<String, Object> infoGeneraliPrenotazioneMap = new HashMap<String, Object>();

	public void aggiungiAppuntamento(AppuntamentoDTO appuntamento) {
		this.getAppuntamenti().add(appuntamento);
	}

	@Override
	public void impostaValoriDTO(Object modelDaConvertire) {
		Map<String, Object> mappa = (HashMap<String, Object>) modelDaConvertire;
		Prenotazione prenotazione = (Prenotazione) mappa.get("prenotazione");
		List<Appuntamento> listaAppuntamentiPrenotazione = (List<Appuntamento>) mappa.get("appuntamentiPrenotazione");
		this.sportivoPrenotante = new SportivoDTO();
		this.sportivoPrenotante.impostaValoriDTO(prenotazione.getSportivoPrenotante());

		for (Appuntamento app : listaAppuntamentiPrenotazione) {
			AppuntamentoDTO appDTO = new AppuntamentoDTO();
			appDTO.impostaValoriDTO(app);
			this.aggiungiAppuntamento(appDTO);
		}
		if (mappa.containsKey("infoGeneraliEvento")) {
			this.setInfoGeneraliPrenotazioneMap((Map<String, Object>) mappa.get("infoGeneraliEvento"));
		}

	}

}
