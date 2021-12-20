package com.example.springbackend.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

//    @Value("${app.signinkey}")
    private static final String SIGNING_KEY = "ce75a659c8e354fa5cc94b68219ac6b7d0529be396aa3667b809191de90a9cf5f93e5886f0cd6686b3917e67c51896a83e7ae705d31dd1c2ef7057d206dcadb8"; //512
    private static final String REFRESH_SIGNING_KEY = "6d909d9dab2fe0bf34066547baec2c4c51841868dba2a6946fad906f709deecd"; //256

//    @Value("${app.expirationtime}")
    private static final int EXPIRATION_TIME = 30;
    private static final int REFRESH_EXPIRATION_TIME = 2 * EXPIRATION_TIME;

    public String generateToken(Authentication authentication) {
        return generateToken(SIGNING_KEY, authentication.getName(), EXPIRATION_TIME, SignatureAlgorithm.HS512);
    }

    public Date getExpirationFromToken(String token) {
        Claims claims = getClaims(token, SIGNING_KEY);

        return claims.getExpiration();
    }

    public String getLoginFromToken(String token) {
        Claims claims = getClaims(token, SIGNING_KEY);

        return claims.getSubject();
    }

    public String generateRefreshToken(String login) {
        return generateToken(REFRESH_SIGNING_KEY, login, REFRESH_EXPIRATION_TIME, SignatureAlgorithm.HS256);
    }

    public String getLoginFromRefreshToken(String refreshToken) {
        Claims claims = getClaims(refreshToken, REFRESH_SIGNING_KEY);
        return claims.getSubject();
    }

    private Claims getClaims(String token, String signingKey) {
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(String signingKey, String subject, int expirationTime, SignatureAlgorithm algorithm) {
        Map<String, Object> claims = new HashMap<>();

        Instant currentDate = Instant.now();
        Instant expirationDate = currentDate.plusSeconds(expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(currentDate.toEpochMilli()))
                .setExpiration(new Date(expirationDate.toEpochMilli()))
                .signWith(algorithm, signingKey)
                .compact();
    }
}
