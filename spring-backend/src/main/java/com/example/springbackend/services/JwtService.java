package com.example.springbackend.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

//    @Value("${app.signinkey}")
    private static final String SIGNIN_KEY = "ce75a659c8e354fa5cc94b68219ac6b7d0529be396aa3667b809191de90a9cf5f93e5886f0cd6686b3917e67c51896a83e7ae705d31dd1c2ef7057d206dcadb8";

//    @Value("${app.expirationtime}")
    private static final int EXPIRATION_TIME = 30;

    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();

        Instant currentDate = Instant.now();
        Instant expirationDate = currentDate.plusSeconds(EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(currentDate.toEpochMilli()))
                .setExpiration(new Date(expirationDate.toEpochMilli()))
                .signWith(SignatureAlgorithm.HS512, SIGNIN_KEY)
                .compact();
    }

    public Date getExpirationFromToken(String token) {
        Claims claims = getClaims(token);

        return claims.getExpiration();
    }

    public String getLoginFromToken(String token) {
        Claims claims = getClaims(token);

        return claims.getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNIN_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
