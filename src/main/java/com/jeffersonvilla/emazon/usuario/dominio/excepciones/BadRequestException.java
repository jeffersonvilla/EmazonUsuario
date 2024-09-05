package com.jeffersonvilla.emazon.usuario.dominio.excepciones;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
