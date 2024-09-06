package com.jeffersonvilla.emazon.usuario.dominio.excepciones;

public class CorreoInvalidoException extends BadRequestException {
    public CorreoInvalidoException(String message) {
        super(message);
    }
}
