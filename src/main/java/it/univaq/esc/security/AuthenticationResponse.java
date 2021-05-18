package it.univaq.esc.security;

import it.univaq.esc.dtoObjects.SportivoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class AuthenticationResponse {
    
    private final String jwt;
    private SportivoDTO sportivo;

}
