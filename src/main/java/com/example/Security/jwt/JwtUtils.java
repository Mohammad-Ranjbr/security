package com.example.Security.jwt;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@Service
public class JwtUtils {
    
    private final String SECRET = "mysecret";

    public String generateToken(String username){  
        return Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + (24 * 60 * 60))) 
        .signWith(SignatureAlgorithm.HS256, SECRET)
        .compact(); 
    }

    public String getUsername(String token){
        return Jwts.parser()
        .setSigningKey(SECRET)
        .parseClaimsJws(token).getBody().getSubject(); 
    }

}
