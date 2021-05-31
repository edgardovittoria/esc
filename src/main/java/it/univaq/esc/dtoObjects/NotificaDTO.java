package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.notifiche.NotificaService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class NotificaDTO  implements IModelToDTO{

	private String messaggio;
	private String mittente;
	private Integer idEvento;
	private String tipoEventoNotificabile;
	private boolean letta;
	
	
	@Override
	public void impostaValoriDTO(Object modelDaConvertire) {
		NotificaService notifica = (NotificaService)modelDaConvertire;
		setMessaggio(notifica.getMessaggio());
		setMittente(notifica.getMittente().getProprieta().get("nome") + " " + notifica.getMittente().getProprieta().get("cognome"));
		setIdEvento((Integer)notifica.getEvento().getInfo().get("identificativo"));
		setTipoEventoNotificabile(notifica.getEvento().getTipoEventoPrenotabile());
		
	}
}
