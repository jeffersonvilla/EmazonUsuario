package com.jeffersonvilla.emazon.usuario.infraestructura.rest.excepciones;

import java.util.Map;

public record RespuestaConVariosErrores(String status, Map<String, String> mensajes) {
}
