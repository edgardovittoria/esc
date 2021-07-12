package it.univaq.esc.creazioneDatiDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;
import it.univaq.esc.model.Pavimentazione;
import it.univaq.esc.model.Sport;
import it.univaq.esc.repository.ImpiantoRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Component
@Singleton
@Setter(value = AccessLevel.PRIVATE)
public class CreaImpiantiDB {

	private CreaSportsDB creaSportsDB;
	private ImpiantoRepository impiantoRepository;
	private Map<String, Impianto> mappaImpianti = new HashMap<String, Impianto>();
	
	public CreaImpiantiDB(CreaSportsDB creaSportsDB, ImpiantoRepository impiantoRepository) {
		setCreaSportsDB(creaSportsDB);
		setImpiantoRepository(impiantoRepository);
	}
	
	public void creaImpianti() {
		List<Sport> sportPraticatiImpianto1 = new ArrayList<Sport>();
		sportPraticatiImpianto1.add(creaSportsDB.getSportConNome("calcetto"));
		sportPraticatiImpianto1.add(creaSportsDB.getSportConNome("tennis"));

		List<Sport> sportPraticatiImpianto2 = new ArrayList<Sport>();
		sportPraticatiImpianto2.add(creaSportsDB.getSportConNome("calcetto"));
		sportPraticatiImpianto2.add(creaSportsDB.getSportConNome("pallavolo"));

		List<Sport> sportPraticatiImpianto3 = new ArrayList<Sport>();
		sportPraticatiImpianto3.add(creaSportsDB.getSportConNome("calcetto"));
		sportPraticatiImpianto3.add(creaSportsDB.getSportConNome("pallavolo"));
		sportPraticatiImpianto3.add(creaSportsDB.getSportConNome("tennis"));

		ImpiantoSpecs specificaImpianto1 = new ImpiantoSpecs(Pavimentazione.SINTETICO, creaSportsDB.getSportConNome("calcetto"));
		ImpiantoSpecs specificaImpianto12 = new ImpiantoSpecs(Pavimentazione.SINTETICO, creaSportsDB.getSportConNome("tennis"));
		ImpiantoSpecs specificaImpianto13 = new ImpiantoSpecs(Pavimentazione.SINTETICO, creaSportsDB.getSportConNome("pallavolo"));
		ImpiantoSpecs specificaImpianto2 = new ImpiantoSpecs(Pavimentazione.TERRA_BATTUTA, creaSportsDB.getSportConNome("tennis"));
		ImpiantoSpecs specificaImpianto3 = new ImpiantoSpecs(Pavimentazione.CEMENTO, creaSportsDB.getSportConNome("pallavolo"));
		ImpiantoSpecs specificaImpianto32 = new ImpiantoSpecs(Pavimentazione.CEMENTO, creaSportsDB.getSportConNome("tennis"));

		// impiantoSpecsRepository.save(specificaImpianto1);
		// impiantoSpecsRepository.save(specificaImpianto2);
		// impiantoSpecsRepository.save(specificaImpianto3);

		List<ImpiantoSpecs> specificheImpianto1 = new ArrayList<ImpiantoSpecs>();
		specificheImpianto1.add(specificaImpianto1);
		specificheImpianto1.add(specificaImpianto12);
		specificheImpianto1.add(specificaImpianto13);

		List<ImpiantoSpecs> specificheImpianto2 = new ArrayList<ImpiantoSpecs>();
		specificheImpianto2.add(specificaImpianto2);

		List<ImpiantoSpecs> specificheImpianto3 = new ArrayList<ImpiantoSpecs>();
		specificheImpianto3.add(specificaImpianto3);
		specificheImpianto3.add(specificaImpianto32);

		Impianto impianto1 = new Impianto(specificheImpianto1);
		Impianto impianto2 = new Impianto(specificheImpianto2);
		Impianto impianto3 = new Impianto(specificheImpianto3);

		impiantoRepository.save(impianto1);
		impiantoRepository.save(impianto2);
		impiantoRepository.save(impianto3);
		
		mappaImpianti.put("impianto1", impianto1);
		mappaImpianti.put("impianto2", impianto2);
		mappaImpianti.put("impianto3", impianto3);

	}
	
	public Impianto getImpiantoBy(String etichetta) {
		return mappaImpianti.get(etichetta);
	}
}

