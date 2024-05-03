package com.matt.financial.config.security.repository;

import com.matt.financial.config.security.entity.GroupAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupAuthorityRepository extends JpaRepository<GroupAuthority, UUID>, JpaSpecificationExecutor<GroupAuthority> {

}
