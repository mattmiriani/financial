package com.matt.financial.application.graphqlcontroller;

import com.matt.financial.application.records.PageableRecord;
import com.matt.financial.model.entity.Workspace;
import com.matt.financial.model.service.WorkspaceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class WorkspaceGraphQLController {

    private final WorkspaceService workspaceService;

    @QueryMapping
    public Page<Workspace> findAllWorkspaces(@Argument Workspace workspace,
                                             @Argument PageableRecord pageableRecord) {
        return this.workspaceService.findAll(workspace, pageableRecord.getPageable());
    }

    @QueryMapping
    public Workspace findWorkspaceById(@Argument UUID workspaceId) {
        return this.workspaceService.findById(workspaceId);
    }

    @MutationMapping
    public Workspace createWorkspace(@Argument Workspace workspace) {
        return this.workspaceService.create(workspace);
    }

    @MutationMapping
    public Workspace updateWorkspace(@Argument Workspace workspace) {
        return this.workspaceService.update(workspace);
    }

    @MutationMapping
    public boolean activateOrDeactivateWorkspace(@Argument UUID workspaceId) {
        return this.workspaceService.activateOrDeactivate(workspaceId);
    }
}
