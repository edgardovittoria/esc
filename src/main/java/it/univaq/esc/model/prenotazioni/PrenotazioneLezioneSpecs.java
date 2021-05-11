package it.univaq.esc.model.prenotazioni;


import java.util.HashMap;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;

import it.univaq.esc.model.Impianto;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
@Entity
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
 
    

    public PrenotazioneLezioneSpecs(){}

    

    public void setIstruttore(UtentePolisportivaAbstract istruttore){
        this.istruttore = istruttore;
    }

    public UtentePolisportivaAbstract getIstruttore(){
        return this.istruttore;
    }
    

    public void setManutentore(UtentePolisportivaAbstract manutentore){
        this.manutentore = manutentore;
    }

    public UtentePolisportivaAbstract getManutentore(){
        return this.manutentore;
    }
   

   

    public void setImpiantoPrenotato(Impianto impiantoPrenotato){
        this.impiantoPrenotato = impiantoPrenotato;
    }

    public Impianto getImpiantoPrenotato(){
        return this.impiantoPrenotato;
    }

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
