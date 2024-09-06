package com.jeffersonvilla.emazon.usuario.dominio.excepciones;

public class CreacionUsuarioException extends BadRequestException {
    public CreacionUsuarioException(String message) {
        super(message);
    }
}
