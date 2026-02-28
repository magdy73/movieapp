package com.movieapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // 🔐 256-bit Base64 encoded secret key (secure)
    private static final String SECRET_KEY =
            "dXhPZ3JkR2hsa1pYUnZjbVZ6ZEdGdWMybHVaR1Z5YVdOaGJHVnlibWx1Zw==";

    // ⏳ 10 ساعات
    private static final long JWT_EXPIRATION = 1000 * 60 * 60 * 10;

    // ==========================================
    // 🎯 Generate Token
    // ==========================================
    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ==========================================
    // 🎯 Extract Username
    // ==========================================
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ==========================================
    // 🎯 Extract Expiration
    // ==========================================
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ==========================================
    // 🎯 Extract Any Claim
    // ==========================================
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // ==========================================
    // 🎯 Validate Token
    // ==========================================
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }

    // ==========================================
    // 🎯 Check Expiration
    // ==========================================
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ==========================================
    // 🎯 Extract All Claims
    // ==========================================
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ==========================================
    // 🔐 Get Signing Key
    // ==========================================
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}