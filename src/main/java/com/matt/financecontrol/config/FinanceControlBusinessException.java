package com.matt.financecontrol.config;

public class FinanceControlBusinessException extends RuntimeException {

    public FinanceControlBusinessException() {
        super();
    }

    public FinanceControlBusinessException(String message) {
        super(message);
    }

    public FinanceControlBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
