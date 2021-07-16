package it.univaq.esc.security;

import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE) @AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService{

    private RegistroUtentiPolisportiva registroUtentiPolisportiva;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       
        UtentePolisportiva utente = getRegistroUtentiPolisportiva().trovaUtenteInBaseAllaSua(email);
        // System.out.println(new User((String)utente.getProprieta().get("email"), 
        // (String)utente.getProprieta().get("password"), 
        // getAuthority(utente)));
        	return new User(utente.getEmail(), 
                    utente.getPassword(), 
                    getAuthority(utente));     

    }
    
    private List<SimpleGrantedAuthority> getAuthority(UtentePolisportiva user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
		user.getRuoli().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		});
		List<SimpleGrantedAuthority> listaRuoli = new ArrayList<SimpleGrantedAuthority>(authorities);
		return listaRuoli;
	}

}
