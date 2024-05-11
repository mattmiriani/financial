package com.matt.financial.service;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Subject;
import com.matt.financial.model.repository.SubjectRepository;
import com.matt.financial.model.service.SubjectService;
import com.matt.financial.model.specification.SubjectSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class SubjectServiceTest {

    private final UUID subjectId = UUID.randomUUID();

    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private SubjectSpecification subjectSpecification;
    @InjectMocks
    private SubjectService subjectService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll() {
    }

    @Test
    void findByIdSuccess() {
        var subject = this.createListSubjects(1L).get(0);
        subject.setId(this.subjectId);

        when(this.subjectRepository.findById(this.subjectId))
                .thenReturn(Optional.of(subject));

        var result = this.subjectService.findById(this.subjectId);

        assertEquals(subject, result);
        verify(this.subjectRepository, times(1)).findById(this.subjectId);
    }

    @Test
    void findByIdThrows() {
        when(this.subjectRepository.findById(this.subjectId))
                .thenReturn(Optional.empty());

        assertThrows(FinancialBusinessException.class, () -> {
            this.subjectService.findById(this.subjectId);
        });
        verify(this.subjectRepository, times(1)).findById(this.subjectId);
    }

    @Test
    void findByUsernameSuccess() {
        var subject = this.createListSubjects(1L).get(0);
        var username = "testeUsername";
        subject.setUsername(username);

        when(this.subjectRepository.findSubjectByUsername(username))
                .thenReturn(subject);

        var result = this.subjectService.findByUsername(username);

        assertEquals(subject, result);
        verify(this.subjectRepository, times(1)).findSubjectByUsername(username);
    }

    @Test
    void findByUsernameThrows() {
        var username = "testeUsername";

        when(this.subjectRepository.findSubjectByUsername(username))
                .thenReturn(null);

        assertThrows(FinancialBusinessException.class, () -> {
            this.subjectService.findByUsername(username);
        });
        verify(this.subjectRepository, times(1)).findSubjectByUsername(username);
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void updateToUsername() {
    }

    @Test
    void updateToEmail() {
    }

    @Test
    void activateOrDeactivate() {
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