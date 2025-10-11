package com.example.plumpet.Security;

import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtCore {
    @Value("$Plumpet.app.secret")
    private String secret;

    @Value("$Plumpet.app.expirationMs")
    private int lifetime;

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = authentication.getPrincipal() == null ? null : (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject((userDetails.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+lifetime))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }
    public String getnamefromJwt(String token) {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody().getSubject();
    }
}
