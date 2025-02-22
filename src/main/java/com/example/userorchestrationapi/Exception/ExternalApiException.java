package com.example.userorchestrationapi.Exception;

public class ExternalApiException extends RuntimeException {
    public ExternalApiException(String message) {
        super(message );
    }
}
