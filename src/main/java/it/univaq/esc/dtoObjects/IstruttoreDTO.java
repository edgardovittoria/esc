package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class IstruttoreDTO implements IModelToDTO{
    
    private String nome;
    private String cognome;
    private String email;
    private List<String> sportPraticati = new ArrayList<String>();
    private List<String> sportInsegnati = new ArrayList<String>();
    private List<AppuntamentoDTO> appuntamentiLezioni = new ArrayList<AppuntamentoDTO>();


    
    public void aggiungiSportPraticato(String nomeSport){
        if(!this.getSportPraticati().contains(nomeSport)){
            this.getSportPraticati().add(nomeSport);
        }
    }

    public void aggiungiSportInsegnato(String nomeSport){
        if(!this.getSportInsegnati().contains(nomeSport)){
            this.getSportInsegnati().add(nomeSport);
        }
    }


    @Override
    public void impostaValoriDTO(Object modelDaConvertire){
        UtentePolisportivaAbstract sportivo = (UtentePolisportivaAbstract)modelDaConvertire;
        this.setNome((String)sportivo.getProprieta().get("nome"));
        this.setCognome((String)sportivo.getProprieta().get("cognome"));
        this.setEmail((String)sportivo.getProprieta().get("email"));
        
        for(Sport sport : (List<Sport>)sportivo.getProprieta().get("sportPraticati")){
            this.aggiungiSportPraticato(sport.getNome());
        }
        
       
        for(Sport sport : (List<Sport>)sportivo.getProprieta().get("sportInsegnati")){
            this.aggiungiSportInsegnato(sport.getNome());
        }

        List<AppuntamentoDTO> listaAppuntamentiDTO = new ArrayList<AppuntamentoDTO>();
        for(Appuntamento app : ((Calendario)sportivo.getProprieta().get("calendarioLezioni")).getListaAppuntamenti()){
            AppuntamentoDTO appDTO = new AppuntamentoDTO();
            appDTO.impostaValoriDTO(app);
            listaAppuntamentiDTO.add(appDTO);
        }
        this.setAppuntamentiLezioni(listaAppuntamentiDTO);
    }


}
