package com.jeffersonvilla.emazon.usuario.dominio.excepciones;

public class CelularInvalidoException extends BadRequestException {
  public CelularInvalidoException(String message) {
    super(message);
  }
}
