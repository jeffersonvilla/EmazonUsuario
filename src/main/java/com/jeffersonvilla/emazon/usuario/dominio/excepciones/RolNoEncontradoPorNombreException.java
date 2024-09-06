package com.jeffersonvilla.emazon.usuario.dominio.excepciones;

public class RolNoEncontradoPorNombreException extends RuntimeException {
    public RolNoEncontradoPorNombreException(String message) {
        super(message);
    }
}
