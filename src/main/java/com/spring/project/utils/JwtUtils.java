package com.spring.project.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public String extractUsername(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        try {
            String jwtToken = extractTokenFromAuthHeader(authHeader);
            return extractAllClaims(jwtToken).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }
        try {
            String jwtToken = extractTokenFromAuthHeader(authHeader);
            return !isTokenExpired(jwtToken);
        } catch (Exception e) {
            return false;
        }
    }

    private String extractTokenFromAuthHeader(String authHeader) {
        return authHeader.substring(7);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date currentTime = new Date(System.currentTimeMillis());
        long jwtExpiryInMs = 1000 * 60;
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ", "JWT")
                .and()
                .issuedAt(currentTime)
                .expiration(new Date(currentTime.getTime() + jwtExpiryInMs))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Invalid JWT token");
        }
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
