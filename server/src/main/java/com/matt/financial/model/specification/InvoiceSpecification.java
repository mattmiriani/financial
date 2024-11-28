package com.matt.financial.model.specification;

import com.matt.financial.model.entity.Invoice;
import com.matt.financial.model.entity.Month;
import com.matt.financial.model.entity.Subject;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class InvoiceSpecification implements SpecificationDefault<Invoice> {

    private Specification<Invoice> subject(Subject subject) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("subject").get("id"), subject.getId());
    }

    private Specification<Invoice> month(Month month) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("month").get("id"), month.getId());
    }

    public Specification<Invoice> filter(Invoice invoice) {
        var builder = this.builder();
        var invoiceOptional = ofNullable(invoice);

        invoiceOptional.map(Invoice::getName).map(this::name).ifPresent(builder::and);
        invoiceOptional.map(Invoice::getActive).map(this::isActive).ifPresent(builder::and);
        invoiceOptional.map(Invoice::getSubject).map(this::subject).ifPresent(builder::and);
        invoiceOptional.map(Invoice::getMonth).map(this::month).ifPresent(builder::and);

        return builder.build();
    }
}
