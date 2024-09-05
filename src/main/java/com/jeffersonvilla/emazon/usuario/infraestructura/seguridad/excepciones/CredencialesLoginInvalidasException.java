package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.excepciones;

public class CredencialesLoginInvalidasException extends RuntimeException {
  public CredencialesLoginInvalidasException(String message) {
    super(message);
  }
}
