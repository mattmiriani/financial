package com.matt.financial.validations;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.config.annotation.Validator;
import com.matt.financial.model.entity.Subject;
import com.matt.financial.model.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Optional.ofNullable;

@Validator
public class SubjectValidator {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectValidator(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject encryptPassword(Subject subject) {
        return new Subject(subject, new BCryptPasswordEncoder().encode(subject.getPassword()));
    }

    @Transactional(readOnly = true)
    public void existsByUsername(String username) {
        if (ofNullable(this.subjectRepository.findSubjectByUsername(username)).isPresent()) {

            throw new FinancialBusinessException("Username is already taken");
        };
    }

    @Transactional(readOnly = true)
    public void existsByEmail(String email) {
        if (ofNullable(this.subjectRepository.findByEmail(email)).isPresent()) {

            throw new FinancialBusinessException("Email is already taken");
        };
    }
}
