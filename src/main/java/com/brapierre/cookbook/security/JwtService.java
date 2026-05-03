package com.brapierre.cookbook.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.nio.charset.StandardCharsets;

import com.brapierre.cookbook.entity.User; // <-- Make sure this import matches your User entity package

@Service
public class JwtService {

    private final byte[] secretKey;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.secretKey = secret.getBytes(StandardCharsets.UTF_8);
    }

    // Updated method to accept User
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail()) // use the email from User
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day validity
                .signWith(Keys.hmacShaKeyFor(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}