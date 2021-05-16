package it.univaq.esc.dtoObjects;


import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.Sport;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SportivoDTO implements IModelToDTO{

    private String nome;
    private String cognome;
    private String email;
    private List<String> sportPraticati = new ArrayList<String>();


    public void aggiungiSportPraticato(String nomeSport){
        if(!this.getSportPraticati().contains(nomeSport)){
            this.getSportPraticati().add(nomeSport);
        }
    }


    @Override
    public void impostaValoriDTO(Object modelDaConvertire){
        UtentePolisportivaAbstract sportivo = (UtentePolisportivaAbstract)modelDaConvertire;
        this.setNome((String)sportivo.getProprieta().get("nome"));
        this.setCognome((String)sportivo.getProprieta().get("cognome"));
        this.setEmail((String)sportivo.getProprieta().get("email"));
        List<String> sportPraticati = new ArrayList<String>();
        for(Sport sport : (List<Sport>)sportivo.getProprieta().get("sportPraticati")){
            sportPraticati.add(sport.getNome());
        }
        this.setSportPraticati(sportPraticati);
    }
    
}
