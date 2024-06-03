package com.matt.financial.service;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Month;
import com.matt.financial.model.repository.MonthRepository;
import com.matt.financial.model.service.MonthService;
import com.matt.financial.model.specification.MonthSpecification;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MonthServiceTest {

    private static final UUID monthId = UUID.randomUUID();

    @Mock
    private MonthRepository monthRepository;
    @Mock
    private MonthSpecification monthSpecification;

    @InjectMocks
    private MonthService monthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private List<Month> createListMonths(Long amount) {
        long validatedAmount = ofNullable(amount).orElse(1L);
        List<Month> list = new ArrayList<>();

        for (int i = 0; i < validatedAmount; i++) {
            var month = new Month();
            month.setId(UUID.randomUUID());
            month.setName("Test" + i);
            month.setAmount(new BigDecimal(i));
            month.setActive(true);

            list.add(month);
        }

        return list;
    }

    @Nested
    class findAll {

        @Test
        void testfindAllSuccess() {
            var months = createListMonths(5L);
            var pageable = Mockito.mock(Pageable.class);
            var monthFilter = new Month();
            var monthPage = new PageImpl<>(months);

            Specification<Month> mockSpecification = (Specification<Month>) (root, query, criteriaBuilder) -> null;
            doReturn(mockSpecification).when(monthSpecification).filter(monthFilter);
            doReturn(monthPage).when(monthRepository).findAll(mockSpecification, pageable);

            var result = monthService.findAll(monthFilter, pageable);

            assertNotNull(result);
            assertEquals(months, result.getContent());
            verify(monthSpecification, times(1)).filter(monthFilter);
            verify(monthRepository, times(1)).findAll(eq(mockSpecification), eq(pageable));
        }

        @Test
        void testfindAllSuccessIsEmpty() {
            var pageable = Mockito.mock(Pageable.class);
            var monthFilter = new Month();
            var monthPage = new PageImpl<>(new ArrayList<>());

            Specification<Month> mockSpecification = (Specification<Month>) (root, query, criteriaBuilder) -> null;
            doReturn(mockSpecification).when(monthSpecification).filter(monthFilter);
            doReturn(monthPage).when(monthRepository).findAll(mockSpecification, pageable);

            var result = monthService.findAll(monthFilter, pageable);

            assertNotNull(result);
            assertEquals(new ArrayList<>(), result.getContent());
            verify(monthSpecification, times(1)).filter(monthFilter);
            verify(monthRepository, times(1)).findAll(eq(mockSpecification), eq(pageable));
        }
    }

    @Nested
    class findById {

        @Test
        void findByIdSuccess() {
            var invoice = createListMonths(1L).getFirst();
            invoice.setId(monthId);
            doReturn(Optional.of(invoice))
                    .when(monthRepository)
                    .findById(monthId);

            var result = monthService.findById(monthId);

            assertEquals(invoice, result);
            verify(monthRepository, times(1)).findById(monthId);
        }

        @Test
        void findByIdThrow() {
            doReturn(Optional.empty())
                    .when(monthRepository)
                    .findById(monthId);

            assertThrows(FinancialBusinessException.class, () ->
                    monthService.findById(monthId)
            );

            verify(monthRepository, times(1)).findById(monthId);
        }
    }
}