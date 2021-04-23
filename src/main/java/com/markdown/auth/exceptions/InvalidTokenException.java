package com.markdown.auth.exceptions;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String invalid_token, RuntimeException e) {
    }
}
