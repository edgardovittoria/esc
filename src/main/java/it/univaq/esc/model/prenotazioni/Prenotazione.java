package it.univaq.esc.model.prenotazioni;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.TipoEventoNotificabile;
import it.univaq.esc.model.notifiche.Notificabile;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "prenotazioni")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPrenotazione")
@Getter @Setter @NoArgsConstructor
public class Prenotazione extends Notificabile{


    @ManyToOne()
    @JoinColumn()
    private UtentePolisportivaAbstract sportivoPrenotante;

    @Column
    private LocalDateTime oraDataPrenotazione;
  
    
    @OneToMany(mappedBy = "prenotazioneAssociata", cascade = CascadeType.ALL)
    //@JoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PrenotazioneSpecs> listaSpecifichePrenotazione = new ArrayList<PrenotazioneSpecs>();


    public Prenotazione(Integer lastIdPrenotazione){
        super(lastIdPrenotazione);
    }

    public Prenotazione(Integer lastIdPrenotazione, PrenotazioneSpecs prenotazioneSpecs) {
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
    
    public String getNomeSportAssociatoAllaPrenotazione() {
    	return this.getListaSpecifichePrenotazione().get(0).getSportAssociato().getNome();
    }

	@Override
	public Map<String, Object> getInfo() {
		Map<String, Object> infoPrenotazionMap = new HashMap<String, Object>();
		infoPrenotazionMap.put("tipoPrenotazione", this.getTipoPrenotazione());
		infoPrenotazionMap.put("sportNome", this.getNomeSportAssociatoAllaPrenotazione());
		infoPrenotazionMap.put("identificativo", this.getIdPrenotazione());
		infoPrenotazionMap.put("numeroIncontri", getNumeroIncontri());
		infoPrenotazionMap.put("modalitaPrenotazione", getModalitaPrenotazione());
		
		return infoPrenotazionMap;
	}
	
	public Integer getIdPrenotazione() {
		return this.getIdNotificabile();
	}
	
	public Integer getNumeroIncontri() {
		if(getTipoPrenotazione().equals(TipiPrenotazione.CORSO.toString())) {
			return getListaSpecifichePrenotazione().get(0).getPrenotazioniSpecsFiglie().size();
		}
		return getListaSpecifichePrenotazione().size();
	}

	@Override
	public String getTipoEventoNotificabile() {
		return TipoEventoNotificabile.PRENOTAZIONE.toString();
	}

	public String getModalitaPrenotazione() {
		return getListaSpecifichePrenotazione().get(0).getModalitaPrenotazione();
	}
    
}