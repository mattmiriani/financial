package com.matt.financecontrol.validations;

import com.matt.financecontrol.application.records.SubjectRecord;
import com.matt.financecontrol.config.FinanceControlBusinessException;
import com.matt.financecontrol.config.annotation.Validator;
import com.matt.financecontrol.model.entity.Subject;
import com.matt.financecontrol.model.repository.SubjectRepository;
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

    public Subject encryptPassword(SubjectRecord subjectRecord) {
        return new Subject(
                subjectRecord,
                new BCryptPasswordEncoder().encode(subjectRecord.password())
        );
    }

    @Transactional(readOnly = true)
    public void existsByUsername(String username) {
        if (ofNullable(this.subjectRepository.findSubjectByUsername(username)).isPresent()) {

            throw new FinanceControlBusinessException("Username is already taken");
        };
    }

    @Transactional(readOnly = true)
    public void existsByEmail(String email) {
        if (ofNullable(this.subjectRepository.findByEmail(email)).isPresent()) {

            throw new FinanceControlBusinessException("Email is already taken");
        };
    }
}
