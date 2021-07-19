package it.univaq.esc.model.catalogoECosti.calcolatori;

import java.util.Map;

import it.univaq.esc.model.Costo;
import it.univaq.esc.model.catalogoECosti.TipoCostoPrenotabile;
import it.univaq.esc.model.prenotazioni.Appuntamento;

public class CalcolatoreCostoScontoPercentuale  extends CalcolatoreCosto{

	@Override
	public Costo calcolaCosto(Appuntamento appuntamento) {
		Map<String, Costo> mappaCosti = appuntamento.getMappaCostiAppuntamento();
		Costo scontoPercentuale = mappaCosti.get(TipoCostoPrenotabile.COSTO_SCONTO_PERCENTUALE.toString());
		Costo costoAttuale = appuntamento.getCostoAppuntamento();
		Costo costoAggiornato = costoAttuale.sottraiIl(costoAttuale.moltiplicaPer(scontoPercentuale).dividiPer(100));
		return costoAggiornato;
	}

}
