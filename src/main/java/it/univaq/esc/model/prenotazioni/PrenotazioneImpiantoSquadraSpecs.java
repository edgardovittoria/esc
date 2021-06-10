package it.univaq.esc.model.prenotazioni;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @Getter @Setter
public class PrenotazioneImpiantoSquadraSpecs extends PrenotazioneSpecs {

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Squadra> squadreInvitate = new ArrayList<Squadra>();

    
    @ManyToOne
    @JoinColumn
    private Impianto impiantoPrenotato;

    @ManyToOne()
    @JoinColumn()
    private UtentePolisportivaAbstract manutentore;
 
  
    public void invitaSquadre(List<Squadra> listaSquadreDaInvitare) {
        getSquadreInvitate().addAll(listaSquadreDaInvitare);
    }

    

    public Integer getIdImpiantoPrenotato(){
        return this.getImpiantoPrenotato().getIdImpianto();
    }

    @Override
    public void impostaValoriSpecificheExtraPrenotazione(Map<String, Object> mappaValori) {
        for(String chiave : mappaValori.keySet()){
            switch (chiave) {
                case "invitati":
                    invitaSquadre((List<Squadra>)mappaValori.get(chiave));
                    break;
                case "manutentore":
                    this.setManutentore((UtentePolisportivaAbstract)mappaValori.get(chiave));
                    break;
                case "impianto":
                    this.setImpiantoPrenotato((Impianto)mappaValori.get(chiave));   
                    break;  
                default:
                    break;
            }
        }
        
    }

    @Override
    public Map<String, Object> getValoriSpecificheExtraPrenotazione() {
        HashMap<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("squadreInvitate", this.getSquadreInvitate());
        mappaValori.put("manutentore", this.getManutentore());
        mappaValori.put("impianto", this.getImpiantoPrenotato());

        return mappaValori;
    }


    @Override
    public String getTipoPrenotazione() {
        return TipiPrenotazione.IMPIANTO.toString();
    }


}
