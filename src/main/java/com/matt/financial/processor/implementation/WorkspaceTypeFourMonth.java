package com.matt.financial.processor.implementation;

import com.matt.financial.model.entity.Month;
import com.matt.financial.model.entity.Workspace;
import com.matt.financial.processor.WorkspaceTypeProcessor;

import java.time.LocalDate;
import java.util.ArrayList;

public class WorkspaceTypeFourMonth implements WorkspaceTypeProcessor {

    @Override
    public void process(Workspace workspace) {
        var months = new ArrayList<Month>();
        var initialMonth = LocalDate.now().getMonth().ordinal();
        var finalMonth = LocalDate.now().getMonth().plus(4).ordinal();

        this.process(months, workspace, initialMonth, finalMonth);
    }
}
