package com.matt.financial.model.entity;

import com.matt.financial.model.enumerations.WorkspaceType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Entity
@Table(name = "workspace")
@Data
public class Workspace implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID_V1")
    private UUID id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "workspace_type", nullable = false)
    private WorkspaceType workspaceType;

    @Column(name = "active", nullable = false)
    private Boolean active = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false, updatable = false)
    private Subject subject;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Month> months = new ArrayList<>();

    @Transient
    private List<Integer> monthNumberList;

    public void mergeForUpdate(Workspace workspace) {
        this.name = ofNullable(workspace.getName()).orElse(this.name);
        this.description = ofNullable(workspace.getDescription()).orElse(this.description);
        this.workspaceType = ofNullable(workspace.getWorkspaceType()).orElse(this.workspaceType);
        this.active = ofNullable(workspace.getActive()).orElse(this.active);
        this.months = ofNullable(workspace.getMonths()).orElse(this.months);
    }
}
