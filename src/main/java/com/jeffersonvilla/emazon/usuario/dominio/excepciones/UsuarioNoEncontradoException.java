package com.jeffersonvilla.emazon.usuario.dominio.excepciones;

public class UsuarioNoEncontradoException extends BadRequestException {
    public UsuarioNoEncontradoException(String message) {
        super(message);
    }
}
