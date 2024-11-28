package com.matt.financial.service;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Invoice;
import com.matt.financial.model.entity.Month;
import com.matt.financial.model.repository.InvoiceRepository;
import com.matt.financial.model.service.InvoiceService;
import com.matt.financial.model.specification.InvoiceSpecification;
import com.matt.financial.strategy.Validation;
import com.matt.financial.strategy.factory.InvoiceFactory;
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

import static com.matt.financial.model.enumerations.Operation.CREATE;
import static com.matt.financial.model.enumerations.Operation.UPDATE;
import static java.util.Optional.ofNullable;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    private final static UUID invoiceId = UUID.randomUUID();

    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private InvoiceSpecification invoiceSpecification;
    @Mock
    private InvoiceFactory invoiceFactory;
    @Mock
    private Validation<Invoice> validation;

    @InjectMocks
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class findAllInvoice {

        @Test
        void findAllSuccess() {
            var invoices = createListInvoices(5L);
            var pageable = Mockito.mock(Pageable.class);
            var invoiceFilter = new Invoice();
            var invoicesPage = new PageImpl<>(invoices);

            Specification<Invoice> mockSpecification = (Specification<Invoice>) (root, query, criteriaBuilder) -> null;
            doReturn(mockSpecification).when(invoiceSpecification).filter(invoiceFilter);
            doReturn(invoicesPage).when(invoiceRepository).findAll(eq(mockSpecification), eq(pageable));

            var result = invoiceService.findAll(invoiceFilter, pageable);

            assertNotNull(result);
            assertEquals(invoices, result.getContent());
            verify(invoiceSpecification, times(1)).filter(invoiceFilter);
            verify(invoiceRepository, times(1)).findAll(eq(mockSpecification), eq(pageable));
        }

        @Test
        void findAllSuccessIsEmpty() {
            var pageable = Mockito.mock(Pageable.class);
            var invoiceFilter = new Invoice();
            var invoicesPage = new PageImpl<>(new ArrayList<>());

            Specification<Invoice> mockSpecification = (Specification<Invoice>) (root, query, criteriaBuilder) -> null;
            doReturn(mockSpecification).when(invoiceSpecification).filter(invoiceFilter);
            doReturn(invoicesPage).when(invoiceRepository).findAll(eq(mockSpecification), eq(pageable));

            var result = invoiceService.findAll(invoiceFilter, pageable);

            assertNotNull(result);
            assertEquals(new ArrayList<>(), result.getContent());
            verify(invoiceSpecification, times(1)).filter(invoiceFilter);
            verify(invoiceRepository, times(1)).findAll(eq(mockSpecification), eq(pageable));
        }
    }

    @Nested
    class findById {

        @Test
        void findByIdSuccess() {
            var invoice = createListInvoices(1L).getFirst();
            invoice.setId(invoiceId);

            doReturn(Optional.of(invoice)).when(invoiceRepository).findById(eq(invoiceId));

            var result = invoiceService.findById(invoiceId);

            assertNotNull(result);
            assertEquals(invoice, result);
            verify(invoiceRepository, times(1)).findById(eq(invoiceId));
        }

        @Test
        void findByIdThrows() {
            doReturn(Optional.empty())
                    .when(invoiceRepository)
                    .findById(eq(invoiceId));

            assertThrows(FinancialBusinessException.class,
                    () -> invoiceService.findById(invoiceId)
            );

            verify(invoiceRepository, times(1)).findById(eq(invoiceId));

        }

    }

    @Nested
    class findByNameAndMonth {

        @Test
        void findByNameAndMonthSuccess() {
            var invoice = createListInvoices(1L).getFirst();
            var month = new Month();
            month.setId(UUID.randomUUID());
            invoice.setId(invoiceId);

            doReturn(Optional.of(invoice))
                    .when(invoiceRepository)
                    .findByNameAndMonth_Id(invoice.getName(), month.getId());

            var result = invoiceService.findByNameAndMonth(invoice.getName(), month);

            assertNotNull(result);
            assertEquals(invoice, result);
            verify(invoiceRepository, times(1)).findByNameAndMonth_Id(eq("Test0"), eq(month.getId()));
        }

        @Test
        void findByNameAndMonthThrows() {
            var invoice = createListInvoices(1L).getFirst();
            var month = new Month();
            month.setId(UUID.randomUUID());
            invoice.setId(invoiceId);

            doReturn(Optional.empty())
                    .when(invoiceRepository)
                    .findByNameAndMonth_Id(invoice.getName(), month.getId());

            assertThrows(FinancialBusinessException.class,
                    () -> invoiceService.findByNameAndMonth(invoice.getName(), month)
            );

            verify(invoiceRepository, times(1)).findByNameAndMonth_Id(eq(invoice.getName()), eq(month.getId()));
        }
    }

    @Nested
    class createInvoice {

        @Test
        void testCreateInvoice() {
            var invoce = createListInvoices(1L).getFirst();
            invoce.setId(invoiceId);

            when(invoiceFactory.getValidation(CREATE)).thenReturn(validation);
            doNothing().when(validation).execute(invoce);
            doReturn(invoce).when(invoiceRepository).save(invoce);

            var result = invoiceService.create(invoce);

            verify(invoiceFactory).getValidation(CREATE);
            verify(validation).execute(invoce);
            verify(invoiceRepository).save(invoce);
            assertEquals(invoce, result);
        }
    }

    @Nested
    class updateInvoice {

        @Test
        void testUpdateInvoice() {
            var invoice = createListInvoices(1L).getFirst();
            invoice.setId(invoiceId);
            invoice.setName("oldName");

            doReturn(Optional.of(invoice)).when(invoiceRepository).findById(invoiceId);

            invoice.setName("newName");

            when(invoiceFactory.getValidation(UPDATE)).thenReturn(validation);
            doNothing().when(validation).execute(invoice);
            doReturn(invoice).when(invoiceRepository).save(invoice);

            var result = invoiceService.update(invoice);

            verify(invoiceFactory).getValidation(UPDATE);
            verify(validation).execute(invoice);
            verify(invoiceRepository).save(invoice);
            assertEquals(invoice, result);
        }
    }

    @Nested
    class activateOrDeactivate {

        @Test
        void testActivateOrDeactivateInvoice() {
            var invoice = createListInvoices(1L).getFirst();
            invoice.setId(invoiceId);

            doReturn(Optional.of(invoice)).when(invoiceRepository).findById(invoiceId);
            doReturn(invoice).when(invoiceRepository).save(invoice);

            var result = invoiceService.activateOrDeactivate(invoice);

            assertFalse(result);
            verify(invoiceRepository).save(invoice);
        }
    }

    private List<Invoice> createListInvoices(Long amount) {
        long validatedAmount = ofNullable(amount).orElse(1L);
        List<Invoice> list = new ArrayList<>();

        for (int i = 0; i < validatedAmount; i++) {
            var invoice = new Invoice();
            invoice.setId(UUID.randomUUID());
            invoice.setName("Test" + i);
            invoice.setValue(new BigDecimal(i));
            invoice.setActive(true);

            list.add(invoice);
        }

        return list;
    }
}