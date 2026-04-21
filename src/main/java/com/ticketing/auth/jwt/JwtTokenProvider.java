package com.ticketing.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessExpMin;

    public JwtTokenProvider(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-exp-min}") long accessExpMin
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpMin = accessExpMin;
    }

    public String createAccessToken(Long userId, String role) {
        Instant now = Instant.now();
        Instant exp = now.plus(accessExpMin, ChronoUnit.MINUTES);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("role", role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}
