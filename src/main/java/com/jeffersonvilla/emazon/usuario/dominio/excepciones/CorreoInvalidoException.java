package com.jeffersonvilla.emazon.usuario.dominio.excepciones;

public class CorreoInvalidoException extends RuntimeException {
    public CorreoInvalidoException(String message) {
        super(message);
    }
}
