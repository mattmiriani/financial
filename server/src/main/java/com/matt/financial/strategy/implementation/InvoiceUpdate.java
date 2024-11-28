package com.matt.financial.strategy.implementation;

import com.matt.financial.config.FinancialBusinessException;
import com.matt.financial.model.entity.Invoice;
import com.matt.financial.model.enumerations.Operation;
import com.matt.financial.model.service.InvoiceService;
import com.matt.financial.strategy.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvoiceUpdate implements Validation<Invoice> {

    private final InvoiceService invoiceService;

    @Override
    public void execute(Invoice invoice) {
        var invoiceToUpdate = invoiceService.findById(invoice.getId());

        if(!invoiceToUpdate.getActive()) {
            throw new FinancialBusinessException("Invoice is not active");
        }
    }

    @Override
    public Operation getOperation() {
        return Operation.UPDATE;
    }
}
