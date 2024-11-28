package com.matt.financial.strategy.implementation;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Subject;
import com.matt.financial.model.enumerations.Operation;
import com.matt.financial.model.service.SubjectService;
import com.matt.financial.strategy.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class SubjectUpdate implements Validation<Subject> {

    private final SubjectService subjectService;

    @Override
    public void execute(Subject subject) {
        var subjectToUpdate = subjectService.findById(subject.getId());

        if (!subjectToUpdate.getActive()) {
            throw new FinancialBusinessException("Subject is not active");
        }

        if (!subjectToUpdate.getUsername().equals(subject.getUsername()) &&
                nonNull(subjectService.findByUsername(subject.getUsername()))) {
            throw new FinancialBusinessException("Username already exists");
        }

        if (!subjectToUpdate.getEmail().equals(subject.getEmail()) &&
                nonNull(subjectService.findByEmail(subject.getEmail()))) {
            throw new FinancialBusinessException("Email already exists");
        }
    }

    @Override
    public Operation getOperation() {
        return Operation.UPDATE;
    }
}
