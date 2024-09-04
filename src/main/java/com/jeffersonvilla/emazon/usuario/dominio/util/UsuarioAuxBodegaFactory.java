package com.jeffersonvilla.emazon.usuario.dominio.util;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;

import java.time.LocalDate;

public class UsuarioAuxBodegaFactory {

    private static Long idPorDefecto = 1L;
    private static String nombrePorDefecto = "NombrePorDefecto";
    private static String apellidoPorDefecto = "ApellidoPorDefecto";
    private static String documentoIdentidadPorDefecto = "123456789";
    private static String celularPorDefecto = "+573011234567";
    private static LocalDate fechaNacimientoPorDefecto
            = LocalDate.of(1990, 1, 1);
    private static String correoPorDefecto = "usuario@ejemplo.com";
    private static String clavePorDefecto = "claveEnTextoPlano";
    private static Rol rolPorDefecto = new Rol(1L, "aux_bodega", "Rol de auxiliar de bodega");

    private UsuarioAuxBodegaFactory(){
        throw new IllegalStateException("Clave de tipo Factory");
    }

    public static Usuario crearUsuarioPorDefecto() {
        return new Usuario.UsuarioBuilder()
                .setId(idPorDefecto)
                .setNombre(nombrePorDefecto)
                .setApellido(apellidoPorDefecto)
                .setDocumentoIdentidad(documentoIdentidadPorDefecto)
                .setCelular(celularPorDefecto)
                .setFechaNacimiento(fechaNacimientoPorDefecto)
                .setCorreo(correoPorDefecto)
                .setClave(clavePorDefecto)
                .setRol(rolPorDefecto)
                .build();
    }

    public static Usuario crearUsuarioConNombre(String nombre) {
        return new Usuario.UsuarioBuilder()
                .setId(idPorDefecto)
                .setNombre(nombre)
                .setApellido(apellidoPorDefecto)
                .setDocumentoIdentidad(documentoIdentidadPorDefecto)
                .setCelular(celularPorDefecto)
                .setFechaNacimiento(fechaNacimientoPorDefecto)
                .setCorreo(correoPorDefecto)
                .setClave(clavePorDefecto)
                .setRol(rolPorDefecto)
                .build();
    }

    public static Usuario crearUsuarioConApellido(String apellido) {
        return new Usuario.UsuarioBuilder()
                .setId(idPorDefecto)
                .setNombre(nombrePorDefecto)
                .setApellido(apellido)
                .setDocumentoIdentidad(documentoIdentidadPorDefecto)
                .setCelular(celularPorDefecto)
                .setFechaNacimiento(fechaNacimientoPorDefecto)
                .setCorreo(correoPorDefecto)
                .setClave(clavePorDefecto)
                .setRol(rolPorDefecto)
                .build();
    }

    public static Usuario crearUsuarioConCorreo(String correo) {
        return new Usuario.UsuarioBuilder()
                .setId(idPorDefecto)
                .setNombre(nombrePorDefecto)
                .setApellido(apellidoPorDefecto)
                .setDocumentoIdentidad(documentoIdentidadPorDefecto)
                .setCelular(celularPorDefecto)
                .setFechaNacimiento(fechaNacimientoPorDefecto)
                .setCorreo(correo)
                .setClave(clavePorDefecto)
                .setRol(rolPorDefecto)
                .build();
    }

    public static Usuario crearUsuarioConCelular(String celular) {
        return new Usuario.UsuarioBuilder()
                .setId(idPorDefecto)
                .setNombre(nombrePorDefecto)
                .setApellido(apellidoPorDefecto)
                .setDocumentoIdentidad(documentoIdentidadPorDefecto)
                .setCelular(celular)
                .setFechaNacimiento(fechaNacimientoPorDefecto)
                .setCorreo(correoPorDefecto)
                .setClave(clavePorDefecto)
                .setRol(rolPorDefecto)
                .build();
    }

    public static Usuario crearUsuarioConDocumentoIdentidad(String documento) {
        return new Usuario.UsuarioBuilder()
                .setId(idPorDefecto)
                .setNombre(nombrePorDefecto)
                .setApellido(apellidoPorDefecto)
                .setDocumentoIdentidad(documento)
                .setCelular(celularPorDefecto)
                .setFechaNacimiento(fechaNacimientoPorDefecto)
                .setCorreo(correoPorDefecto)
                .setClave(clavePorDefecto)
                .setRol(rolPorDefecto)
                .build();
    }

    public static Usuario crearUsuarioConFechaNacimiento(LocalDate fechaNacimiento) {
        return new Usuario.UsuarioBuilder()
                .setId(idPorDefecto)
                .setNombre(nombrePorDefecto)
                .setApellido(apellidoPorDefecto)
                .setDocumentoIdentidad(documentoIdentidadPorDefecto)
                .setCelular(celularPorDefecto)
                .setFechaNacimiento(fechaNacimiento)
                .setCorreo(correoPorDefecto)
                .setClave(clavePorDefecto)
                .setRol(rolPorDefecto)
                .build();
    }

    public static Usuario crearUsuarioConRol(Rol rol) {
        return new Usuario.UsuarioBuilder()
                .setId(idPorDefecto)
                .setNombre(nombrePorDefecto)
                .setApellido(apellidoPorDefecto)
                .setDocumentoIdentidad(documentoIdentidadPorDefecto)
                .setCelular(celularPorDefecto)
                .setFechaNacimiento(fechaNacimientoPorDefecto)
                .setCorreo(correoPorDefecto)
                .setClave(clavePorDefecto)
                .setRol(rol)
                .build();
    }

    public static Usuario crearUsuarioConClave(String clave) {
        return new Usuario.UsuarioBuilder()
                .setId(idPorDefecto)
                .setNombre(nombrePorDefecto)
                .setApellido(apellidoPorDefecto)
                .setDocumentoIdentidad(documentoIdentidadPorDefecto)
                .setCelular(celularPorDefecto)
                .setFechaNacimiento(fechaNacimientoPorDefecto)
                .setCorreo(correoPorDefecto)
                .setClave(clave)
                .setRol(rolPorDefecto)
                .build();
    }

    public static Usuario crearUsuarioConClaveCifrada(Usuario usuario, String claveCifrada) {
        return new Usuario.UsuarioBuilder()
                .setId(usuario.getId())
                .setNombre(usuario.getNombre())
                .setApellido(usuario.getApellido())
                .setDocumentoIdentidad(usuario.getDocumentoIdentidad())
                .setCelular(usuario.getCelular())
                .setFechaNacimiento(usuario.getFechaNacimiento())
                .setCorreo(usuario.getCorreo())
                .setClave(claveCifrada)
                .setRol(usuario.getRol())
                .build();
    }
}

