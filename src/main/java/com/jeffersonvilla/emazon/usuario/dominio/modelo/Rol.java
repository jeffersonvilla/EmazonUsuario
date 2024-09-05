package com.jeffersonvilla.emazon.usuario.dominio.modelo;

public class Rol {

    private final Long id;
    private final String nombre;
    private final String descripcion;

    public Rol(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
