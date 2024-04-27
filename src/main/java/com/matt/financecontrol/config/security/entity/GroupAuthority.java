package com.matt.financecontrol.config.security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "group_authority")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class GroupAuthority implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID_V1")
    private UUID id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "groupAuthority", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Authority> authorities = new ArrayList<>();

    public void mergeForUpdate(GroupAuthority groupAuthority) {
        this.name = groupAuthority.getName();

        groupAuthority.getAuthorities().stream()
                .filter(Objects::nonNull)
                .peek(authority -> this.getAuthorities().clear())
                .forEach(authority -> {
                    authority.setGroupAuthority(this);
                    this.authorities.add(authority);
                });
    }

}
