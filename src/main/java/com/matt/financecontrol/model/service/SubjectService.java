package com.matt.financecontrol.model.service;

import com.matt.financecontrol.application.records.SubjectRecord;
import com.matt.financecontrol.config.FinanceControlBusinessException;
import com.matt.financecontrol.model.entity.Subject;
import com.matt.financecontrol.model.repository.SubjectRepository;
import com.matt.financecontrol.model.specification.SubjectSpecification;
import com.matt.financecontrol.validations.SubjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SubjectService {

    private final SubjectSpecification subjectSpecification;
    private final SubjectRepository subjectRepository;
    private final SubjectValidator subjectValidator;

    @Autowired
    public SubjectService(SubjectSpecification subjectSpecification,SubjectRepository subjectRepository,
                          SubjectValidator subjectValidator) {
        this.subjectSpecification = subjectSpecification;
        this.subjectRepository = subjectRepository;
        this.subjectValidator = subjectValidator;
    }

    @Transactional(readOnly = true)
    public Page<Subject> findAll(SubjectRecord subjectRecord, Pageable pageable) {
        return this.subjectRepository.findAll(
                this.subjectSpecification.filter(subjectRecord),
                pageable
        );
    }

    @Transactional(readOnly = true)
    public Subject findById(UUID subjectId) {
        return this.subjectRepository.findById(subjectId)
                .orElseThrow(() -> new FinanceControlBusinessException("Subject not found"));
    }

    @Transactional(readOnly = true)
    public Subject findByUsername(String username) {
        return (Subject) this.subjectRepository.findSubjectByUsername(username);
    }

    private Subject save(Subject subject) {
        return this.subjectRepository.save(subject);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Subject create(Subject subject) {
        this.subjectValidator.existsByUsername(subject.getUsername());

        return this.save(subject);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Subject update(Subject subject) {
        var subjectToUpdate = this.findById(subject.getId());

        subjectToUpdate.mergeForUpdate(subject);

        return this.save(subjectToUpdate);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void activateOrDeactivate(UUID subjectId, boolean status) {
        var subject = this.findById(subjectId);

        subject.setActive(status);

        this.save(subject);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateToUsername(UUID subjectId, String newUsername) {
        var subject = this.findById(subjectId);

        this.subjectValidator.existsByUsername(newUsername);

        subject.setUsername(newUsername);

        this.save(subject);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateToEmail(UUID subjectId, String newEmail) {
        var subject = this.findById(subjectId);

        this.subjectValidator.existsByEmail(newEmail);

        subject.setEmail(newEmail);

        this.save(subject);
    }
}
