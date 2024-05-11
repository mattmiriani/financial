package com.matt.financial.service;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.config.security.entity.Authority;
import com.matt.financial.config.security.entity.GroupAuthority;
import com.matt.financial.config.security.repository.GroupAuthorityRepository;
import com.matt.financial.config.security.service.GroupAuthorityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class GroupAuthorityServiceTest {

    private final UUID groupAuthorityId = UUID.randomUUID();
    private final GroupAuthority newGroupAuthority = new GroupAuthority(
            this.groupAuthorityId, "name teste",
            new ArrayList<Authority>()
    );

    @Mock
    private GroupAuthorityRepository groupAuthorityRepository;
    @InjectMocks
    private GroupAuthorityService groupAuthorityService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test find by id")
    void findByIdSuccess() {
        when(this.groupAuthorityRepository.findById(this.groupAuthorityId))
                .thenReturn(Optional.of(newGroupAuthority));

        var result = this.groupAuthorityService.findById(this.groupAuthorityId);

        assertEquals(newGroupAuthority, result);
        verify(this.groupAuthorityRepository, times(1)).findById(this.groupAuthorityId);
    }

    @Test
    @DisplayName("Test find by id throws")
    void findByIdThrows() {
        when(this.groupAuthorityRepository.findById(this.groupAuthorityId))
                .thenReturn(Optional.empty());

        assertThrows(FinancialBusinessException.class, () -> {
            this.groupAuthorityService.findById(this.groupAuthorityId);
        });
        verify(this.groupAuthorityRepository, times(1)).findById(this.groupAuthorityId);
    }

    @Test
    @DisplayName("Test create")
    void create() {
        when(this.groupAuthorityRepository.save(any(GroupAuthority.class)))
                .thenReturn(this.newGroupAuthority);

        var result = this.groupAuthorityService.create(this.newGroupAuthority);

        assertEquals(this.groupAuthorityId, result.getId());
        assertEquals("name teste", result.getName());
        assertEquals(new ArrayList<Authority>(), result.getAuthorities());
    }

    @Test
    @DisplayName("Test update")
    void update() {
        var groupAuthority = this.newGroupAuthority;

        when(this.groupAuthorityRepository.findById(groupAuthority.getId()))
                .thenReturn(Optional.of(groupAuthority));

        groupAuthority.setName("name teste update");

        when(this.groupAuthorityRepository.save(any(GroupAuthority.class)))
                .thenReturn(groupAuthority);

        var result = this.groupAuthorityService.update(groupAuthority);

        verify(this.groupAuthorityRepository, times(1)).findById(groupAuthority.getId());
        verify(this.groupAuthorityRepository, times(1)).save(groupAuthority);
        assertEquals(groupAuthority, result);
    }
}