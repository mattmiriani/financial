package com.matt.financial.application.graphqlcontroller;

import com.matt.financial.application.records.PageableRecord;
import com.matt.financial.model.entity.Invoice;
import com.matt.financial.model.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class InvoiceGraphQLController {

    private final InvoiceService invoiceService;

    @QueryMapping
    public Page<Invoice> findAllInvoice(@Argument Invoice invoice,
                                        @Argument PageableRecord pageableRecord) {
        return invoiceService.findAll(invoice, pageableRecord.getPageable());
    }

    @QueryMapping
    public Invoice findInvoiceById(@Argument UUID invoiceId) {
        return invoiceService.findById(invoiceId);
    }

    @MutationMapping
    public Invoice createInvoice(@Argument Invoice invoice) {
        return invoiceService.create(invoice);
    }

    @MutationMapping
    public Invoice updateInvoice(@Argument Invoice invoice) {
        return invoiceService.update(invoice);
    }

    @MutationMapping
    public Invoice activateOrDeactivateInvoice(@Argument Invoice invoice) {
        return invoiceService.activateOrDeactivate(invoice);
    }
}
