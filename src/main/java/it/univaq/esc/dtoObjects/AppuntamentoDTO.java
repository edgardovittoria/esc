package it.univaq.esc.dtoObjects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.utility.BeanUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppuntamentoDTO {

	private Integer idAppuntamento;
	private OrarioAppuntamento orarioAppuntamento = new OrarioAppuntamento();
	private List<UtentePolisportivaDTO> partecipanti = new ArrayList<UtentePolisportivaDTO>();
	private List<Integer> squadrePartecipanti = new ArrayList<Integer>();
	private PrenotazioneSpecsDTO specificaPrenotazione;
	private List<QuotaPartecipazioneDTO> quotePartecipazione = new ArrayList<QuotaPartecipazioneDTO>();
	private UtentePolisportivaDTO creatore;
	private Integer idManutentore;
	private String modalitaPrenotazione;
	private String tipoPrenotazione;

	public LocalDate getDataAppuntamento() {
		return this.getOrarioAppuntamento().getDataPrenotazione();
	}

	public void setDataAppuntamento(LocalDate dataAppuntamento) {
		this.getOrarioAppuntamento().setDataPrenotazione(dataAppuntamento);
	}

	public LocalTime getOraInizioAppuntamento() {
		return this.getOrarioAppuntamento().getOraInizio();
	}

	public void setOraInizioAppuntamento(LocalTime oraInizioAppuntamento) {
		this.getOrarioAppuntamento().setOraInizio(oraInizioAppuntamento);
	}

	public LocalTime getOraFineAppuntamento() {
		return this.getOrarioAppuntamento().getOraFine();
	}

	public void setOraFineAppuntamento(LocalTime oraFineAppuntamento) {
		this.getOrarioAppuntamento().setOraFine(oraFineAppuntamento);
	}

	public void aggiungiPartecipante(UtentePolisportivaDTO partecipante) {
		this.partecipanti.add(partecipante);
	}

	

}
