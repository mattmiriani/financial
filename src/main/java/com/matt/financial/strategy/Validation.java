package com.matt.financial.strategy;

import com.matt.financial.model.enumerations.Operation;

public interface Validation<T> {
    void execute(T entity);
    Operation getOperation();
}
