package com.matt.financial.config.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${application.key-api}")
    private String KEY_API;

    public String generateToken(Subject subject) {
        try {
            var algorithm = Algorithm.HMAC256(KEY_API);

            return JWT.create()
                    .withIssuer("secret-api")
                    .withSubject(subject.getUsername())
                    .withExpiresAt(this.generationExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new FinancialBusinessException("Error to generate token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            var algorithm = Algorithm.HMAC256(KEY_API);

            return JWT.require(algorithm)
                    .withIssuer("secret-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new FinancialBusinessException("Error to validate token", exception);
        }
    }

    private Instant generationExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
