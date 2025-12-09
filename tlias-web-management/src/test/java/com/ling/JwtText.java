package com.ling;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtText {

    @Test
    public void testJwt() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "ling");
        String ling = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "ling")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60)).compact();
        System.out.println(ling);
    }

    @Test
    public void testJwt2() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3NjQ0MDA2NzcsInVzZXJuYW1lIjoibGluZyJ9.vl3F2g0J31joDkTjFaVlXbqT0QCPQiH0Lnsrs4arDwI";
        Claims claims = Jwts.parser().setSigningKey("ling").parseClaimsJws(token).getBody();
        System.out.println(claims);
    }
}
