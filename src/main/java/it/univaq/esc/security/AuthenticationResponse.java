package it.univaq.esc.security;

import it.univaq.esc.dtoObjects.SportivoDTO;

public class AuthenticationResponse {
    
    private final String jwt;
    private SportivoDTO sportivo;
    

    public AuthenticationResponse(String jwt, SportivoDTO sportivoDTO){
        this.jwt = jwt;
        this.setSportivo(sportivoDTO);
    }


    public SportivoDTO getSportivo() {
        return sportivo;
    }


    public void setSportivo(SportivoDTO sportivoDTO) {
        this.sportivo = sportivoDTO;
    }


    public String getJwt() {
        return jwt;
    }


}
