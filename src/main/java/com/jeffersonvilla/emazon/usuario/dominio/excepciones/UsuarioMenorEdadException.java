package com.jeffersonvilla.emazon.usuario.dominio.excepciones;

public class UsuarioMenorEdadException extends BadRequestException {
    public UsuarioMenorEdadException(String message) {
        super(message);
    }
}
