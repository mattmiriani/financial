package com.matt.financial.service;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.config.security.entity.Authority;
import com.matt.financial.config.security.entity.GroupAuthority;
import com.matt.financial.config.security.repository.GroupAuthorityRepository;
import com.matt.financial.config.security.service.GroupAuthorityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class findById {

        @Test
        void testFindByIdSuccess() {
            when(groupAuthorityRepository.findById(groupAuthorityId))
                    .thenReturn(Optional.of(newGroupAuthority));

            var result = groupAuthorityService.findById(groupAuthorityId);

            assertEquals(newGroupAuthority, result);
            verify(groupAuthorityRepository, times(1)).findById(groupAuthorityId);
        }

        @Test
        void testFindByIdThrows() {
            when(groupAuthorityRepository.findById(groupAuthorityId))
                    .thenReturn(Optional.empty());

            assertThrows(FinancialBusinessException.class, () -> {
                groupAuthorityService.findById(groupAuthorityId);
            });
            verify(groupAuthorityRepository, times(1)).findById(groupAuthorityId);
        }
    }

    @Nested
    class create {
        @Test
        void testeCreate() {
            when(groupAuthorityRepository.save(any(GroupAuthority.class)))
                    .thenReturn(newGroupAuthority);

            var result = groupAuthorityService.create(newGroupAuthority);

            assertEquals(groupAuthorityId, result.getId());
            assertEquals("name teste", result.getName());
            assertEquals(new ArrayList<Authority>(), result.getAuthorities());
        }
    }

    @Nested
    class update {

        @Test
        void testUpdate() {
            var groupAuthority = newGroupAuthority;

            when(groupAuthorityRepository.findById(groupAuthority.getId()))
                    .thenReturn(Optional.of(groupAuthority));

            groupAuthority.setName("name teste update");

            when(groupAuthorityRepository.save(any(GroupAuthority.class)))
                    .thenReturn(groupAuthority);

            var result = groupAuthorityService.update(groupAuthority);

            verify(groupAuthorityRepository, times(1)).findById(groupAuthority.getId());
            verify(groupAuthorityRepository, times(1)).save(groupAuthority);
            assertEquals(groupAuthority, result);
        }
    }
}