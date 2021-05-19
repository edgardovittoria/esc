package it.univaq.esc.model.prenotazioni;


import java.util.HashMap;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;

import it.univaq.esc.model.Impianto;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@NoArgsConstructor @Getter @Setter
public class PrenotazioneLezioneSpecs extends PrenotazioneSpecs{
    @ManyToOne()
    @JoinColumn()
    private UtentePolisportivaAbstract istruttore;

    @ManyToOne
    @JoinColumn
    private Impianto impiantoPrenotato;

    @ManyToOne()
    @JoinColumn()
    private UtentePolisportivaAbstract manutentore;
 

    public Integer getIdImpiantoPrenotato(){
        return this.getImpiantoPrenotato().getIdImpianto();
    }

    @Override
    public void impostaValoriSpecificheExtraPrenotazione(Map<String, Object> mappaValori) {
        for(String chiave : mappaValori.keySet()){
            switch (chiave) {
                case "istruttore":
                    this.setIstruttore((UtentePolisportivaAbstract)mappaValori.get(chiave));
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
        mappaValori.put("istruttore", this.getIstruttore());
        mappaValori.put("manutentore", this.getManutentore());
        mappaValori.put("impianto", this.getImpiantoPrenotato());

        return mappaValori;
    }



    // @Override
    // protected void setTipoPrenotazione() {
    //     this.tipoPrenotazione = TipiPrenotazione.IMPIANTO.toString();
    // }



    @Override
    public String getTipoPrenotazione() {
        return TipiPrenotazione.LEZIONE.toString();
    }

	

}
