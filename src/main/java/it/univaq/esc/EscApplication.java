package it.univaq.esc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univaq.esc.test.TestFactoryStatiNotifiche;
import it.univaq.esc.test.TestVerificaImpostazioneStatiNelleNotifiche;
import it.univaq.esc.utility.BeanUtil;


@SpringBootApplication
public class EscApplication implements CommandLineRunner {


	public static void main(String[] args) {

		SpringApplication.run(EscApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

//		RicercaAppuntamentiSottoscrivibili ricercaAppuntamentiSottoscrivibili = BeanUtil.getBean(RicercaAppuntamentiSottoscrivibili.class);
//		ricercaAppuntamentiSottoscrivibili.test();
//
//		RegistrazioneCorsiTest registrazioneCorsiTest = BeanUtil.getBean(RegistrazioneCorsiTest.class);
//		registrazioneCorsiTest.test();
//
//		PopolaDB popolaDB = BeanUtil.getBean(PopolaDB.class);
//		popolaDB.popola();
//
//		TestVerificaCalendario testCalendarioPrenotazioni = BeanUtil.getBean(TestVerificaCalendario.class);
//		testCalendarioPrenotazioni.test();
//
//		TestFactoryStatiNotifiche testFactoryStatiNotifiche = BeanUtil.getBean(TestFactoryStatiNotifiche.class);
//		testFactoryStatiNotifiche.test();

//		TestFactoryStatoEffettuaPrenotazione testFactoryStatoEffettuaPrenotazione = BeanUtil.getBean(TestFactoryStatoEffettuaPrenotazione.class);
//		testFactoryStatoEffettuaPrenotazione.test();
		
//		TestVerificaImpostazioneStatiNelleNotifiche testVerificaImpostazioneStatiNelleNotifiche = BeanUtil.getBean(TestVerificaImpostazioneStatiNelleNotifiche.class);
//		testVerificaImpostazioneStatiNelleNotifiche.test();

	}

}
