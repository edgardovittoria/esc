package it.univaq.esc.dtoObjects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.FactorySpecifichePrenotazione;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppuntamentoDTO implements IModelToDTO {

	private Integer idAppuntamento;
	private OrarioAppuntamento orarioAppuntamento = new OrarioAppuntamento();
	private List<UtentePolisportivaDTO> partecipanti = new ArrayList<UtentePolisportivaDTO>();
	private PrenotazioneSpecsDTO specificaPrenotazione;
	private List<QuotaPartecipazioneDTO> quotePartecipazione = new ArrayList<QuotaPartecipazioneDTO>();
	private UtentePolisportivaDTO creatore;
	private Integer idManutentore;
	private String modalitaPrenotazione;

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

	@Override
	public void impostaValoriDTO(Object modelDaConvertire) {
		Appuntamento appuntamento = (Appuntamento) modelDaConvertire;
		this.setIdAppuntamento(appuntamento.getIdAppuntamento());
		setDataAppuntamento(appuntamento.getDataAppuntamento());
		setOraInizioAppuntamento(appuntamento.getOraInizioAppuntamento());
		setOraFineAppuntamento(appuntamento.getOraFineAppuntamento());
		PrenotazioneSpecsDTO specificaDTO = FactorySpecifichePrenotazione
				.getSpecifichePrenotazioneDTO(appuntamento.getPrenotazioneSpecsAppuntamento().getTipoPrenotazione());
		specificaDTO.impostaValoriDTO(appuntamento.getPrenotazioneSpecsAppuntamento());
		this.setSpecificaPrenotazione(specificaDTO);
		for (UtentePolisportivaAbstract partecipante : appuntamento.getUtentiPartecipanti()) {
			UtentePolisportivaDTO partecipanteDTO = new UtentePolisportivaDTO();
			partecipanteDTO.impostaValoriDTO(partecipante);
			this.aggiungiPartecipante(partecipanteDTO);
		}
		List<QuotaPartecipazioneDTO> listaQuote = new ArrayList<QuotaPartecipazioneDTO>();
		for (QuotaPartecipazione quota : appuntamento.getQuotePartecipazione()) {
			QuotaPartecipazioneDTO quotaDTO = new QuotaPartecipazioneDTO();
			quotaDTO.impostaValoriDTO(quota);
			listaQuote.add(quotaDTO);

		}
		this.setQuotePartecipazione(listaQuote);

		UtentePolisportivaDTO creatore = new UtentePolisportivaDTO();
		creatore.impostaValoriDTO(appuntamento.creatoDa());
		this.setCreatore(creatore);
		
		if (appuntamento.getManutentore() != null) {
			this.setIdManutentore(appuntamento.getManutentore().getId());
		}
		
		setModalitaPrenotazione(appuntamento.getModalitaPrenotazione());

	}

}
