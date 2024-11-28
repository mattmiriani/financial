package com.matt.financial.strategy.factory;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Workspace;
import com.matt.financial.model.enumerations.Operation;
import com.matt.financial.strategy.Validation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;

@Component
public class WorkspaceFactory {

    private final Map<Operation, Validation<Workspace>> validations = new HashMap<>();

    public WorkspaceFactory(Set<Validation<Workspace>> validations) {
        validations.forEach(validation -> this.validations.put(validation.getOperation(), validation));
    }

    public Validation<Workspace> getValidation(Operation operation) {
        var validation = validations.get(operation);

        if (isNull(validation)) {
            throw new FinancialBusinessException("Operation not supported");
        }

        return validation;
    }
}
