package it.univaq.esc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import groovy.transform.stc.FirstParam.ThirdGenericType;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.utenti.ProfiloIstruttore;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.TipoRuolo;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RunWith(MockitoJUnitRunner.class)
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
@Component
public class TestRegistroUtenti {

	@Spy
	private RegistroUtentiPolisportiva registroUtentiPolisportiva;
	@Spy
	private RegistroSport registroSport;
	
	@BeforeEach
	private void init() {
		MockitoAnnotations.initMocks(this);
		
		Sport calcetto = Mockito.mock(Sport.class);
		Sport tennis = Mockito.mock(Sport.class);
		Sport pallavolo = Mockito.mock(Sport.class);
		Sport scacchi = Mockito.mock(Sport.class);
		when(calcetto.getNome()).thenReturn("calcetto");
		when(tennis.getNome()).thenReturn("tennis");
		when(pallavolo.getNome()).thenReturn("pallavolo");
		when(scacchi.getNome()).thenReturn("scacchi");
		
		List<Sport> sportPolisportiva = new ArrayList<Sport>();
		sportPolisportiva.add(calcetto);
		sportPolisportiva.add(tennis);
		sportPolisportiva.add(pallavolo);
		sportPolisportiva.add(scacchi);
		when(registroSport.getListaSportPolisportiva()).thenReturn(sportPolisportiva);
		
		
		UtentePolisportiva istruttoreCalcetto = Mockito.mock(UtentePolisportiva.class);
		List<Sport> sportIstruttoreCalcetto = new ArrayList<Sport>();
		sportIstruttoreCalcetto.add(calcetto);
		ProfiloIstruttore profiloistruttoreCalcetto = Mockito.mock(ProfiloIstruttore.class);
		when(istruttoreCalcetto.is(TipoRuolo.ISTRUTTORE)).thenReturn(true);
		when(istruttoreCalcetto.comeIstruttore()).thenReturn(profiloistruttoreCalcetto);
		when(profiloistruttoreCalcetto.getSportInsegnati()).thenReturn(sportIstruttoreCalcetto);
		
		
		UtentePolisportiva istruttoreTennis = Mockito.mock(UtentePolisportiva.class);
		List<Sport> sportIstruttoreTennis = new ArrayList<Sport>();
		sportIstruttoreTennis.add(tennis);
		ProfiloIstruttore profiloistruttoreTennis = Mockito.mock(ProfiloIstruttore.class);
		when(istruttoreTennis.is(TipoRuolo.ISTRUTTORE)).thenReturn(true);
		when(istruttoreTennis.comeIstruttore()).thenReturn(profiloistruttoreTennis);
		when(profiloistruttoreTennis.getSportInsegnati()).thenReturn(sportIstruttoreTennis);
		
		
		UtentePolisportiva istruttorePallvoloCalcetto = Mockito.mock(UtentePolisportiva.class);
		List<Sport> sportIstruttorePallavoloCalcetto = new ArrayList<Sport>();
		sportIstruttorePallavoloCalcetto.add(calcetto);
		sportIstruttorePallavoloCalcetto.add(pallavolo);
		ProfiloIstruttore profiloistruttorePallavoloCalcetto = Mockito.mock(ProfiloIstruttore.class);
		when(istruttorePallvoloCalcetto.is(TipoRuolo.ISTRUTTORE)).thenReturn(true);
		when(istruttorePallvoloCalcetto.comeIstruttore()).thenReturn(profiloistruttorePallavoloCalcetto);
		when(profiloistruttorePallavoloCalcetto.getSportInsegnati()).thenReturn(sportIstruttorePallavoloCalcetto);
		
		List<UtentePolisportiva> utenti = new ArrayList<UtentePolisportiva>();
		utenti.add(istruttorePallvoloCalcetto);
		utenti.add(istruttoreTennis);
		utenti.add(istruttoreCalcetto);
		
		when(registroUtentiPolisportiva.getListaUtentiPolisportiva()).thenReturn(utenti);
		
	}
	
	
	@Test
	public void testRicercaSportPerCuiCiSonoIstruttori() {
		List<Sport> sportCheHannoistruttori = registroSport.getListaSportCheHannoIstruttori(registroUtentiPolisportiva);
		System.out.println("size: " + sportCheHannoistruttori.size());
		assertEquals(3, sportCheHannoistruttori.size());
	}
}
