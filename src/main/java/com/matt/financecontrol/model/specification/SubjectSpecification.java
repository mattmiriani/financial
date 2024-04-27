package com.matt.financecontrol.model.specification;

import com.matt.financecontrol.application.records.SubjectRecord;
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

    public Specification<Subject> filter(SubjectRecord subjectRecord) {
        var builder = this.builder();

        ofNullable(subjectRecord.name()).map(this::name).ifPresent(builder::and);
        ofNullable(subjectRecord.username()).map(this::username).ifPresent(builder::and);
        ofNullable(subjectRecord.email()).map(this::email).ifPresent(builder::and);
        ofNullable(subjectRecord.phone()).map(this::phone).ifPresent(builder::and);
        builder.and(this.isActive(subjectRecord.active()));
        ofNullable(subjectRecord.groupAuthority()).map(this::groupAuthority).ifPresent(builder::and);

        return builder.build();
    }
}
