package it.univaq.esc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authoriztionHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        /**
         * vengono ricavati token e username
         */
        if(authoriztionHeader != null && authoriztionHeader.startsWith("Bearer ")){
            jwt = authoriztionHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        /**
         * viene verificato se è già presente un utente autenticato
         */
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            /**
             * viene creato un oggetto userDetails relativo all'utente che sta 
             * effettuando la richiesta.
             * userDetails viene usato per validare il token relativo all'utente.
             * Viene creato il default token utilizzato da spring e settato nel contesto
             * della richiesta http   
             */
            UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);
            if(jwtUtil.validateToken(jwt, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.
                    setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
        
    }
    
}
