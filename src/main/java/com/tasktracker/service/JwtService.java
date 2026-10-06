package com.tasktracker.service;


import com.tasktracker.configuration.JwtProperties;
import com.tasktracker.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public String generateJwtToken(String username) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", UserRole.USER.name());

        return createToken(claims, username);
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private String createToken(Map<String, Object> claims, String subject) {
        Date createdDate = new Date(System.currentTimeMillis());
        Date expireDate = new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000));
        Key secretKey = getSignedKey();

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(createdDate)
                .expiration(expireDate)
                .signWith(secretKey)
                .compact();
    }


    private SecretKey getSignedKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
