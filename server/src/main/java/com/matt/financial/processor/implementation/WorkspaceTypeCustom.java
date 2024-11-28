package com.matt.financial.processor.implementation;

import com.matt.financial.model.entity.Month;
import com.matt.financial.model.entity.Workspace;
import com.matt.financial.processor.WorkspaceTypeProcessor;

import java.util.ArrayList;

public class WorkspaceTypeCustom implements WorkspaceTypeProcessor {

    @Override
    public void process(Workspace workspace) {
        var months = new ArrayList<Month>();

        this.process(months, workspace, workspace.getMonthNumberList());
    }
}
