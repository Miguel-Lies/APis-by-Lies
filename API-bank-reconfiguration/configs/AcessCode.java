package com.enterprise.bank_lies.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class AcessCode {

    @Value("${jwt.expiration}")
    private Long expirationTime;

    @Value("${jwt.key}")
    private String key;

    public String generateCode(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        return buildCode(user.getUsername());
    }

    private String buildCode(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigninKey())
                .compact();
    }

    private SecretKey getSigninKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    public boolean isTokenValid(String code) {
        try {
            getClaims(code);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String code) {

        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(code)
                .getPayload();
    }

    public String getUsername(String code){
        return getClaims(code).getSubject();
    }
}
