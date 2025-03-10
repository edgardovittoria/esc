package it.univaq.esc.model.catalogoECosti;

import it.univaq.esc.model.Costo;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.TipoPrenotazione;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component(value = "PRENOTABILE_DESCRIZIONE_BUILDER")
@NoArgsConstructor
@Getter
@Setter
public class PrenotabileDescrizioneBuilder {

	private PrenotabileDescrizione prenotabileDescrizione;

	public PrenotabileDescrizioneBuilder creaNuovaDescrizione() {
			setPrenotabileDescrizione(new PrenotabileDescrizione());
		return this;
	}

	public PrenotabileDescrizioneBuilder impostaSport(Sport sportDaImpostare) {
		this.getPrenotabileDescrizione().setSportAssociato(sportDaImpostare);
		return this;
	}

	public PrenotabileDescrizioneBuilder impostaTipoPrenotazione(TipoPrenotazione tipoPrenotazione) {
		this.getPrenotabileDescrizione().setTipoPrenotazione(tipoPrenotazione);
		return this;
	}

	public PrenotabileDescrizioneBuilder impostaSogliaMinimaPartecipanti(Integer numeroMinimoPartecipanti) {
		this.getPrenotabileDescrizione().setMinimoNumeroPartecipanti(numeroMinimoPartecipanti);
		return this;
	}

	public PrenotabileDescrizioneBuilder impostaSogliaMassimaPartecipanti(Integer numeroMassimoPartecipanti) {
		this.getPrenotabileDescrizione().setMassimoNumeroPartecipanti(numeroMassimoPartecipanti);
		return this;
	}

	public PrenotabileDescrizioneBuilder impostaModalitaPrenotazioneComeSingoloUtente() {
		this.getPrenotabileDescrizione().setModalitaPrenotazione(ModalitaPrenotazione.SINGOLO_UTENTE);
		return this;
	}

	public PrenotabileDescrizioneBuilder impostaModalitaPrenotazioneComeSquadra() {
		this.getPrenotabileDescrizione().setModalitaPrenotazione(ModalitaPrenotazione.SQUADRA);
		return this;
	}

	public PrenotabileDescrizioneBuilder impostaDescrizione(String descrizioneEvento) {
		getPrenotabileDescrizione().setDescrizione(descrizioneEvento);

		return this;
	}

	public PrenotabileDescrizioneBuilder impostaCostoOrario(Costo costoDaImpostare) {
		CostoPrenotabile costoOrario = new CostoPrenotabile();
		costoOrario.setCosto(costoDaImpostare);
		costoOrario.setTipoCosto(TipoCostoPrenotabile.COSTO_ORARIO);
		this.getPrenotabileDescrizione().aggiungiCosto(costoOrario);

		return this;
	}

	public PrenotabileDescrizioneBuilder impostaCostoUnaTantum(Costo costoDaImpostare) {
		CostoPrenotabile costoUnaTantum = new CostoPrenotabile();
		costoUnaTantum.setCosto(costoDaImpostare);
		costoUnaTantum.setTipoCosto(TipoCostoPrenotabile.COSTO_UNA_TANTUM);
		this.getPrenotabileDescrizione().aggiungiCosto(costoUnaTantum);

		return this;
	}

	public PrenotabileDescrizioneBuilder impostaCostoScontoPercentuale(Costo costoDaImpostare) {
		CostoPrenotabile costoScontoPercentuale = new CostoPrenotabile();
		costoScontoPercentuale.setCosto(costoDaImpostare);
		costoScontoPercentuale.setTipoCosto(TipoCostoPrenotabile.COSTO_SCONTO_PERCENTUALE);
		getPrenotabileDescrizione().aggiungiCosto(costoScontoPercentuale);
		return this;
	}


	public PrenotabileDescrizioneBuilder impostaCostoPavimentazione(Costo costoDaImpostare, String tipoPavimentazione) {
		CostoPrenotabile costoPavimentazione = new CostoPrenotabilePavimentazione();
		costoPavimentazione.setCosto(costoDaImpostare);
		costoPavimentazione.setTipoCosto(TipoCostoPrenotabile.COSTO_PAVIMENTAZIONE);
		Map<String, Object> mappaProprieta = new HashMap<String, Object>();
		mappaProprieta.put("tipoPavimentazione", tipoPavimentazione);
		costoPavimentazione.setProprieta(mappaProprieta);
		this.getPrenotabileDescrizione().aggiungiCosto(costoPavimentazione);

		return this;
	}

	public PrenotabileDescrizione build() {
		return this.getPrenotabileDescrizione();
	}

}
