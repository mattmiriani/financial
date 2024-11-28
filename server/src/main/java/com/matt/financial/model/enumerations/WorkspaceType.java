package com.matt.financial.model.enumerations;

import com.matt.financial.processor.WorkspaceTypeProcessor;
import com.matt.financial.processor.implementation.WorkspaceTypeCustom;
import com.matt.financial.processor.implementation.WorkspaceTypeFourMonth;
import com.matt.financial.processor.implementation.WorkspaceTypeTrimester;
import com.matt.financial.processor.implementation.WorkspaceTypeYearly;
import lombok.Getter;

@Getter
public enum WorkspaceType {
    YEARLY(new WorkspaceTypeYearly()),
    TRIMESTER(new WorkspaceTypeTrimester()),
    FOUR_MONTH(new WorkspaceTypeFourMonth()),
    CUSTOM(new WorkspaceTypeCustom());

    private final WorkspaceTypeProcessor workspaceTypeProcessor;

    WorkspaceType(WorkspaceTypeProcessor workspaceTypeProcessor) {
        this.workspaceTypeProcessor = workspaceTypeProcessor;
    }
}
