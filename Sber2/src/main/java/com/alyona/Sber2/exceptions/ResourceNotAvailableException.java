package com.alyona.Sber2.exceptions;

public class ResourceNotAvailableException extends RuntimeException {
    public ResourceNotAvailableException() {
        super("Resource is not available during the specified period");
    }
}

