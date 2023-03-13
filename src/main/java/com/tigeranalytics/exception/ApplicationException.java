package com.tigeranalytics.exception;

public class ApplicationException extends RuntimeException {

    ApplicationException(String message) {
        super(message);
    }
}
