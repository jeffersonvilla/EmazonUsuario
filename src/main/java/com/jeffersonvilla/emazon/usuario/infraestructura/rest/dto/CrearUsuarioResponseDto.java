package com.jeffersonvilla.emazon.usuario.infraestructura.rest.dto;

import java.time.LocalDate;

public record CrearUsuarioResponseDto(
        Long id,
        String nombre,
        String apellido,
        String documentoIdentidad,
        String celular,
        LocalDate fechaNacimiento,
        String correo
) {
}
