package com.matt.financial.strategy.implementation;

import com.matt.financial.model.entity.Workspace;
import com.matt.financial.model.enumerations.Operation;
import com.matt.financial.strategy.Validation;

public class WorkspaceUpdate implements Validation<Workspace> {

    @Override
    public void execute(Workspace entity) {

    }

    @Override
    public Operation getOperation() {
        return Operation.UPDATE;
    }
}
