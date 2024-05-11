package com.matt.financial.application.controller;

import com.matt.financial.application.records.TokenRecord;
import com.matt.financial.config.security.service.TokenService;
import com.matt.financial.model.entity.Subject;
import com.matt.financial.model.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("subject")
public class SubjectController {

    private final AuthenticationManager authenticationManager;
    private final SubjectService subjectService;
    private final TokenService tokenService;

    @Autowired
    public SubjectController(AuthenticationManager authenticationManager, SubjectService subjectService,
                             TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.subjectService = subjectService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Subject subject) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                subject.getUsername(), subject.getPassword()
        );
        var authentication = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok(new TokenRecord(
                this.subjectService.findByUsername(authentication.getName()).getId(),
                this.tokenService.generateToken((Subject) authentication.getPrincipal()))
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid Subject subject) {
        var subjectEncrypted = new Subject(subject, new BCryptPasswordEncoder().encode(subject.getPassword()));

        this.subjectService.create(subjectEncrypted);

        return ResponseEntity.ok().build();
    }
}
