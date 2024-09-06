package com.jeffersonvilla.emazon.usuario.dominio.modelo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioTest {

    private static final Long ID = 1L;
    private static final String NOMBRE = "nombre";
    private static final String APELLIDO = "apellido";
    private static final String DOCUMENTO = "1025452896";
    private static final String CELULAR = "+573005627453";
    private static final LocalDate FECHA_NACIMIENTO = LocalDate.now();
    private static final String CORREO = "ejemplo@correo.com";
    private static final String CLAVE = "contraseña";
    private static final Rol rol = new Rol(1L , "rol", "descripción");

    private static Usuario usuario;

    @BeforeAll
    static void setUp(){
        usuario = new Usuario.UsuarioBuilder()
                .setId(ID)
                .setNombre(NOMBRE)
                .setApellido(APELLIDO)
                .setDocumentoIdentidad(DOCUMENTO)
                .setCelular(CELULAR)
                .setFechaNacimiento(FECHA_NACIMIENTO)
                .setCorreo(CORREO)
                .setClave(CLAVE)
                .setRol(rol)
        .build();


    }

    @Test
    void getId() {
        assertEquals(ID, usuario.getId());
    }

    @Test
    void getNombre() {
        assertEquals(NOMBRE, usuario.getNombre());
    }

    @Test
    void getApellido() {
        assertEquals(APELLIDO, usuario.getApellido());
    }

    @Test
    void getDocumentoIdentidad() {
        assertEquals(DOCUMENTO, usuario.getDocumentoIdentidad());
    }

    @Test
    void getCelular() {
        assertEquals(CELULAR, usuario.getCelular());
    }

    @Test
    void getFechaNacimiento() {
        assertEquals(FECHA_NACIMIENTO, usuario.getFechaNacimiento());
    }

    @Test
    void getCorreo() {
        assertEquals(CORREO, usuario.getCorreo());
    }

    @Test
    void getClave() {
        assertEquals(CLAVE, usuario.getClave());
    }

    @Test
    void getRol() {
        assertEquals(rol, usuario.getRol());
    }

}