package it.univaq.esc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoSingoliPartecipanti;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneCorsoSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneLezioneSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@RunWith(SpringRunner.class)
@SpringBootTest
@Setter(value = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PRIVATE)
public class TestConfermaCorso {
	
	@Autowired
	private CatalogoPrenotabili catalogoPrenotabili;
	
	@Autowired
	private RegistroSport registroSport;
	
	@Autowired
	private RegistroAppuntamenti registroAppuntamenti;
	
	@Autowired
	private RegistroPrenotazioni registroPrenotazioni;
	
	private Prenotazione corso;
	
	private List<Appuntamento> appuntamenti;
	

	@BeforeEach
	public void init() {
		PrenotazioneSpecs specificaCorso = new PrenotazioneCorsoSpecs();
		PrenotazioneSpecs lezione1 = new PrenotazioneLezioneSpecs();
		PrenotazioneSpecs lezione2 = new PrenotazioneLezioneSpecs();
		
		List<PrenotazioneSpecs> lezioni = new ArrayList<PrenotazioneSpecs>();
		lezioni.add(lezione1);
		lezioni.add(lezione2);
		Map<String, Object> mappaValoriCorso = new HashMap<String, Object>();
		mappaValoriCorso.put("listaLezioniCorso", lezioni);
		specificaCorso.impostaValoriSpecificheExtraPrenotazione(mappaValoriCorso);
		
		Prenotazione prenotazione = new Prenotazione(getRegistroPrenotazioni().getLastIdPrenotazione()+1);
		prenotazione.aggiungiSpecifica(specificaCorso);
		specificaCorso.setPrenotazioneAssociata(prenotazione);
		lezione1.setPrenotazioneAssociata(prenotazione);
		lezione2.setPrenotazioneAssociata(prenotazione);
		
		PrenotabileDescrizione descrizioneCorso = getCatalogoPrenotabili().nuovoPrenotabile_avviaCreazione(
				getRegistroSport().getSportByNome("tennis"), 
				TipiPrenotazione.CORSO.toString(), 4, 30)
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_impostaCostoUnaTantum(Float.parseFloat("50"))
				.nuovoPrenotabile_salvaPrenotabileInCreazione();
		
		specificaCorso.setSpecificaDescription(descrizioneCorso);
		lezione1.setSpecificaDescription(descrizioneCorso);
		lezione2.setSpecificaDescription(descrizioneCorso);
		
		Appuntamento appuntamento1 = new AppuntamentoSingoliPartecipanti();
		appuntamento1.setPrenotazioneSpecsAppuntamento(lezione1);
		
		Appuntamento appuntamento2 = new AppuntamentoSingoliPartecipanti();
		appuntamento2.setPrenotazioneSpecsAppuntamento(lezione2);
		
		List<Appuntamento> listaAppuntamenti = new ArrayList<Appuntamento>();
		listaAppuntamenti.add(appuntamento1);
		listaAppuntamenti.add(appuntamento2);
		
		setCorso(prenotazione);
		setAppuntamenti(listaAppuntamenti);
	}
	
	
	@Test
	public void testSalvataggioCorso() {
		Integer numeroPrenotazioni = getRegistroPrenotazioni().getPrenotazioniRegistrate().size();
		getRegistroPrenotazioni().aggiungiPrenotazione(corso);
		
		Integer numeroAppuntamenti = getRegistroAppuntamenti().getListaAppuntamenti().size();
		getRegistroAppuntamenti().salvaListaAppuntamenti(appuntamenti);
		
		assertEquals(numeroAppuntamenti+2, getRegistroAppuntamenti().getListaAppuntamenti().size());
		assertEquals(numeroPrenotazioni+1, getRegistroPrenotazioni().getPrenotazioniRegistrate().size());
		
		
	}
}
