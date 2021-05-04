package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

@Entity
public class PrenotazioneImpiantoSpecs extends PrenotazioneSpecs {


    
    



    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<UtentePolisportivaAbstract> invitati = new ArrayList<UtentePolisportivaAbstract>();

    @Column
    private int numeroGiocatoriNonRegistratiAssociati = 0;


    @ManyToOne
    @JoinColumn
    private Impianto impiantoPrenotato;

    @ManyToOne()
    @JoinColumn()
    private UtentePolisportivaAbstract manutentore;
 
    

    public PrenotazioneImpiantoSpecs(){}

    public List<UtentePolisportivaAbstract> getListaInvitati() {
        return this.invitati;
    }

    public void invitaSportivi(List<UtentePolisportivaAbstract> listaSportiviDaInvitare) {
        getListaInvitati().addAll(listaSportiviDaInvitare);
    }

    public void setManutentore(UtentePolisportivaAbstract manutentore){
        this.manutentore = manutentore;
    }

    public UtentePolisportivaAbstract getManutentore(){
        return this.manutentore;
    }
    /**
     * @return int return the numeroGiocatoriNonRegistratiAssociati
     */
    public int getNumeroGiocatoriNonRegistratiAssociati() {
        return numeroGiocatoriNonRegistratiAssociati;
    }

    /**
     * @param numeroGiocatoriNonRegistratiAssociati the numeroGiocatoriNonRegistratiAssociati to set
     */
    public void setNumeroGiocatoriNonRegistratiAssociati(int numeroGiocatoriNonRegistratiAssociati) {
        this.numeroGiocatoriNonRegistratiAssociati = numeroGiocatoriNonRegistratiAssociati;
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
                case "invitati":
                    this.invitaSportivi((List<UtentePolisportivaAbstract>)mappaValori.get(chiave));
                    break;
                case "numeroGiocatoriNonIscritti" : 
                    this.setNumeroGiocatoriNonRegistratiAssociati((Integer)mappaValori.get(chiave));
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
        mappaValori.put("invitati", this.getListaInvitati());
        mappaValori.put("numeroGiocatoriNonIscritti", this.getNumeroGiocatoriNonRegistratiAssociati());
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
        return TipiPrenotazione.IMPIANTO.toString();
    }

}
