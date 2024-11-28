package com.matt.financial.model.repository;

import com.matt.financial.model.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, UUID>, JpaSpecificationExecutor<Workspace> {
}
