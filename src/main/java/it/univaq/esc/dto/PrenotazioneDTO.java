package it.univaq.esc.dto;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import it.univaq.esc.model.CostoPrenotazione;
import it.univaq.esc.model.IPrenotabile;

@Component
public class PrenotazioneDTO {

	private Boolean confermata;
	private enum modalita{
		STANDARD, PENDING
	}
	private int numPartecipanti;
	private int numPostiLiberi;
	private SportivoDTO sportivoPrenotante;
	private CostoPrenotazione costoPrenotazione;
	private IPrenotabile servizioPrenotato;
	private Calendar calendario;
	private List<SportivoDTO> Partecipanti;
	private List<SportivoDTO> Invitati;
	
	public Boolean getConfermata() {
		return confermata;
	}
	public void setConfermata(Boolean confermata) {
		this.confermata = confermata;
	}
	public int getNumPartecipanti() {
		return numPartecipanti;
	}
	public void setNumPartecipanti(int numPartecipanti) {
		this.numPartecipanti = numPartecipanti;
	}
	public int getNumPostiLiberi() {
		return numPostiLiberi;
	}
	public void setNumPostiLiberi(int numPostiLiberi) {
		this.numPostiLiberi = numPostiLiberi;
	}
	public SportivoDTO getSportivoPrenotante() {
		return sportivoPrenotante;
	}
	public void setSportivoPrenotante(SportivoDTO sportivoPrenotante) {
		this.sportivoPrenotante = sportivoPrenotante;
	}
	public CostoPrenotazione getCostoPrenotazione() {
		return costoPrenotazione;
	}
	public void setCostoPrenotazione(CostoPrenotazione costoPrenotazione) {
		this.costoPrenotazione = costoPrenotazione;
	}
	public IPrenotabile getServizioPrenotato() {
		return servizioPrenotato;
	}
	public void setServizioPrenotato(IPrenotabile servizioPrenotato) {
		this.servizioPrenotato = servizioPrenotato;
	}
	public Calendar getCalendario() {
		return calendario;
	}
	public void setCalendario(Calendar calendario) {
		this.calendario = calendario;
	}
	public List<SportivoDTO> getPartecipanti() {
		return Partecipanti;
	}
	public void setPartecipanti(List<SportivoDTO> partecipanti) {
		Partecipanti = partecipanti;
	}
	public List<SportivoDTO> getInvitati() {
		return Invitati;
	}
	public void setInvitati(List<SportivoDTO> invitati) {
		Invitati = invitati;
	}


}
