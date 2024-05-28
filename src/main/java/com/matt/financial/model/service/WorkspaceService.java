package com.matt.financial.model.service;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Workspace;
import com.matt.financial.model.repository.WorkspaceRepository;
import com.matt.financial.model.specification.WorkspaceSpecification;
import com.matt.financial.strategy.factory.WorkspaceFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.matt.financial.model.enumerations.Operation.CREATE;
import static com.matt.financial.model.enumerations.Operation.UPDATE;

@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class WorkspaceService {

    private final WorkspaceSpecification workspaceSpecification;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceFactory workspaceFactory;

    @Transactional(readOnly = true)
    public Page<Workspace> findAll(Workspace workspace, Pageable pageable) {
        return workspaceRepository.findAll(workspaceSpecification.filter(workspace), pageable);
    }

    @Transactional(readOnly = true)
    public Workspace findById(UUID workspaceId) {
        return workspaceRepository.findById(workspaceId).orElseThrow(
                () -> new FinancialBusinessException("Workspace not found")
        );
    }

    private Workspace save(Workspace workspace) {
        return workspaceRepository.save(workspace);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Workspace create(Workspace workspace) {
        workspaceFactory.getValidation(CREATE).execute(workspace);

        return this.save(workspace);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Workspace update(Workspace workspace) {
        var workspaceToUpdate = this.findById(workspace.getId());

        workspaceFactory.getValidation(UPDATE).execute(workspace);

        workspaceToUpdate.mergeForUpdate(workspace);

        return this.save(workspaceToUpdate);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean activateOrDeactivate(UUID workspaceId) {
        var workspaceToUpdate = this.findById(workspaceId);

        workspaceToUpdate.setActive(!workspaceToUpdate.getActive());

        return this.save(workspaceToUpdate).getActive();
    }
}
