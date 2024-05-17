package com.matt.financial.strategy.implementation;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Subject;
import com.matt.financial.model.enumerations.Operation;
import com.matt.financial.model.repository.SubjectRepository;
import com.matt.financial.strategy.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class SubjectCreate implements Validation<Subject> {

    private final SubjectRepository subjectRepository;

    @Override
    public void execute(Subject subject) {
        if (nonNull(this.subjectRepository.findSubjectByUsername(subject.getUsername()))) {
            throw new FinancialBusinessException("Username is already taken");
        }

        if (nonNull(this.subjectRepository.findByEmail(subject.getEmail()))) {
            throw new FinancialBusinessException("Email is already taken");
        }
    }

    @Override
    public Operation getOperation() {
        return Operation.CREATE;
    }
}
