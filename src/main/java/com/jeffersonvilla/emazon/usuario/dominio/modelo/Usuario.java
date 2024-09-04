package com.jeffersonvilla.emazon.usuario.dominio.modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Usuario {

    private final Long id;
    private final String nombre;
    private final String apellido;
    private final String documentoIdentidad;
    private final String celular;
    private final LocalDate fechaNacimiento;
    private final String correo;
    private final String clave;
    private final Rol rol;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public String getCelular() {
        return celular;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public String getClave() {
        return clave;
    }

    public Rol getRol() {
        return rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) &&
                Objects.equals(nombre, usuario.nombre) &&
                Objects.equals(apellido, usuario.apellido) &&
                Objects.equals(documentoIdentidad, usuario.documentoIdentidad) &&
                Objects.equals(celular, usuario.celular) &&
                Objects.equals(fechaNacimiento, usuario.fechaNacimiento) &&
                Objects.equals(correo, usuario.correo) &&
                Objects.equals(clave, usuario.clave) &&
                Objects.equals(rol, usuario.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                nombre,
                apellido,
                documentoIdentidad,
                celular,
                fechaNacimiento,
                correo,
                clave,
                rol);
    }

    private Usuario(UsuarioBuilder builder){
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.documentoIdentidad = builder.documentoIdentidad;
        this.celular = builder.celular;
        this.fechaNacimiento = builder.fechaNacimiento;
        this.correo = builder.correo;
        this.clave = builder.clave;
        this.rol = builder.rol;
    }

    public static class UsuarioBuilder{

        private Long id;
        private String nombre;
        private String apellido;
        private String documentoIdentidad;
        private String celular;
        private LocalDate fechaNacimiento;
        private String correo;
        private String clave;
        private Rol rol;

        public UsuarioBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UsuarioBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public UsuarioBuilder setApellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public UsuarioBuilder setDocumentoIdentidad(String documentoIdentidad) {
            this.documentoIdentidad = documentoIdentidad;
            return this;
        }

        public UsuarioBuilder setCelular(String celular) {
            this.celular = celular;
            return this;
        }

        public UsuarioBuilder setFechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }

        public UsuarioBuilder setCorreo(String correo) {
            this.correo = correo;
            return this;
        }

        public UsuarioBuilder setClave(String clave) {
            this.clave = clave;
            return this;
        }

        public UsuarioBuilder setRol(Rol rol) {
            this.rol = rol;
            return this;
        }

        public Usuario build(){
            return new Usuario(this);
        }
    }
}
