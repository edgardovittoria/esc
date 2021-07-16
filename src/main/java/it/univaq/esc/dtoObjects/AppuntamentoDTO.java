package it.univaq.esc.dtoObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AppuntamentoDTO extends NotificabileDTO{

	private Integer idAppuntamento;
	private OrarioAppuntamentoDTO orarioAppuntamento = new OrarioAppuntamentoDTO();
	private List<String> partecipanti = new ArrayList<String>();
	private List<Integer> squadrePartecipanti = new ArrayList<Integer>();
	private List<QuotaPartecipazioneDTO> quotePartecipazione = new ArrayList<QuotaPartecipazioneDTO>();
	private String creatore;
	private String manutentore;
	private String modalitaPrenotazione;
	private String tipoPrenotazione;
	private boolean confermata;
	private boolean pending;
	private String costo;
	private SportDTO sportAssociato;
	private List<String> invitati = new ArrayList<String>();
	private Integer idImpiantoPrenotato;
	private String pavimentazioneImpianto;
	private String istruttore;

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

}
