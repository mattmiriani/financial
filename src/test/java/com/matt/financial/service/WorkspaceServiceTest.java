package com.matt.financial.service;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Workspace;
import com.matt.financial.model.enumerations.WorkspaceType;
import com.matt.financial.model.repository.WorkspaceRepository;
import com.matt.financial.model.service.WorkspaceService;
import com.matt.financial.model.specification.WorkspaceSpecification;
import com.matt.financial.strategy.Validation;
import com.matt.financial.strategy.factory.WorkspaceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.matt.financial.model.enumerations.Operation.CREATE;
import static com.matt.financial.model.enumerations.Operation.UPDATE;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class WorkspaceServiceTest {

    private static final UUID workspaceId = UUID.randomUUID();

    @Mock
    private WorkspaceRepository workspaceRepository;
    @Mock
    private WorkspaceSpecification workspaceSpecification;
    @Mock
    private WorkspaceFactory workspaceFactory;
    @Mock
    private Validation<Workspace> validation;

    @InjectMocks
    private WorkspaceService workspaceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private List<Workspace> createListWorkspaces(Long amount) {
        long validatedAmount = ofNullable(amount).orElse(1L);
        List<Workspace> list = new ArrayList<>();

        for (int i = 0; i < validatedAmount; i++) {
            var workspace = new Workspace();
            workspace.setId(UUID.randomUUID());
            workspace.setName("Test" + i);
            workspace.setDescription("description test" + i);
            workspace.setWorkspaceType(WorkspaceType.YEARLY);
            workspace.setActive(true);

            list.add(workspace);
        }

        return list;
    }

    @Nested
    class findAllWorkspace {

        @Test
        void findAllSuccess() {
            var list = createListWorkspaces(5L);
            var pageable = Mockito.mock(Pageable.class);
            var workspaceFilter = new Workspace();
            var workspacePage = new PageImpl<>(list);

            Specification<Workspace> mockSpecification = (Specification<Workspace>) (root, query, criteriaBuilder) -> null;
            doReturn(mockSpecification).when(workspaceSpecification).filter(workspaceFilter);
            doReturn(workspacePage).when(workspaceRepository).findAll(eq(mockSpecification), eq(pageable));

            var result = workspaceService.findAll(workspaceFilter, pageable);

            assertNotNull(result);
            assertEquals(list, result.getContent());
            verify(workspaceSpecification, times(1)).filter(workspaceFilter);
            verify(workspaceRepository, times(1)).findAll(eq(mockSpecification), eq(pageable));
        }

        @Test
        void findAllSuccessIsEmpty() {
            var pageable = Mockito.mock(Pageable.class);
            var workspaceFilter = new Workspace();
            var workspacePage = new PageImpl<>(new ArrayList<>());

            Specification<Workspace> mockSpecification = (Specification<Workspace>) (root, query, criteriaBuilder) -> null;
            doReturn(mockSpecification).when(workspaceSpecification).filter(workspaceFilter);
            doReturn(workspacePage).when(workspaceRepository).findAll(eq(mockSpecification), eq(pageable));

            var result = workspaceService.findAll(workspaceFilter, pageable);

            assertNotNull(result);
            assertEquals(new ArrayList<>(), result.getContent());
            verify(workspaceSpecification, times(1)).filter(workspaceFilter);
            verify(workspaceRepository, times(1)).findAll(eq(mockSpecification), eq(pageable));
        }
    }

    @Nested
    class findByIdWorkspace {

        @Test
        void findByIdSuccess() {
            var workspace = new Workspace();
            workspace.setId(workspaceId);
            doReturn(Optional.of(workspace))
                    .when(workspaceRepository)
                    .findById(workspaceId);

            var result = workspaceService.findById(workspaceId);

            assertEquals(workspace, result);
            verify(workspaceRepository, times(1)).findById(workspaceId);
        }

        @Test
        void findByIdThrow() {
            doReturn(Optional.empty())
                    .when(workspaceRepository)
                    .findById(workspaceId);

            assertThrows(FinancialBusinessException.class, () ->
                    workspaceService.findById(workspaceId)
            );
            verify(workspaceRepository, times(1)).findById(workspaceId);
        }

    }

    @Nested
    class createWorkspace {

        @Test
        void testCreateSubject() {
            var workspace = createListWorkspaces(1L).getFirst();
            workspace.setId(workspaceId);

            when(workspaceFactory.getValidation(CREATE)).thenReturn(validation);
            doNothing().when(validation).execute(workspace);
            doReturn(workspace).when(workspaceRepository).save(workspace);

            var result = workspaceService.create(workspace);

            verify(workspaceFactory).getValidation(CREATE);
            verify(validation).execute(workspace);
            verify(workspaceRepository).save(workspace);
            assertEquals(workspace, result);
        }
    }

    @Nested
    class updateWorkspace {

        @Test
        void testUpdateSubject() {
            var workspace = createListWorkspaces(1L).getFirst();
            workspace.setId(workspaceId);
            workspace.setName("oldName");

            doReturn(Optional.of(workspace)).when(workspaceRepository).findById(workspaceId);

            workspace.setName("newName");

            when(workspaceFactory.getValidation(UPDATE)).thenReturn(validation);
            doNothing().when(validation).execute(workspace);
            doReturn(workspace).when(workspaceRepository).save(workspace);

            var result = workspaceService.update(workspace);

            verify(workspaceFactory).getValidation(UPDATE);
            verify(validation).execute(workspace);
            verify(workspaceRepository).save(workspace);
            assertEquals(workspace, result);
        }
    }

    @Nested
    class activateOrDeactivateWorkspace {

        @Test
        void testActivateOrDeactivateSubject() {
            var workspace = createListWorkspaces(1L).getFirst();
            workspace.setId(workspaceId);

            doReturn(Optional.of(workspace)).when(workspaceRepository).findById(workspaceId);
            doReturn(workspace).when(workspaceRepository).save(workspace);

            var result = workspaceService.activateOrDeactivate(workspaceId);

            assertFalse(result);
            verify(workspaceRepository).save(workspace);
        }
    }
}