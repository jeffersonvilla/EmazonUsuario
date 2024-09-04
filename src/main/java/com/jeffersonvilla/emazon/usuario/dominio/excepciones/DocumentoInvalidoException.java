package com.jeffersonvilla.emazon.usuario.dominio.excepciones;

public class DocumentoInvalidoException extends RuntimeException {
    public DocumentoInvalidoException(String message) {
        super(message);
    }
}
