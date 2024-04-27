package com.matt.financecontrol.application.records;

import com.matt.financecontrol.config.security.entity.GroupAuthority;
import jakarta.validation.constraints.Email;

public record SubjectRecord(
        String name,
        String username,
        @Email
        String email,
        String phone,
        String password,
        boolean active,
        GroupAuthority groupAuthority
) {

}
