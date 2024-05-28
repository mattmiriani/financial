package com.matt.financial.strategy.factory;

import com.matt.financial.model.entity.Invoice;
import com.matt.financial.model.enumerations.Operation;
import com.matt.financial.strategy.Validation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;

@Component
public class InvoiceFactory {

    private final Map<Operation, Validation<Invoice>> validations = new HashMap<>();

    public InvoiceFactory(Set<Validation<Invoice>> validations) {
        validations.forEach(validation -> this.validations.put(validation.getOperation(), validation));
    }

    public Validation<Invoice> getValidation(Operation operation) {
        var validation = validations.get(operation);

        if (isNull(validation)) {
            throw new IllegalArgumentException("Operation not supported");
        }

        return validation;
    }
}