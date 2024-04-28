package com.matt.financecontrol.config.security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import static java.util.Optional.ofNullable;

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
        this.name = ofNullable(groupAuthority.getName()).orElse(this.name);

        ofNullable(groupAuthority.getAuthorities()).orElse(this.getAuthorities()).stream()
                .filter(Objects::nonNull)
                .peek(authority -> this.getAuthorities().clear())
                .forEach(authority -> {
                    authority.setGroupAuthority(this);
                    this.authorities.add(authority);
                });
    }

}
