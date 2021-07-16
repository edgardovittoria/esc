package it.univaq.esc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    
    private String SECRET_KEY = "secret";

    /*
        viene estratto lo username dal token
    */ 
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /*
        viene estratta la data di scadenza del token
    */ 
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /*
        viene estratto il payload dal token
    */ 
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /*
        controlla che il token non sia scaduto
    */ 
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /*
        viene generato il token grazie ai dettagli dell'utente
    */ 
    public String generateToken(UserDetails userDetails){
        /*
            se volessimo includere all'interno del token un payload, 
            dobbiamo creare e popolare una mappa e passarla al metodo createToken.
            In questo caso non abbiamo bisogno di un payload e quindi passiamo 
            una mappa vuota
        */
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /*
        viene generato il token in cui vengono rispettivamente settati:
        payload, user, data di creazione, scadenza, algoritmo per criptare il token
    */ 
    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*2))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /*
        viene effattuata la validazione del token, controllando che nel token 
        è presente lo username corretto e che non sia scaduto
    */ 
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
