package com.jeffersonvilla.emazon.usuario.dominio.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RolTest {

    private final Long id = 1L;
    private final String nombre = "rol";
    private final String descripcion = "descripción";

    private final Rol rol = new Rol(id, nombre, descripcion);

    @Test
    void getId() {
        assertEquals(id, rol.getId());
    }

    @Test
    void getNombre() {
        assertEquals(nombre, rol.getNombre());
    }

    @Test
    void getDescripcion() {
        assertEquals(descripcion, rol.getDescripcion());
    }
}