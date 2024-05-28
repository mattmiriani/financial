package com.matt.financial.model.service;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Subject;
import com.matt.financial.model.repository.SubjectRepository;
import com.matt.financial.model.specification.SubjectSpecification;
import com.matt.financial.strategy.factory.SubjectFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.matt.financial.model.enumerations.Operation.CREATE;
import static com.matt.financial.model.enumerations.Operation.UPDATE;
import static java.util.Optional.ofNullable;

@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class SubjectService {

    private final SubjectSpecification subjectSpecification;
    private final SubjectRepository subjectRepository;
    private final SubjectFactory subjectFactory;

    @Transactional(readOnly = true)
    public Page<Subject> findAll(Subject subject, Pageable pageable) {
        return subjectRepository.findAll(subjectSpecification.filter(subject), pageable);
    }

    @Transactional(readOnly = true)
    public Subject findById(UUID subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(
                () -> new FinancialBusinessException("Subject not found")
        );
    }

    @Transactional(readOnly = true)
    public Subject findByUsername(String username) {
        return (Subject) ofNullable(subjectRepository.findSubjectByUsername(username)).orElseThrow(
                () -> new FinancialBusinessException("Subject not found")
        );
    }

    @Transactional(readOnly = true)
    public Subject findByEmail(String email) {
        return ofNullable(subjectRepository.findByEmail(email)).orElseThrow(
                () -> new FinancialBusinessException("Subject not found")
        );
    }

    private Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Subject create(Subject subject) {
        subjectFactory.getValidation(CREATE).execute(subject);

        return this.save(subject);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Subject update(Subject subject) {
        var subjectToUpdate = this.findById(subject.getId());

        subjectFactory.getValidation(UPDATE).execute(subject);

        subjectToUpdate.mergeForUpdate(subject);

        return this.save(subjectToUpdate);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Subject updateToUsername(Subject subject, String newUsername) {
        subject.setUsername(newUsername);

        return this.update(subject);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Subject updateToEmail(Subject subject, String newEmail) {
        subject.setEmail(newEmail);

        return this.update(subject);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean activateOrDeactivate(UUID subjectId) {
        var subjectToUpdate = this.findById(subjectId);

        subjectToUpdate.setActive(!subjectToUpdate.getActive());

        return this.save(subjectToUpdate).getActive();
    }
}
