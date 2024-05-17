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
public class SubjectUpdate implements Validation<Subject> {

    private final SubjectRepository subjectRepository;

    @Override
    public void execute(Subject subject) {
        var subjectToUpdate = subjectRepository.findById(subject.getId()).orElseThrow(
                () -> new FinancialBusinessException("Subject not found")
        );

        if (!subjectToUpdate.getUsername().equals(subject.getUsername()) &&
                nonNull(subjectRepository.findSubjectByUsername(subject.getUsername()))) {
            throw new FinancialBusinessException("Username already exists");
        }

        if (!subjectToUpdate.getEmail().equals(subject.getEmail()) &&
                nonNull(subjectRepository.findByEmail(subject.getEmail()))) {
            throw new FinancialBusinessException("Email already exists");
        }
    }

    @Override
    public Operation getOperation() {
        return Operation.UPDATE;
    }
}
