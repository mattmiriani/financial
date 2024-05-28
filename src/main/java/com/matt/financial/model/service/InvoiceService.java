package com.matt.financial.model.service;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Invoice;
import com.matt.financial.model.entity.Month;
import com.matt.financial.model.repository.InvoiceRepository;
import com.matt.financial.model.specification.InvoiceSpecification;
import com.matt.financial.strategy.factory.InvoiceFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.matt.financial.model.enumerations.Operation.CREATE;
import static com.matt.financial.model.enumerations.Operation.UPDATE;

@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceSpecification invoiceSpecification;
    private final InvoiceFactory invoiceFactory;

    @Transactional(readOnly = true)
    public Page<Invoice> findAll(Invoice invoice, Pageable pageable) {
        return invoiceRepository.findAll(invoiceSpecification.filter(invoice), pageable);
    }

    @Transactional(readOnly = true)
    public Invoice findById(UUID invoiceId) {
        return invoiceRepository.findById(invoiceId).orElseThrow(
                () -> new FinancialBusinessException("Invoice not found")
        );
    }

    @Transactional(readOnly = true)
    public Invoice findByNameAndMonth(String name, Month month) {
        return invoiceRepository.findByNameAndMonth_Id(name, month.getId()).orElseThrow(
                () -> new FinancialBusinessException("Invoice not found")
        );
    }

    private Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Invoice create(Invoice invoice) {
        invoiceFactory.getValidation(CREATE).execute(invoice);

        return save(invoice);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Invoice update(Invoice invoice) {
        var invoiceToUpdate = this.findById(invoice.getId());

        invoiceFactory.getValidation(UPDATE).execute(invoice);

        invoiceToUpdate.mergeForUpdate(invoice);

        return this.save(invoice);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Invoice activateOrDeactivate(Invoice invoice) {
        var invoiceToUpdate = this.findById(invoice.getId());

        invoiceToUpdate.setActive(!invoiceToUpdate.getActive());

        return this.save(invoiceToUpdate);
    }
}
