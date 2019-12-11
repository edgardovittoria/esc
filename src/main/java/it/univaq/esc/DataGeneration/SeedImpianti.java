package it.univaq.esc.DataGeneration;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Impianto1;
import it.univaq.esc.model.Impianto2;
import it.univaq.esc.model.Impianto3;
import it.univaq.esc.repository.ImpiantoRepository;

@Component
public class SeedImpianti {

	private static ImpiantoRepository impianti;
	
	@Autowired
	private ImpiantoRepository impiantiInit;
	
	private static ArrayList<Impianto> impiantiGenerati = new ArrayList<Impianto>();
	
	private static Logger loggerSeedImpianti = LoggerFactory.getLogger(SeedImpianti.class);
	
	private SeedImpianti() {}
	
	@PostConstruct
	private void initializeImpiantiRepo() {
		impianti = this.impiantiInit;
	}
	
	private static void deleteGeneratedData() {
		impianti.deleteAll();
	}
	
	 /**
     * Genera 3 record nella tabella impianti.
     * @throws Exception eccezione generata in caso di mancato salvataggio dei record.
     */
	public static void generaDati() throws Exception{
		deleteGeneratedData();
		
		Impianto impianto1 = Impianto1.getInstance();
		impiantiGenerati.add(impianto1);
		
		Impianto impianto2 = Impianto2.getInstance();
		impiantiGenerati.add(impianto2);
		
		Impianto impianto3 = Impianto3.getInstance();
		impiantiGenerati.add(impianto3);
		
		try {
			impianti.saveAll(impiantiGenerati);
			loggerSeedImpianti.info("...Impianti creati");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
