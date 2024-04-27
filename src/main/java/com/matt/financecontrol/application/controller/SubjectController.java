package com.matt.financecontrol.application.controller;

import com.matt.financecontrol.application.records.SubjectRecord;
import com.matt.financecontrol.application.records.TokenRecord;
import com.matt.financecontrol.config.security.service.TokenService;
import com.matt.financecontrol.model.entity.Subject;
import com.matt.financecontrol.model.service.SubjectService;
import com.matt.financecontrol.validations.SubjectValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("subject")
public class SubjectController {

    private final AuthenticationManager authenticationManager;
    private final SubjectValidator subjectValidator;
    private final SubjectService subjectService;
    private final TokenService tokenService;

    @Autowired
    public SubjectController(AuthenticationManager authenticationManager, SubjectValidator subjectValidator,
                             SubjectService subjectService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.subjectValidator = subjectValidator;
        this.subjectService = subjectService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid SubjectRecord subjectRecord) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                subjectRecord.username(), subjectRecord.password()
        );
        var authentication = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok(new TokenRecord(
                this.subjectService.findByUsername(authentication.getName()).getId(),
                this.tokenService.generateToken((Subject) authentication.getPrincipal()))
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid SubjectRecord subjectRecord) {
        var subject = this.subjectValidator.encryptPassword(subjectRecord);

        this.subjectService.create(subject);

        return ResponseEntity.ok().build();
    }
}
