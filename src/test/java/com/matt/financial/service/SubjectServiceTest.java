package com.matt.financial.service;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Subject;
import com.matt.financial.model.repository.SubjectRepository;
import com.matt.financial.model.service.SubjectService;
import com.matt.financial.model.specification.SubjectSpecification;
import com.matt.financial.strategy.Validation;
import com.matt.financial.strategy.factory.SubjectFactory;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.matt.financial.model.enumerations.Operation.CREATE;
import static com.matt.financial.model.enumerations.Operation.UPDATE;
import static java.util.Optional.ofNullable;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class SubjectServiceTest {

    private final static UUID workspaceId = UUID.randomUUID();

    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private SubjectSpecification subjectSpecification;
    @Mock
    private SubjectFactory subjectFactory;
    @Mock
    private Validation<Subject> validation;

    @InjectMocks
    private SubjectService subjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class findAllSubject {

        @Test
        void findAllSuccess() {
            var subjects = createListSubjects(5L);
            var pageable = Mockito.mock(Pageable.class);
            var subjectFilter = new Subject();
            var subjectPage = new PageImpl<>(subjects);

            Specification<Subject> mockSpecification = (Specification<Subject>) (root, query, criteriaBuilder) -> null;
            doReturn(mockSpecification).when(subjectSpecification).filter(subjectFilter);
            doReturn(subjectPage).when(subjectRepository).findAll(eq(mockSpecification), eq(pageable));

            var result = subjectService.findAll(subjectFilter, pageable);

            assertNotNull(result);
            assertEquals(subjects, result.getContent());
            verify(subjectSpecification, times(1)).filter(subjectFilter);
            verify(subjectRepository, times(1)).findAll(eq(mockSpecification), eq(pageable));
        }

        @Test
        void findAllSuccessIsEmpty() {
            var pageable = Mockito.mock(Pageable.class);
            var subjectFilter = new Subject();
            var subjectPage = new PageImpl<>(new ArrayList<>());

            Specification<Subject> mockSpecification = (Specification<Subject>) (root, query, criteriaBuilder) -> null;
            doReturn(mockSpecification).when(subjectSpecification).filter(subjectFilter);
            doReturn(subjectPage).when(subjectRepository).findAll(eq(mockSpecification), eq(pageable));

            var result = subjectService.findAll(subjectFilter, pageable);

            assertNotNull(result);
            assertEquals(new ArrayList<>(), result.getContent());
            verify(subjectSpecification, times(1)).filter(subjectFilter);
            verify(subjectRepository, times(1)).findAll(eq(mockSpecification), eq(pageable));
        }
    }

    @Nested
    class findByIdSubject {
        @Test
        void findByIdSuccess() {
            var subject = createListSubjects(1L).getFirst();
            subject.setId(workspaceId);
            doReturn(Optional.of(subject))
                    .when(subjectRepository)
                    .findById(workspaceId);

            var result = subjectService.findById(workspaceId);

            assertEquals(subject, result);
            verify(subjectRepository, times(1)).findById(workspaceId);
        }

        @Test
        void findByIdThrows() {
            doReturn(Optional.empty())
                    .when(subjectRepository)
                    .findById(workspaceId);

            assertThrows(FinancialBusinessException.class,
                    () -> subjectService.findById(workspaceId)
            );
            verify(subjectRepository, times(1)).findById(workspaceId);
        }
    }

    @Nested
    class findByUsernameSubject {

        @Test
        void findByUsernameSuccess() {
            var subject = createListSubjects(1L).getFirst();
            var username = "testeUsername";
            subject.setUsername(username);

            doReturn(subject).when(subjectRepository)
                    .findSubjectByUsername(username);

            var result = subjectService.findByUsername(username);

            assertEquals(subject, result);
            verify(subjectRepository, times(1)).findSubjectByUsername(username);
        }

        @Test
        void findByUsernameThrows() {
            var username = "testeUsername";

            doReturn(null).when(subjectRepository)
                    .findSubjectByUsername(username);

            assertThrows(FinancialBusinessException.class, () ->
                    subjectService.findByUsername(username)
            );
            verify(subjectRepository, times(1)).findSubjectByUsername(username);
        }
    }

    @Nested
    class findByEmailSubject {

        @Test
        void findByEmailSubjectSuccess() {
            var subject = createListSubjects(1L).getFirst();
            var email = "email@email.email";
            subject.setUsername(email);

            doReturn(subject).when(subjectRepository)
                    .findByEmail(email);

            var result = subjectService.findByEmail(email);

            assertEquals(subject, result);
            verify(subjectRepository, times(1)).findByEmail(email);
        }

        @Test
        void findByEmailSubjectThrows() {
            var email = "email@email.email";

            doReturn(null).when(subjectRepository)
                    .findByEmail(email);

            assertThrows(FinancialBusinessException.class, () ->
                    subjectService.findByEmail(email)
            );
            verify(subjectRepository, times(1)).findByEmail(email);
        }
    }

    @Nested
    class createSubject {

        @Test
        void testCreateSubject() {
            var subject = createListSubjects(1L).getFirst();
            subject.setId(workspaceId);

            when(subjectFactory.getValidation(CREATE)).thenReturn(validation);
            doNothing().when(validation).execute(subject);
            doReturn(subject).when(subjectRepository).save(subject);

            var result = subjectService.create(subject);

            verify(subjectFactory).getValidation(CREATE);
            verify(validation).execute(subject);
            verify(subjectRepository).save(subject);
            assertEquals(subject, result);
        }
    }

    @Nested
    class updateSubject {

        @Test
        void testUpdateSubject() {
            var subject = createListSubjects(1L).getFirst();
            subject.setId(workspaceId);
            subject.setName("oldName");

            doReturn(Optional.of(subject)).when(subjectRepository).findById(workspaceId);

            subject.setName("newName");

            when(subjectFactory.getValidation(UPDATE)).thenReturn(validation);
            doNothing().when(validation).execute(subject);
            doReturn(subject).when(subjectRepository).save(subject);

            var result = subjectService.update(subject);

            verify(subjectFactory).getValidation(UPDATE);
            verify(validation).execute(subject);
            verify(subjectRepository).save(subject);
            assertEquals(subject, result);
        }
    }

    @Nested
    class updateSubjectUsername {

        @Test
        void testUpdateSubjectUsername() {
            var subject = createListSubjects(1L).getFirst();
            var newUsername = "newUsername";
            subject.setId(workspaceId);

            doReturn(Optional.of(subject)).when(subjectRepository).findById(workspaceId);

            when(subjectFactory.getValidation(UPDATE)).thenReturn(validation);
            doNothing().when(validation).execute(subject);
            doReturn(subject).when(subjectRepository).save(subject);

            var result = subjectService.updateToUsername(subject, newUsername);

            verify(subjectFactory).getValidation(UPDATE);
            verify(validation).execute(subject);
            verify(subjectRepository).save(subject);
            assertEquals(newUsername, result.getUsername());
        }
    }

    @Nested
    class updateSubjectEmail {

        @Test
        void testUpdateSubjectEmail() {
            var subject = createListSubjects(1L).getFirst();
            var newEmail = "newEmail@email.email";
            subject.setId(workspaceId);

            doReturn(Optional.of(subject)).when(subjectRepository).findById(workspaceId);

            when(subjectFactory.getValidation(UPDATE)).thenReturn(validation);
            doNothing().when(validation).execute(subject);
            doReturn(subject).when(subjectRepository).save(subject);

            var result = subjectService.updateToEmail(subject, newEmail);

            verify(subjectFactory).getValidation(UPDATE);
            verify(validation).execute(subject);
            verify(subjectRepository).save(subject);
            assertEquals(newEmail, result.getEmail());
        }
    }

    @Nested
    class activateOrDeactivateSubject {

        @Test
        void testActivateOrDeactivateSubject() {
            var subject = createListSubjects(1L).getFirst();
            subject.setId(workspaceId);

            doReturn(Optional.of(subject)).when(subjectRepository).findById(workspaceId);
            doReturn(subject).when(subjectRepository).save(subject);

            var result = subjectService.activateOrDeactivate(workspaceId);

            assertFalse(result);
            verify(subjectRepository).save(subject);
        }
    }

    private List<Subject> createListSubjects(Long amount) {
        long validatedAmount = ofNullable(amount).orElse(1L);
        List<Subject> list = new ArrayList<>();

        for (int i = 0; i < validatedAmount; i++) {
            var subject = new Subject();
            subject.setId(UUID.randomUUID());
            subject.setName("Test" + i);
            subject.setUsername("test" + i);
            subject.setEmail("test" + i + "@financial.com");
            subject.setActive(true);
            subject.setPhone("0" + i + "00000000");

            list.add(subject);
        }

        return list;
    }
}