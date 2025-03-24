package com.elberjsn.restaurant.security;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {

    private static final String SECRET_KEY = "projetoRestaurant";
    private static final long EXPIRATION_TIME = 1000 * 60 * 360;

    public static String gerarToken(String cnpj) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create().withIssuer("restaurant")
                .withSubject(cnpj)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public static String validarToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("restaurant")
                .build();
            return verifier.verify(token).getSubject();
        }catch(JWTVerificationException exception){
            return null;
        }
    }

    public static boolean isTokenExpirado(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        Date expiration = decodedJWT.getExpiresAt();
        return expiration != null && expiration.before(new Date());
    }

    public static String decoderToken(String token){

        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

           return decodedJWT.getSubject();
        } catch (Exception e) {
            return e.getMessage();
        } 
    }

     
}
