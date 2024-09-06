package com.jeffersonvilla.emazon.usuario.dominio.excepciones;

public class DocumentoInvalidoException extends BadRequestException {
    public DocumentoInvalidoException(String message) {
        super(message);
    }
}
