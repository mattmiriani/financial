package com.matt.financial.model.specification;

import com.matt.financial.model.entity.Invoice;
import com.matt.financial.model.entity.Month;
import com.matt.financial.model.entity.Workspace;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class MonthSpecification implements SpecificationDefault<Month> {

    private Specification<Month> workspace(Workspace workspace) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("workspace").get("id"), workspace.getId());
    }

    private Specification<Month> invoices(List<Invoice> invoices) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("id")).value(invoices.stream().map(Invoice::getId).toList());
    }

    public Specification<Month> filter(Month month) {
        var builder = this.builder();
        var monthOptional = ofNullable(month);

        monthOptional.map(Month::getName).map(this::name).ifPresent(builder::and);
        monthOptional.map(Month::getActive).map(this::isActive).ifPresent(builder::and);
        monthOptional.map(Month::getWorkspace).map(this::workspace).ifPresent(builder::and);
        monthOptional.map(Month::getInvoices).map(this::invoices).ifPresent(builder::and);

        return builder.build();
    }
}
