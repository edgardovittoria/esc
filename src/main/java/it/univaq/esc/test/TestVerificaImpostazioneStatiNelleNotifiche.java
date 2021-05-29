package it.univaq.esc.test;



import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import it.univaq.esc.model.notifiche.NotificaImpiantoState;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Component
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE) @AllArgsConstructor
public class TestVerificaImpostazioneStatiNelleNotifiche {

	private RegistroNotifiche registroNotifiche;
	
	
	
	
	public void test() {
		
		
		getRegistroNotifiche().getListaNotifiche().forEach(
				(notifica) ->
				{
					System.out.println(notifica.getEvento().getInfo().get("tipoPrenotazione"));
					System.out.println(notifica.getStatoNotifica());
				}
				);
		
	}
}
