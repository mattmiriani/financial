package com.matt.financial.processor.implementation;

import com.matt.financial.model.entity.Month;
import com.matt.financial.model.entity.Workspace;
import com.matt.financial.processor.WorkspaceTypeProcessor;

import java.time.LocalDate;
import java.util.ArrayList;

public class WorkspaceTypeTrimester implements WorkspaceTypeProcessor {

    private static final Integer TRIMESTER = 3;

    @Override
    public void process(Workspace workspace) {
        var months = new ArrayList<Month>();
        var initialMonth = LocalDate.now().getMonth().getValue();
        var finalMonth = LocalDate.now().getMonth().plus(TRIMESTER).getValue();

        this.process(months, workspace, initialMonth, finalMonth);
    }
}
