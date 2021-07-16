package it.univaq.esc.model.catalogoECosti.calcolatori;

import it.univaq.esc.model.Costo;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.StrutturaPolisportiva;
import it.univaq.esc.model.catalogoECosti.TipoCostoPrenotabile;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MINUTES;

@NoArgsConstructor
public class CalcolatoreCostoBase extends CalcolatoreCosto {

	@Override
	public Costo calcolaCosto(Appuntamento appuntamento) {
		LocalTime oraInizio = appuntamento.getOraInizioAppuntamento();
		LocalTime oraFine = appuntamento.getOraFineAppuntamento();
		Long minutiDurataAppuntamento = MINUTES.between(oraInizio, oraFine);
		StrutturaPolisportiva impiantoAppuntamento = appuntamento.getStrutturaPrenotata();
		String pavimentazione = null;
		if (impiantoAppuntamento instanceof Impianto) {
			pavimentazione = ((Impianto) impiantoAppuntamento).getTipoPavimentazione().toString();
		}
		Map<String, Costo> mappaCosti = appuntamento.getMappaCostiAppuntamento();
		Costo costo = new Costo();
		if (mappaCosti.containsKey(TipoCostoPrenotabile.COSTO_ORARIO.toString())) {
			Costo costoOrario = mappaCosti.get(TipoCostoPrenotabile.COSTO_ORARIO.toString());
			Costo costoPavimentazione = mappaCosti.get(pavimentazione);
			costo = ((costoOrario.sommaIl((costoOrario.moltiplicaPer(costoPavimentazione)).dividiPer(100)))
					.dividiPer(60)).moltiplicaPer(minutiDurataAppuntamento);
//            costo = ((costoOrario + (costoOrario*costoPavimentazione/100))/60)*minutiDurataAppuntamento;
		} else {
			Costo costoUnaTantum = mappaCosti.get(TipoCostoPrenotabile.COSTO_UNA_TANTUM.toString());
			costo = costoUnaTantum;
		}

		return costo;
	}

}
