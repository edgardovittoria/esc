package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SquadraDTO implements IModelToDTO{
	
	private Integer idSquadra;
	private String nome;
	private SportDTO sport;
	private List<UtentePolisportivaDTO> membri;
	private List<UtentePolisportivaDTO> amministratori;
	private List<AppuntamentoDTO> appuntamenti;

	@Override
	public void impostaValoriDTO(Object modelDaConvertire) {
		Squadra squadra = (Squadra) modelDaConvertire;
		
		setIdSquadra(squadra.getIdSquadra());
		setNome(squadra.getNome());
		
		SportDTO sportDTO = new SportDTO();
		sportDTO.impostaValoriDTO(squadra.getSport());
		setSport(sportDTO);
		
		List<UtentePolisportivaDTO> membriDTO = new ArrayList<UtentePolisportivaDTO>();
		for(UtentePolisportivaAbstract membro : squadra.getMembri()) {
			UtentePolisportivaDTO membroDTO = new UtentePolisportivaDTO();
			membroDTO.impostaValoriDTO(membro);
			membriDTO.add(membroDTO);
		}
		setMembri(membriDTO);
		
		
		List<UtentePolisportivaDTO> amministratoriDTO = new ArrayList<UtentePolisportivaDTO>();
		for(UtentePolisportivaAbstract amministratore : squadra.getAmministratori()) {
			UtentePolisportivaDTO amministratoreDTO = new UtentePolisportivaDTO();
			amministratoreDTO.impostaValoriDTO(amministratore);
			amministratoriDTO.add(amministratoreDTO);
		}
		setAmministratori(amministratoriDTO);
		
		
		List<AppuntamentoDTO> appuntamentiDTO = new ArrayList<AppuntamentoDTO>();
		for(Appuntamento appuntamento : squadra.getCalendarioSquadra().getListaAppuntamenti()) {
			AppuntamentoDTO appuntamentoDTO = new AppuntamentoDTO();
			appuntamentoDTO.impostaValoriDTO(appuntamento);
			appuntamentiDTO.add(appuntamentoDTO);
		}
		setAppuntamenti(appuntamentiDTO);
	}

}
