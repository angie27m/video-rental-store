package com.casumo.videorental.exceptions;

public class InvalidFilmTypeException extends RuntimeException {
    public InvalidFilmTypeException(String message) {
        super(message);
    }
}
