package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.Notifica;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class NotificaDTO  implements IModelToDTO{

	private String messaggio;
	private String mittente;
	private Long idEvento;
	private boolean letta;
	
	
	@Override
	public void impostaValoriDTO(Object modelDaConvertire) {
		Notifica notifica = (Notifica)modelDaConvertire;
		setMessaggio(notifica.getMessaggio());
		setMittente(notifica.getMittente().getProprieta().get("nome") + " " + notifica.getMittente().getProprieta().get("cognome"));
		setIdEvento((Long)notifica.getEvento().getInfo().get("identificativo"));
		
	}
}
