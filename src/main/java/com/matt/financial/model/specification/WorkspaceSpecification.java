package com.matt.financial.model.specification;

import com.matt.financial.model.entity.Workspace;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceSpecification implements SpecificationDefault<Workspace> {

    public Specification<Workspace> filter(Workspace workspace) {
        var builder = this.builder();

        return builder.build();
    }
}
