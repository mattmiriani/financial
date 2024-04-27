package com.matt.financecontrol.model.specification;

import com.matt.financecontrol.config.security.entity.GroupAuthority;
import com.matt.financecontrol.model.entity.Subject;
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

    private Specification<Subject> groupAuthority(GroupAuthority groupAuthority) {
        return (root, query, cb) -> cb.equal(root.join("groupAuthority").get("id"), groupAuthority.getId());
    }

    public Specification<Subject> filter(Subject subject) {
        var builder = this.builder();

        ofNullable(subject.getName()).map(this::name).ifPresent(builder::and);
        ofNullable(subject.getUsername()).map(this::username).ifPresent(builder::and);
        ofNullable(subject.getEmail()).map(this::email).ifPresent(builder::and);
        ofNullable(subject.getPhone()).map(this::phone).ifPresent(builder::and);
        ofNullable(subject.getActive()).map(this::isActive).ifPresent(builder::and);
        ofNullable(subject.getGroupAuthority()).map(this::groupAuthority).ifPresent(builder::and);

        return builder.build();
    }
}
