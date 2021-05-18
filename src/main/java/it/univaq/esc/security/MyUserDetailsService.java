package it.univaq.esc.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import javassist.NotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE) @AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService{

    private RegistroUtentiPolisportiva registroUtentiPolisportiva;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        UtentePolisportivaAbstract utente = getRegistroUtentiPolisportiva().getUtenteByEmail(username);
        
        	return new User((String)utente.getProprieta().get("email"), 
                    (String)utente.getProprieta().get("password"), 
                    new ArrayList<>());     

    }

}
