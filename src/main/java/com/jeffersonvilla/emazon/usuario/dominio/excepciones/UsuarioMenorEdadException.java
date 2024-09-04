package com.jeffersonvilla.emazon.usuario.dominio.excepciones;

public class UsuarioMenorEdadException extends RuntimeException {
    public UsuarioMenorEdadException(String message) {
        super(message);
    }
}
