package com.jeffersonvilla.emazon.usuario.dominio.excepciones;

public class CreacionUsuarioRolNoEsCorrectoException extends RuntimeException {
    public CreacionUsuarioRolNoEsCorrectoException(String message) {
        super(message);
    }
}
