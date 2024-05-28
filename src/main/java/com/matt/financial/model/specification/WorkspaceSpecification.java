package com.matt.financial.model.specification;

import com.matt.financial.config.tools.StringTools;
import com.matt.financial.model.entity.Month;
import com.matt.financial.model.entity.Workspace;
import com.matt.financial.model.enumerations.WorkspaceType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class WorkspaceSpecification implements SpecificationDefault<Workspace> {

    private Specification<Workspace> description(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"),
                "%" + StringTools.removeAccentLower(description) + "%"
        );
    }

    private Specification<Workspace> workspaceType(WorkspaceType workspaceType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("workspaceType"), workspaceType);
    }

    private Specification<Workspace> months(List<Month> months) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("months")).value(months);
    }

    public Specification<Workspace> filter(Workspace workspace) {
        var builder = this.builder();
        var workspaceOptional = ofNullable(workspace);

        workspaceOptional.map(Workspace::getName).map(this::name).ifPresent(builder::and);
        workspaceOptional.map(Workspace::getDescription).map(this::description).ifPresent(builder::and);
        workspaceOptional.map(Workspace::getWorkspaceType).map(this::workspaceType).ifPresent(builder::and);
        workspaceOptional.map(Workspace::getActive).map(this::isActive).ifPresent(builder::and);
        workspaceOptional.map(Workspace::getMonths).map(this::months).ifPresent(builder::and);

        return builder.build();
    }
}
