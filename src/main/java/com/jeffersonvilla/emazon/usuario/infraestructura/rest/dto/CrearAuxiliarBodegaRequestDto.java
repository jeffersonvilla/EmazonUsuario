package com.jeffersonvilla.emazon.usuario.infraestructura.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_APELLIDO_USUARIO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_CELULAR;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_CLAVE_USUARIO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_CORREO_USUARIO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_DOCUMENTO_USUARIO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_USUARIO;

public record CrearAuxiliarBodegaRequestDto(
        @NotBlank @Size(max = TAMANO_MAXIMO_NOMBRE_USUARIO) String nombre,
        @NotBlank @Size(max = TAMANO_MAXIMO_APELLIDO_USUARIO) String apellido,
        @NotBlank @Size(max = TAMANO_MAXIMO_DOCUMENTO_USUARIO) String documentoIdentidad,
        @NotBlank @Size(max = TAMANO_MAXIMO_CELULAR) String celular,
        @NotNull LocalDate fechaNacimiento,
        @NotBlank @Email @Size(max = TAMANO_MAXIMO_CORREO_USUARIO) String correo,
        @NotBlank @Size(max = TAMANO_MAXIMO_CLAVE_USUARIO) String clave
) {
}
