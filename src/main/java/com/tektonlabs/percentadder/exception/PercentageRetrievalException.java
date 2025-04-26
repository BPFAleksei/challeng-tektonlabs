package com.tektonlabs.percentadder.exception;

public class PercentageRetrievalException extends RuntimeException {
    public PercentageRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

    public PercentageRetrievalException(String message) {
        super(message);
    }
}
