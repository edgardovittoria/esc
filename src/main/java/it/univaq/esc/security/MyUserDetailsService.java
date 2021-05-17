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

@Component
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private RegistroUtentiPolisportiva registroUtentiPolisportiva;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        UtentePolisportivaAbstract utente = registroUtentiPolisportiva.getUtenteByEmail(username);
        return new User((String)utente.getProprieta().get("email"), 
                        (String)utente.getProprieta().get("password"), 
                        new ArrayList<>());

    }

}
