package it.univaq.esc.model.prenotazioni;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.Impianto;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice.This;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PrenotazioneCorsoSpecs extends PrenotazioneSpecs {
	@OneToMany()
	@JoinColumn()
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<PrenotazioneSpecs> listaLezioni = new ArrayList<PrenotazioneSpecs>();

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UtentePolisportivaAbstract> invitati = new ArrayList<UtentePolisportivaAbstract>();

	@Override
	public void impostaValoriSpecificheExtraPrenotazione(Map<String, Object> mappaValori) {
		// List<Map<String, Object>> listaMappe = ((List<Map<String,
		// Object>>)mappaValori.get("listaMappeLezioni"));
		this.setListaLezioni((List<PrenotazioneSpecs>) mappaValori.get("listaLezioniCorso"));
		// listaLezioni.forEach((lezione) ->
		// lezione.impostaValoriSpecificheExtraPrenotazione(listaMappe.get(listaLezioni.indexOf(lezione))));
		this.setInvitati((List<UtentePolisportivaAbstract>) mappaValori.get("invitati"));

	}

	@Override
	public Map<String, Object> getValoriSpecificheExtraPrenotazione() {
		HashMap<String, Object> mappaValori = new HashMap<String, Object>();
		mappaValori.put("listaLezioniCorso", this.getListaLezioni());
		mappaValori.put("invitati", this.getInvitati());
		return mappaValori;
	}

	@Override
	public String getTipoPrenotazione() {
		return TipiPrenotazione.CORSO.toString();
	}

	@Override
	public void setPending(boolean pending) {
		super.setPending(pending);
		this.getListaLezioni().forEach((lezioneSpecs) -> lezioneSpecs.setPending(pending));

	}
	
	@Override
	public List<PrenotazioneSpecs> getPrenotazioniSpecsFiglie() {
		return getListaLezioni();
	}

}
