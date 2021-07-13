package it.univaq.esc.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.OrarioAppuntamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Calendario {

    private List<Appuntamento> listaAppuntamenti = new ArrayList<Appuntamento>();



    /**
     * Aggiunge un appuntamento al calendario, a condizione che non si sovrapponga agli appuntamenti già presenti.
     * @param appuntamentoDaAggiungere appuntamento da aggiungere al calendario
     */
    public void aggiungiAppuntamento(Appuntamento appuntamentoDaAggiungere) {
        if (!this.sovrapponeA(appuntamentoDaAggiungere)) {
            this.getListaAppuntamenti().add(appuntamentoDaAggiungere);
        }
    }
    
    public void aggiungiListaDi(List<Appuntamento> nuoviAppuntamenti) {
    	nuoviAppuntamenti.forEach((appuntamento) -> aggiungiAppuntamento(appuntamento));
    }

    /**
     * Crea un appuntamento con i dati passati come parametri, dopodiché lo aggiunge al calendario se non si sovrappone
     * agli appuntamenti esistenti.
     * @param dataOraInizioAppuntamento data e ora di inizio dell'appuntamento da aggiungere.
     * @param dataOraFineAppuntamento data e ora di fine dell'appuntamento da aggiungere.
     * @param prenotazioneSpecs specifica di prenotazione da associare all'appuntamento da aggiungere.
     */
//    public void aggiungiAppuntamento(LocalDateTime dataOraInizioAppuntamento, LocalDateTime dataOraFineAppuntamento,
//            PrenotazioneSpecs prenotazioneSpecs) {
//
//        Appuntamento appuntamentoDaAggiungere = new Appuntamento(dataOraInizioAppuntamento, dataOraFineAppuntamento,
//                prenotazioneSpecs);
//        if (!this.sovrapponeA(appuntamentoDaAggiungere)) {
//            this.getListaAppuntamenti().add(appuntamentoDaAggiungere);
//        }
//
//    }

    /**
     * Verifica se due calendari si sovrappongono almeno in un appuntamento
     * @param calendarioDiCuiVerificareSovrapposizione calendario di cui verificare
     *                                                 la sovrapposizione
     * @return true se i calendari si sovrappongono almeno su un appuntamento, false
     *         altrimenti.
     */
    public boolean sovrapponeA(Calendario calendarioDiCuiVerificareSovrapposizione) {
        boolean calendariSiSovrappongono = false;

        for (Appuntamento appuntamento : calendarioDiCuiVerificareSovrapposizione.getListaAppuntamenti()) {
            if (!calendariSiSovrappongono) {
                calendariSiSovrappongono = this.sovrapponeA(appuntamento);
            }
        }
        return calendariSiSovrappongono;
    }

    /**
     * Verifica se un appuntamento si sovrappone con quelli del calendario in
     * oggetto
     * 
     * @param appuntamentoDiCuiVerificareSovrapposizione appuntamento di cui
     *                                                   verificare la
     *                                                   sovrapposizione
     * @return true se il calendario si sovrappone almeno su un appuntamento con
     *         quello da verificare, false altrimenti.
     */
    public boolean sovrapponeA(Appuntamento appuntamentoDiCuiVerificareSovrapposizione) {
        boolean calendarioSiSovrappone = false;
        if (!this.getListaAppuntamenti().isEmpty()) {
            for (Appuntamento appuntamento : this.getListaAppuntamenti()) {
                if (!calendarioSiSovrappone) {
                    calendarioSiSovrappone = appuntamento.sovrapponeA(appuntamentoDiCuiVerificareSovrapposizione);
                }
            }
        }
        return calendarioSiSovrappone;
    }

    /**
     * Verifica se il calendario ha un appuntamento che si sovrappone all'orario passato come parametro.
     * Restituisce true in caso di sovrapposizione, false altrimenti.
     * @param oraInizio data e ora iniziali dell'orario da verificare.
     * @param oraFine data e ora finali dell'orario da verificare.
     * @return true in caso di sovrapposizione, false altrimenti.
     */
    public boolean sovrapponeA(LocalDate dataAppuntamento, LocalTime oraInizio, LocalTime oraFine) {
        boolean calendarioSiSovrappone = false;
        if (!this.getListaAppuntamenti().isEmpty()) {
            for (Appuntamento appuntamento : this.getListaAppuntamenti()) {
                if (!calendarioSiSovrappone) {
                    calendarioSiSovrappone = appuntamento.sovrapponeA(dataAppuntamento, oraInizio, oraFine);
                }
            }
        }
        return calendarioSiSovrappone;
    }

    public boolean sovrapponeA(OrarioAppuntamento orarioAppuntamento) {
    	return sovrapponeA(orarioAppuntamento.getDataOrarioAppuntamento(), orarioAppuntamento.getOraInizio(), orarioAppuntamento.getOraFine());
    }
    
    /**
     * Unisce gli appuntamenti di un calendario passato come parametro a quelli del calendario esistente, 
     * se i due non si sovrappongono.
     * @param calendarioDaUnire calendario da unire a quello preesistente.
     */
    public void unisciCalendario(Calendario calendarioDaUnire) {
        if (!this.sovrapponeA(calendarioDaUnire)) {
            this.getListaAppuntamenti().addAll(calendarioDaUnire.getListaAppuntamenti());
        }
    }
   
    
    public boolean ha(Appuntamento appuntamento) {
    	for(Appuntamento appuntamentoCalendario : getListaAppuntamenti()) {
    		if(appuntamentoCalendario.isEqual(appuntamento)) {
    			return true;
    			
    		}
    	}
    	return false;
    }

}