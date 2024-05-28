package com.matt.financial.strategy.implementation;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Invoice;
import com.matt.financial.model.enumerations.Operation;
import com.matt.financial.model.service.InvoiceService;
import com.matt.financial.strategy.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class InvoiceCreate implements Validation<Invoice> {

    private final InvoiceService invoiceService;

    @Override
    public void execute(Invoice invoice) {
        if (nonNull(this.invoiceService.findByNameAndMonth(invoice.getName(), invoice.getMonth()))) {
            throw new FinancialBusinessException("The invoice already exists for this month");
        }
    }

    @Override
    public Operation getOperation() {
        return Operation.CREATE;
    }
}
