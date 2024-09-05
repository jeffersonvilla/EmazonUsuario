package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.excepciones;

import com.jeffersonvilla.emazon.usuario.dominio.excepciones.BadRequestException;

public class CredencialesLoginInvalidasException extends BadRequestException {
  public CredencialesLoginInvalidasException(String message) {
    super(message);
  }
}
