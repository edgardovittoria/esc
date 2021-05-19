package it.univaq.esc.dtoObjects;

import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor
public class FormCreaCorso implements IFormPrenotabile{

	private FormPrenotaLezioneDTO formLezione;
	private List<String> invitatiCorso;
	private Integer numeroMinimoPartecipanti;
	private Integer numeroMassimoPartecipanti;
	private Float costoPerPartecipante;
	
	
	
	@Override
	public HashMap<String, Object> getValoriForm() {
		HashMap<String, Object> mappaValori = this.getFormLezione().getValoriForm();
		mappaValori.put("invitati", this.getInvitatiCorso());
		mappaValori.put("numeroMinimoPartecipanti", getNumeroMinimoPartecipanti());
		mappaValori.put("numeroMassimoPartecipanti", getNumeroMassimoPartecipanti());
		mappaValori.put("costoPerPartecipante", getCostoPerPartecipante());
		
		return mappaValori;
	}
}
