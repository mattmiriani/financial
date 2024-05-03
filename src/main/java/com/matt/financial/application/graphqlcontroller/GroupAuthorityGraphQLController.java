package com.matt.financecontrol.application.graphqlcontroller;

import com.matt.financecontrol.config.security.entity.GroupAuthority;
import com.matt.financecontrol.config.security.service.GroupAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class GroupAuthorityGraphQLController {

    private final GroupAuthorityService groupAuthorityService;

    @Autowired
    public GroupAuthorityGraphQLController(GroupAuthorityService groupAuthorityService) {
        this.groupAuthorityService = groupAuthorityService;
    }

    @QueryMapping
    public GroupAuthority findGroupAuthorityById(@Argument UUID groupAuthorityId) {
        return this.groupAuthorityService.findById(groupAuthorityId);
    }

    @MutationMapping
    public GroupAuthority createGroupAuthority(@Argument GroupAuthority groupAuthority) {
        return this.groupAuthorityService.create(groupAuthority);
    }

    @MutationMapping
    public GroupAuthority updateGroupAuthority(@Argument GroupAuthority groupAuthority) {
        return this.groupAuthorityService.update(groupAuthority);
    }
}
