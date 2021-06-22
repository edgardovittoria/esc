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
	private List<String> partecipanti = new ArrayList<String>();
	private List<Integer> squadrePartecipanti = new ArrayList<Integer>();
	private List<QuotaPartecipazioneDTO> quotePartecipazione = new ArrayList<QuotaPartecipazioneDTO>();
	private String creatore;
	private Integer idManutentore;
	private String modalitaPrenotazione;
	private String tipoPrenotazione;
	private boolean confermata;
	private boolean pending;
	private Float costo;
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

	public void aggiungiPartecipante(String emailPartecipante) {
		this.partecipanti.add(emailPartecipante);
	}

	public void aggiungiInvitato(String emailInvitato) {
		getInvitati().add(emailInvitato);
	}
	

	public void aggiungiSquadraPartecipante(Integer idSquadra) {
		getSquadrePartecipanti().add(idSquadra);
	}
}
