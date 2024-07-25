package com.devdoc.backend.security;

import com.devdoc.backend.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {

    private Key secretKey;
    private final com.devdoc.backend.service.TokenStore tokenStore;

    public TokenProvider(com.devdoc.backend.service.TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @PostConstruct
    public void init() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String create(UserEntity userEntity) {
        Date expiryDate = Date.from(Instant.now().plus(1L, ChronoUnit.DAYS));
        String token = Jwts.builder()
                .signWith(this.secretKey)
                .setSubject(userEntity.getId())
                .setIssuer("Issuer")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
        tokenStore.addToken(token);
        return token;
    }

    public String validateAndGetUserId(String token) {
        if (!tokenStore.isValidToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }
        Claims claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public void invalidateToken(String token) {
        tokenStore.removeToken(token);
    }
}
