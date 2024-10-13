package com.matt.financial.processor;

import com.matt.financial.model.entity.Month;
import com.matt.financial.model.entity.Workspace;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface WorkspaceTypeProcessor {

    void process(Workspace workspace);

    default void process(List<Month> months, Workspace workspace) {
        IntStream.range(0, 12).forEach(mes -> {
            var month = new Month();
            month.setName(java.time.Month.of(mes).name());
            month.setWorkspace(workspace);
            months.add(month);
        });

        workspace.setMonths(months);
    }

    default void process(List<Month> months, Workspace workspace,
                         List<Integer> monthsList) {
        monthsList.forEach(month -> {
            var monthEntity = new Month();
            monthEntity.setName(java.time.Month.of(month).name());
            monthEntity.setWorkspace(workspace);
            months.add(monthEntity);
        });

        workspace.setMonths(months);
    }

    default void process(List<Month> months, Workspace workspace,
                         Integer initialMonth, Integer finalMonth) {
        Stream.iterate(initialMonth, month -> month == 12 ? 1 : month + 1)
                .limit((finalMonth >= initialMonth ? finalMonth - initialMonth + 1 : 12 - initialMonth + finalMonth))
                .forEach(month -> {
                    var monthEntity = new Month();
                    monthEntity.setName(java.time.Month.of(month).name());
                    monthEntity.setWorkspace(workspace);
                    months.add(monthEntity);
                });

        workspace.setMonths(months);
    }
}
