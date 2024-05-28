package com.matt.financial.processor.implementation;

import com.matt.financial.model.entity.Month;
import com.matt.financial.model.entity.Workspace;
import com.matt.financial.processor.WorkspaceTypeProcessor;

import java.time.LocalDate;
import java.util.ArrayList;

public class WorkspaceTypeFourMonth implements WorkspaceTypeProcessor {

    private static final Integer FOUR_MONTH = 4;

    @Override
    public void process(Workspace workspace) {
        var months = new ArrayList<Month>();
        var initialMonth = LocalDate.now().getMonth().ordinal();
        var finalMonth = LocalDate.now().getMonth().plus(FOUR_MONTH).ordinal();

        this.process(months, workspace, initialMonth, finalMonth);
    }
}
