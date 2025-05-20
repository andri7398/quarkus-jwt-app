package org.acme.util;

public class ErrorResponse {
    public String message;
    public int status;

    public ErrorResponse() {}

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
