package it.univaq.esc.model.prenotazioni;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.Notificabile;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice.This;


@Entity
@Table(name = "prenotazioni")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPrenotazione")
@Getter @Setter @NoArgsConstructor
public class Prenotazione extends Notificabile{
//    @Id
//    private Long idPrenotazione;
    

    @ManyToOne()
    @JoinColumn()
    private UtentePolisportivaAbstract sportivoPrenotante;

    @Column
    private LocalDateTime oraDataPrenotazione;
  
    
    @OneToMany(mappedBy = "prenotazioneAssociata", cascade = CascadeType.ALL)
    //@JoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PrenotazioneSpecs> listaSpecifichePrenotazione = new ArrayList<PrenotazioneSpecs>();


    public Prenotazione(Long lastIdPrenotazione){
        super(lastIdPrenotazione);
    }

    public Prenotazione(Long lastIdPrenotazione, PrenotazioneSpecs prenotazioneSpecs) {
        super(lastIdPrenotazione);
        //aggiungiQuotaPartecipazione(sportivoPrenotante, 0, false);
        this.getListaSpecifichePrenotazione().add(prenotazioneSpecs);
    }

    
    public void aggiungiSpecifica(PrenotazioneSpecs specifica){
        this.getListaSpecifichePrenotazione().add(specifica);
    }


    public void impostaValoriListaSpecifichePrenotazione(List<Map<String, Object>> listaMappeValori){
        for(PrenotazioneSpecs specifica : this.getListaSpecifichePrenotazione()){
            specifica.impostaValoriSpecificheExtraPrenotazione(listaMappeValori.get(this.getListaSpecifichePrenotazione().indexOf(specifica)));
        }
    }

    public void impostaValoriSpecificheExtraSingolaPrenotazioneSpecs(Map<String, Object> mappaValori, PrenotazioneSpecs specifica){
        specifica.impostaValoriSpecificheExtraPrenotazione(mappaValori);
    }

    public Object getSingolaSpecificaExtra(String etichettaSpecifica, PrenotazioneSpecs specifica){
        return specifica.getValoriSpecificheExtraPrenotazione().get(etichettaSpecifica);
    }

    public Map<String, Object> getSpecificheExtraSingolaPrenotazioneSpecs(PrenotazioneSpecs specifica){
        return specifica.getValoriSpecificheExtraPrenotazione();
    }


    public String getTipoPrenotazione(){
    
        return this.getListaSpecifichePrenotazione().get(0).getTipoPrenotazione();
    }
    
    public Sport getSportAssociatoAllaPrenotazione() {
    	return this.getListaSpecifichePrenotazione().get(0).getSportAssociato();
    }

	@Override
	public Map<String, Object> getInfo() {
		Map<String, Object> infoPrenotazionMap = new HashMap<String, Object>();
		infoPrenotazionMap.put("tipoPrenotazione", this.getTipoPrenotazione());
		infoPrenotazionMap.put("sport", this.getSportAssociatoAllaPrenotazione());
		infoPrenotazionMap.put("identificativo", this.getIdPrenotazione());
		
		return infoPrenotazionMap;
	}
	
	public Long getIdPrenotazione() {
		return this.getIdNotificabile();
	}

    
}