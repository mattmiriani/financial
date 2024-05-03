package com.matt.financial.config;

public class FinancialBusinessException extends RuntimeException {

    public FinancialBusinessException() {
        super();
    }

    public FinancialBusinessException(String message) {
        super(message);
    }

    public FinancialBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
