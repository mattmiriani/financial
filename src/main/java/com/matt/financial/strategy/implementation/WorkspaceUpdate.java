package com.matt.financial.strategy.implementation;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Workspace;
import com.matt.financial.model.enumerations.Operation;
import com.matt.financial.model.service.WorkspaceService;
import com.matt.financial.strategy.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkspaceUpdate implements Validation<Workspace> {

    private final WorkspaceService workspaceService;

    @Override
    public void execute(Workspace entity) {
        var workspaceToUpdate = workspaceService.findById(entity.getId());

        if (!workspaceToUpdate.getActive()) {
            throw new FinancialBusinessException("Workspace is not active");
        }
    }

    @Override
    public Operation getOperation() {
        return Operation.UPDATE;
    }
}
