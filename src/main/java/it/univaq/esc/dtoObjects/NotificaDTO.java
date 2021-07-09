package it.univaq.esc.dtoObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class NotificaDTO {

	private Integer idNotifica;
	private String messaggio;
	private String mittente;
	private Integer idEvento;
	private String tipoEventoNotificabile;
	private String tipoNotifica;
	private SquadraDTO squadraDelMittente;
	private SquadraDTO squadraDelDestinatario;
	private boolean letta;
	
	
}
