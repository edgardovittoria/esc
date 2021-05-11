package it.univaq.esc.dtoObjects;


import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

public class PrenotazioneLezioneSpecsDTO extends PrenotazioneSpecsDTO {
    
    private IstruttoreDTO istruttore;
   
    private Integer idImpiantoPrenotato;
    private String pavimentazioneImpianto;

    public PrenotazioneLezioneSpecsDTO(){}

    public IstruttoreDTO getIstruttore() {
        return istruttore;
    }

    public void setIstruttore(IstruttoreDTO istruttore) {
        this.istruttore = istruttore;
    }

    public String getPavimentazioneImpianto() {
        return pavimentazioneImpianto;
    }

    public void setPavimentazioneImpianto(String pavimentazioneImpianto) {
        this.pavimentazioneImpianto = pavimentazioneImpianto;
    }

    public Integer getIdImpiantoPrenotato() {
        return idImpiantoPrenotato;
    }

    public void setIdImpiantoPrenotato(Integer impiantoPrenotato) {
        this.idImpiantoPrenotato = impiantoPrenotato;
    }

     

   

    @Override
    public void impostaValoriDTO(PrenotazioneSpecs specifica) {
        
        IstruttoreDTO istDTO = new IstruttoreDTO();
        istDTO.impostaValoriDTO((UtentePolisportivaAbstract)specifica.getValoriSpecificheExtraPrenotazione().get("istruttore"));
        this.setIstruttore(istDTO);
        
       
        
        this.setIdImpiantoPrenotato(((Impianto)specifica.getValoriSpecificheExtraPrenotazione().get("impianto")).getIdImpianto());
        this.setPavimentazioneImpianto(((Impianto)specifica.getValoriSpecificheExtraPrenotazione().get("impianto")).getTipoPavimentazione().toString());
        super.impostaValoriDTO(specifica);
        
    }



    

    
}

