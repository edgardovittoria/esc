package it.univaq.esc.model.prenotazioni;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.repository.QuotaPartecipazioneRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Singleton
@Setter(value = AccessLevel.PRIVATE)
public class RegistroQuotePartecipazione {

	private List<QuotaPartecipazione> listaQuotaPartecipazione = new ArrayList<QuotaPartecipazione>();
	private RegistroUtentiPolisportiva registroUtentiPolisportiva;
	private QuotaPartecipazioneRepository quotaPartecipazioneRepository;
	
	public RegistroQuotePartecipazione(QuotaPartecipazioneRepository quotaPartecipazioneRepository, RegistroUtentiPolisportiva registroUtentiPolisportiva) {
		setQuotaPartecipazioneRepository(quotaPartecipazioneRepository);
		setRegistroUtentiPolisportiva(registroUtentiPolisportiva);
		popola();
	}
	
	
	private void popola() {
		setListaQuotaPartecipazione(quotaPartecipazioneRepository.findAll());
		for(QuotaPartecipazione quotaPartecipazione : listaQuotaPartecipazione) {
			UtentePolisportiva utente = registroUtentiPolisportiva.trovaUtenteInBaseAllaSua(quotaPartecipazione.getSportivoAssociato().getEmail());
			quotaPartecipazione.setSportivoAssociato(utente);
		}
	}
	
	public Integer getUltimoIdQuote() {
		Integer ultimoId = 0;
		for(QuotaPartecipazione quotaPartecipazione : listaQuotaPartecipazione) {
			if(quotaPartecipazione.getIdQuotaPartecipazione()>ultimoId) {
				ultimoId = quotaPartecipazione.getIdQuotaPartecipazione();
			}
		}
		return ultimoId;
	}
}
