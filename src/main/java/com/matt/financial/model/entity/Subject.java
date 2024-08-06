package com.matt.financial.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Entity
@Table(name = "subject")
@Data
@NoArgsConstructor
public class Subject implements Serializable {

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

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "active", nullable = false)
    private Boolean active = Boolean.TRUE;

    public Subject(Subject subject, String password) {
        this.name = subject.getName();
        this.username = subject.getUsername();
        this.email = subject.getEmail();
        this.phone = subject.getPhone();
        this.active = true;
    }

    public void mergeForUpdate(Subject subject) {
        this.name = ofNullable(subject.getName()).orElse(this.name);
        this.username = ofNullable(subject.getUsername()).orElse(this.username);
        this.email = ofNullable(subject.getEmail()).orElse(this.email);
        this.phone = ofNullable(subject.getPhone()).orElse(this.phone);
    }
}
