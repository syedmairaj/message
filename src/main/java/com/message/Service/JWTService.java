package com.message.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private long expiry;


    private Algorithm algorithm;

    @PostConstruct

    public void init() throws UnsupportedEncodingException {
        if(algorithmKey ==null || algorithmKey.isEmpty()){
            throw  new IllegalStateException("JWT algorithm key must not be null or empty");
        }
        algorithm =Algorithm.HMAC256(algorithmKey);
    }

    //Generating token
    public String generateToken(String username, String role){
        if(username ==null || username.isEmpty()){
            throw new IllegalArgumentException("User must not be null or empty");
        }

        return JWT.create()
                .withClaim("name",username)
                .withClaim("role",role)
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiry *1000L))
                .sign(algorithm);

    }

    //Validate token and extract username

    public String getUsernameFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token);
            return decodedJWT.getClaim("name").asString();
        }catch(JWTVerificationException e){
            throw new IllegalArgumentException("Invalid or expired token",e);
        }

    }

    public String generateroleFromToken(String token){
        try {
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token);
            return decodedJWT.getClaim("role").asString();
        }catch(JWTVerificationException e){
            throw new IllegalArgumentException("Invalid or expired token",e);

        }
    }

}
