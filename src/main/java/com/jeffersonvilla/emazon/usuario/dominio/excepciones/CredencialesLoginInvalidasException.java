package com.jeffersonvilla.emazon.usuario.dominio.excepciones;

public class CredencialesLoginInvalidasException extends BadRequestException {
  public CredencialesLoginInvalidasException(String message) {
    super(message);
  }
}
