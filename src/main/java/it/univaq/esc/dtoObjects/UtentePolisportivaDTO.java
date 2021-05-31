package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.utenti.TipoRuolo;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UtentePolisportivaDTO implements IModelToDTO{
	
	private String nome;
	private String cognome;
	private String email;
	private List<String> ruoli;
	private Map<String, Object> attributiExtra;

	@Override
	public void impostaValoriDTO(Object modelDaConvertire) {
		UtentePolisportivaAbstract utente = (UtentePolisportivaAbstract)modelDaConvertire;
		setNome((String)utente.getProprieta().get("nome"));
		setCognome((String)utente.getProprieta().get("cognome"));
		setEmail((String)utente.getProprieta().get("email"));
		setRuoli(utente.getRuoliUtentePolisportiva());
		
		Map<String, Object> mappaAttributi = new HashMap<String, Object>();
		
		/*
		 * Impostiamo gli attributi extra dello sportivo.
		 */
		if(ruoli.contains(TipoRuolo.SPORTIVO.toString())) {
			List<String> sportPraticati = new ArrayList<String>();
			for(Sport sport : (List<Sport>)utente.getProprieta().get("sportPraticati")){
	            sportPraticati.add(sport.getNome());
	        }
			mappaAttributi.put("sportPraticati", sportPraticati);
			mappaAttributi.put("moroso", (Boolean)utente.getProprieta().get("moroso"));
		}
		
		
		/*
		 * Impostiamo gli attributi extra dell'istruttore
		 */
		if(ruoli.contains(TipoRuolo.ISTRUTTORE.toString())) {
			List<String> sportInsegnati = new ArrayList<String>();
			for(Sport sport : (List<Sport>)utente.getProprieta().get("sportInsegnati")){
	            sportInsegnati.add(sport.getNome());
	        }
			mappaAttributi.put("sportInsegnati", sportInsegnati);
			
			List<AppuntamentoDTO> listaAppuntamentiDTO = new ArrayList<AppuntamentoDTO>();
	        for(Appuntamento app : ((Calendario)utente.getProprieta().get("calendarioLezioni")).getListaAppuntamenti()){
	            AppuntamentoDTO appDTO = new AppuntamentoDTO();
	            appDTO.impostaValoriDTO(app);
	            listaAppuntamentiDTO.add(appDTO);
	        }
	        mappaAttributi.put("appuntamentiLezioni", listaAppuntamentiDTO);
		}
		
		setAttributiExtra(mappaAttributi);
		
	}

}
