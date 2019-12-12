package it.univaq.esc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;



@SpringBootApplication
public class EscApplication extends SpringBootServletInitializer{


	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EscApplication.class);
    }

	public static void main(String[] args) {
				
		SpringApplication.run(EscApplication.class, args);

		
	}

}
