package com.matt.financecontrol.application.graphqlcontroller;

import com.matt.financecontrol.application.records.PageableRecord;
import com.matt.financecontrol.application.records.SubjectRecord;
import com.matt.financecontrol.model.entity.Subject;
import com.matt.financecontrol.model.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class SubjectGraphQLController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectGraphQLController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @QueryMapping
    public Page<Subject> findAllSubjects(@Argument SubjectRecord subjectRecord,
                                         @Argument PageableRecord pageableRecord) {
        return this.subjectService.findAll(subjectRecord, pageableRecord.getPageable());
    }

    @QueryMapping
    public Subject findSubjectById(@Argument UUID subjectId) {
        return this.subjectService.findById(subjectId);
    }

    @MutationMapping
    public Subject updateSubject(@Argument Subject subject) {
        return this.subjectService.update(subject);
    }
}
