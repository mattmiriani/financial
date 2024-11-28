package com.matt.financial.config.security.controller;

import com.matt.financial.config.FinancialBusinessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/keycloak")
public class KeycloakController {

    public record Subject(String username, String password) {
    }

    @PostMapping(value = "/getToken")
    public ResponseEntity<String> getToken(@RequestBody Subject subject) {
        try {
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            var formData = new LinkedMultiValueMap<String, String>();
            formData.add("username", subject.username);
            formData.add("password", subject.password);
            formData.add("client_id", "development");
            formData.add("grant_type", "password");

            var entity = new HttpEntity<>(formData, headers);

            return new RestTemplate().postForEntity("http://localhost:8080/realms/development/protocol/openid-connect/token", entity, String.class);
        } catch (Exception e) {
            throw new FinancialBusinessException("Failed to get token!", e);
        }
    }
}
