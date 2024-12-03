package com.example.sleepmonitor_backend.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    // Provide a default secret key if not set in properties
    @Value("${jwt.secret:defaultSecretKey_AttackTheDPoint}")
    private String secretKey;

    private SecretKey generateSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String userId) {
        long now = System.currentTimeMillis();
        SecretKey key = generateSecretKey();

        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date(now))
                .expiration(new Date(now + 1000 * 60 * 60 * 24)) // 24-hour validity  JWT的计时机制是通过时间戳实现的，这些时间戳在JWT中以iat (Issued At) 和 exp (Expiration Time) 标识。JWT的验证过程只涉及检查这些时间戳是否仍在有效期内，与服务器的状态（如是否重启）无关。
                .signWith(key)
                .compact();
    }

    public String validateTokenAndGetUserId(String token) {
        try {
            SecretKey key = generateSecretKey();

            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            // Handle token validation failure
            throw new RuntimeException("Invalid or expired JWT token", e);
        }
    }

//    public String validateTokenAndGetUserId(String token) {
//        try {
//            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//
//            Claims claims = Jwts.parser()
//                    .verifyWith(key)
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//
//            // Additional logging
//            System.out.println("Token Subject: " + claims.getSubject());
//            System.out.println("Token Expiration: " + claims.getExpiration());
//
//            // Check expiration
//            if (claims.getExpiration().before(new Date())) {
//                throw new ExpiredJwtException(null, claims, "Token expired");
//            }
//
//            return claims.getSubject();
//        } catch (Exception e) {
//            System.err.println("Token Validation Error: " + e.getMessage());
//            throw new RuntimeException("Invalid token", e);
//        }
//    }
}