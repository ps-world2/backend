package com.pskjeong.psk.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {
    @Value("${jwtkey}")
    private static String JWT_SECRET;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    public static String generateAccessToken(Map<String, Object> info) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", info.get("email"));
        claims.put("nickname", info.get("nickname"));
        return doGenerateToken(claims, (String) info.get("email"));
    }

    public static String generateRefreshToken() {
        return Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }


    public static String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }
}
