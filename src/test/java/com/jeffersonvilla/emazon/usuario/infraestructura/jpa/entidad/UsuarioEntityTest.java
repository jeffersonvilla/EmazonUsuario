package com.jeffersonvilla.emazon.usuario.infraestructura.jpa.entidad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioEntityTest {

    private UsuarioEntity usuarioEntity;

    private Long id = 1L;
    private String nombre = "nombre";
    private String apellido = "apellido";
    private String documento = "1234567897";
    private String celular = "+3005827412";
    private LocalDate fechaNacimiento = LocalDate.now();
    private String correo = "ejemplo@correo.com";
    private String clave = "clave";
    private Long idRol = 1L;
    private String nombreRol = "rol";
    private String descripcionRol = "descripción";

    @BeforeEach
    void setUp() {
        RolEntity rolEntity = new RolEntity(idRol, nombreRol, descripcionRol);

        usuarioEntity = new UsuarioEntity(
                id,
                nombre,
                apellido,
                documento,
                celular,
                fechaNacimiento,
                correo,
                clave,
                rolEntity);
    }

    @DisplayName("getAuthorities debe retornar lista de nombres de los roles " +
            "como SimpleGrantedAuthority")
    @Test
    void getAuthorities() {
        assertEquals(
                List.of(new SimpleGrantedAuthority(nombreRol))
                , usuarioEntity.getAuthorities()
        );
    }

    @DisplayName("getPassword debe retornar la clave")
    @Test
    void getPassword() {
        assertEquals(clave, usuarioEntity.getPassword());
    }

    @DisplayName("getUsername debe retornar el correo")
    @Test
    void getUsername() {
        assertEquals(correo, usuarioEntity.getUsername());
    }
}