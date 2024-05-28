package com.matt.financial.application.graphqlcontroller;

import com.matt.financial.application.records.PageableRecord;
import com.matt.financial.model.entity.Subject;
import com.matt.financial.model.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class SubjectGraphQLController {

    private final SubjectService subjectService;

    @QueryMapping
    public Page<Subject> findAllSubjects(@Argument Subject subject,
                                         @Argument PageableRecord pageableRecord) {
        return this.subjectService.findAll(subject, pageableRecord.getPageable());
    }

    @QueryMapping
    public Subject findSubjectById(@Argument UUID subjectId) {
        return this.subjectService.findById(subjectId);
    }

    @MutationMapping
    public Subject updateSubject(@Argument Subject subject) {
        return this.subjectService.update(subject);
    }

    @MutationMapping
    public Subject updateSubjectUsername(@Argument Subject subject,
                                         @Argument String username) {
        return this.subjectService.updateToUsername(subject, username);
    }

    @MutationMapping
    public Subject updateSubjectEmail(@Argument Subject subject,
                                      @Argument String email) {
        return this.subjectService.updateToEmail(subject, email);
    }

    @MutationMapping
    public boolean activateOrDeactivate(@Argument Subject subject) {
        return this.subjectService.activateOrDeactivate(subject.getId());
    }
}
