package it.univaq.esc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univaq.esc.creazioneDatiDB.PopolaDB;
import it.univaq.esc.utility.BeanUtil;


@SpringBootApplication
public class EscApplication implements CommandLineRunner {


	public static void main(String[] args) {

		SpringApplication.run(EscApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {


//		 PopolaDB popolaDB = BeanUtil.getBean(PopolaDB.class);
//		 popolaDB.popola();


	}

}
