package com.dummy.omni_biz.authentication.util;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dummy.omni_biz.authentication.jwt.JwtConst;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public JwtUtil(
            @Value("${spring.jwt.private-key}") RSAPrivateKey privateKey,
            @Value("${spring.jwt.public-key}") RSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .subject(username)
                .claim(JwtConst.ROLES, roles)
                .signWith(privateKey, Jwts.SIG.RS256)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // after 1 hour
                .compact();
    }

    public Jws<Claims> validateTokenAndGetClaims(String token) {
        return Jwts.parser()
                .verifyWith(publicKey) // verify by public key
                .build()
                .parseSignedClaims(token);
    }
}
