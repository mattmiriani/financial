package com.matt.financecontrol.config.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.matt.financecontrol.config.FinanceControlBusinessException;
import com.matt.financecontrol.config.properties.ApplicationProperties;
import com.matt.financecontrol.model.entity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.util.Optional.ofNullable;

@Service
public class TokenService {

    private final String KEY_API;

    private final ApplicationProperties applicationProperties;

    @Autowired
    public TokenService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;

        this.KEY_API = ofNullable(applicationProperties.getKeyApi()).orElse("secret");
    }

    public String generateToken(Subject subject) {
        try {
            var algorithm = Algorithm.HMAC256(KEY_API);

            return JWT.create()
                    .withIssuer("secret-api")
                    .withSubject(subject.getUsername())
                    .withExpiresAt(this.generationExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new FinanceControlBusinessException("Error to generate token", exception);
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
            throw new FinanceControlBusinessException("Error to validate token", exception);
        }
    }

    private Instant generationExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
