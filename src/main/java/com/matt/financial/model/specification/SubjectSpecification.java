package com.matt.financial.model.specification;

import com.matt.financial.model.entity.Subject;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class SubjectSpecification implements SpecificationDefault<Subject> {

    private Specification<Subject> username(String username) {
        return (root, query, cb) -> cb.equal(root.get("username"), username);
    }

    private Specification<Subject> email(String email) {
        return (root, query, cb) -> cb.equal(root.get("email"), email);
    }

    private Specification<Subject> phone(String phone) {
        return (root, query, cb) -> cb.equal(root.get("phone"), phone);
    }

    public Specification<Subject> filter(Subject subject) {
        var builder = this.builder();
        var subjectOptional = ofNullable(subject);

        subjectOptional.map(Subject::getName).map(this::name).ifPresent(builder::and);
        subjectOptional.map(Subject::getUsername).map(this::username).ifPresent(builder::and);
        subjectOptional.map(Subject::getEmail).map(this::email).ifPresent(builder::and);
        subjectOptional.map(Subject::getPhone).map(this::phone).ifPresent(builder::and);
        subjectOptional.map(Subject::getActive).map(this::isActive).ifPresent(builder::and);

        return builder.build();
    }
}
