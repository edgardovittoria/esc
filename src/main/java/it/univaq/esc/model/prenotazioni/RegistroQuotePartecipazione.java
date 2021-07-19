package it.univaq.esc.model.prenotazioni;

import groovy.lang.Singleton;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.repository.QuotaPartecipazioneRepository;
import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Singleton
@Setter(value = AccessLevel.PRIVATE)
public class RegistroQuotePartecipazione {

	private List<QuotaPartecipazione> listaQuotaPartecipazione = new ArrayList<QuotaPartecipazione>();
	private RegistroUtentiPolisportiva registroUtentiPolisportiva;
	private QuotaPartecipazioneRepository quotaPartecipazioneRepository;

	public RegistroQuotePartecipazione(QuotaPartecipazioneRepository quotaPartecipazioneRepository,
			RegistroUtentiPolisportiva registroUtentiPolisportiva) {
		setQuotaPartecipazioneRepository(quotaPartecipazioneRepository);
		setRegistroUtentiPolisportiva(registroUtentiPolisportiva);
		popola();
	}

	private void popola() {
		setListaQuotaPartecipazione(quotaPartecipazioneRepository.findAll());
		for (QuotaPartecipazione quotaPartecipazione : listaQuotaPartecipazione) {
			UtentePolisportiva utente = registroUtentiPolisportiva
					.trovaUtenteInBaseAllaSua(quotaPartecipazione.getSportivoAssociato().getEmail());
			quotaPartecipazione.setSportivoAssociato(utente);
		}
	}

	
	public void salva(List<QuotaPartecipazione> nuovaListaQuotePartecipazione) {
		for(QuotaPartecipazione quotaPartecipazione : nuovaListaQuotePartecipazione) {
			salva(quotaPartecipazione);
		}
	}
	
	public void salva(QuotaPartecipazione nuovaQuota) {
		if(!isGiaPresenteNelRegistroLa(nuovaQuota)) {
			listaQuotaPartecipazione.add(nuovaQuota);
		}	
	}

	private boolean isGiaPresenteNelRegistroLa(QuotaPartecipazione nuovaQuota) {
		boolean giaPresente = false;
		for (QuotaPartecipazione quota : listaQuotaPartecipazione) {
			if (quota.isEqual(nuovaQuota)) {
				giaPresente = true;
				break;
			}
		}
		return giaPresente;
	}


	public Integer getUltimoIdQuote() {
		Integer ultimoId = 0;
		for (QuotaPartecipazione quotaPartecipazione : listaQuotaPartecipazione) {
			if (quotaPartecipazione.getIdQuotaPartecipazione() > ultimoId) {
				ultimoId = quotaPartecipazione.getIdQuotaPartecipazione();
			}
		}
		return ultimoId;
	}
}
