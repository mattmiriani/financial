package com.matt.financial.config.security.service;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.config.security.entity.GroupAuthority;
import com.matt.financial.config.security.repository.GroupAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GroupAuthorityService {

    private final GroupAuthorityRepository groupAuthorityRepository;

    @Autowired
    public GroupAuthorityService(GroupAuthorityRepository groupAuthorityRepository) {
        this.groupAuthorityRepository = groupAuthorityRepository;
    }

    @Transactional(readOnly = true)
    public GroupAuthority findById(UUID groupAuthorityId) {
        return this.groupAuthorityRepository.findById(groupAuthorityId)
                .orElseThrow(() -> new FinancialBusinessException("Group authority not found"));
    }

    private GroupAuthority save(GroupAuthority groupAuthority) {
        return this.groupAuthorityRepository.save(groupAuthority);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public GroupAuthority create(GroupAuthority groupAuthority) {
        return this.save(groupAuthority);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public GroupAuthority update(GroupAuthority groupAuthority) {
        var groupAuthorityToUpdate = this.findById(groupAuthority.getId());

        groupAuthorityToUpdate.mergeForUpdate(groupAuthority);

        return this.save(groupAuthority);
    }
}
