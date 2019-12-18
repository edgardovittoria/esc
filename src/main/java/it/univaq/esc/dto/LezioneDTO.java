package it.univaq.esc.dto;

import java.util.Calendar;

import org.javamoney.moneta.Money;

public class LezioneDTO {
    private Money costoLezione;
	
	private ImpiantoDTO impianto;
	
	private IstruttoreDTO istruttore;
	
	private Calendar calendario;

    /**
     * @return Money return the costoLezione
     */
    public Money getCostoLezione() {
        return costoLezione;
    }

    /**
     * @param costoLezione the costoLezione to set
     */
    public void setCostoLezione(Money costoLezione) {
        this.costoLezione = costoLezione;
    }

    /**
     * @return ImpiantoDTO return the impianto
     */
    public ImpiantoDTO getImpianto() {
        return impianto;
    }

    /**
     * @param impianto the impianto to set
     */
    public void setImpianto(ImpiantoDTO impianto) {
        this.impianto = impianto;
    }

    /**
     * @return IstruttoreDTO return the istruttore
     */
    public IstruttoreDTO getIstruttore() {
        return istruttore;
    }

    /**
     * @param istruttore the istruttore to set
     */
    public void setIstruttore(IstruttoreDTO istruttore) {
        this.istruttore = istruttore;
    }

    /**
     * @return Calendar return the calendario
     */
    public Calendar getCalendario() {
        return calendario;
    }

    /**
     * @param calendario the calendario to set
     */
    public void setCalendario(Calendar calendario) {
        this.calendario = calendario;
    }

}