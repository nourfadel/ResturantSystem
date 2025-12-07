package com.luv2code.ResturantSystem.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    // convert secret key to bytes and make it sha key
    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // generate token for JWT
    public String generateToken(String username){

        Date now = new Date();
        Date exp = new Date(now.getTime()+jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username) //place username of user in token
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // give the key and algorithm for the generate token
                .compact(); // return final token as string

    }

    // extract username from token
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        Claims claims = parseClaims(token); // read all claims from token
        return claimsResolver.apply(claims); // run function that pass in the call like Claims::getSubject
    }


    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // check if the token is expire or not
    public boolean isTokenExpired(String token){
        Date expiration = extractClaim(token,Claims::getExpiration);
        return expiration.before(new Date());
    }

    // function to validate token
    public boolean validateToken(String token , String username){
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }


}
