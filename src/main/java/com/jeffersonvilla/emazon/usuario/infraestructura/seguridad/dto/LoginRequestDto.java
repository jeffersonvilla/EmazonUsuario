package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank String correo,
        @NotBlank String clave
) {
}
