package com.matt.financial.strategy;

import com.matt.financial.model.entity.Subject;
import com.matt.financial.model.enumerations.Operation;

public interface Validation {
    void execute(Subject subject);
    Operation getOperation();
}
