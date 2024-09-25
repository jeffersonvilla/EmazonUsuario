package com.jeffersonvilla.emazon.usuario.dominio.util;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;

import java.time.LocalDate;

import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.APELLIDO_POR_DEFECTO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.CELULAR_POR_DEFECTO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.CLAVE_POR_DEFECTO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.CORREO_POR_DEFECTO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.DOCUMENTO_IDENTIDAD_POR_DEFECTO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.FECHA_NACIMIENTO_POR_DEFECTO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.ID_POR_DEFECTO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.NOMBRE_POR_DEFECTO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.ROL_POR_DEFECTO;

public class UsuarioFactory {

    private UsuarioFactory(){
        throw new IllegalStateException("Clave de tipo Factory");
    }

    public static Usuario crearUsuarioPorDefecto() {
        return new Usuario.UsuarioBuilder()
                .setId(ID_POR_DEFECTO)
                .setNombre(NOMBRE_POR_DEFECTO)
                .setApellido(APELLIDO_POR_DEFECTO)
                .setDocumentoIdentidad(DOCUMENTO_IDENTIDAD_POR_DEFECTO)
                .setCelular(CELULAR_POR_DEFECTO)
                .setFechaNacimiento(FECHA_NACIMIENTO_POR_DEFECTO)
                .setCorreo(CORREO_POR_DEFECTO)
                .setClave(CLAVE_POR_DEFECTO)
                .setRol(ROL_POR_DEFECTO)
                .build();
    }

    public static Usuario crearUsuarioConNombre(String nombre) {
        return new Usuario.UsuarioBuilder()
                .setId(ID_POR_DEFECTO)
                .setNombre(nombre)
                .setApellido(APELLIDO_POR_DEFECTO)
                .setDocumentoIdentidad(DOCUMENTO_IDENTIDAD_POR_DEFECTO)
                .setCelular(CELULAR_POR_DEFECTO)
                .setFechaNacimiento(FECHA_NACIMIENTO_POR_DEFECTO)
                .setCorreo(CORREO_POR_DEFECTO)
                .setClave(CLAVE_POR_DEFECTO)
                .setRol(ROL_POR_DEFECTO)
                .build();
    }

    public static Usuario crearUsuarioConApellido(String apellido) {
        return new Usuario.UsuarioBuilder()
                .setId(ID_POR_DEFECTO)
                .setNombre(NOMBRE_POR_DEFECTO)
                .setApellido(apellido)
                .setDocumentoIdentidad(DOCUMENTO_IDENTIDAD_POR_DEFECTO)
                .setCelular(CELULAR_POR_DEFECTO)
                .setFechaNacimiento(FECHA_NACIMIENTO_POR_DEFECTO)
                .setCorreo(CORREO_POR_DEFECTO)
                .setClave(CLAVE_POR_DEFECTO)
                .setRol(ROL_POR_DEFECTO)
                .build();
    }

    public static Usuario crearUsuarioConCorreo(String correo) {
        return new Usuario.UsuarioBuilder()
                .setId(ID_POR_DEFECTO)
                .setNombre(NOMBRE_POR_DEFECTO)
                .setApellido(APELLIDO_POR_DEFECTO)
                .setDocumentoIdentidad(DOCUMENTO_IDENTIDAD_POR_DEFECTO)
                .setCelular(CELULAR_POR_DEFECTO)
                .setFechaNacimiento(FECHA_NACIMIENTO_POR_DEFECTO)
                .setCorreo(correo)
                .setClave(CLAVE_POR_DEFECTO)
                .setRol(ROL_POR_DEFECTO)
                .build();
    }

    public static Usuario crearUsuarioConCelular(String celular) {
        return new Usuario.UsuarioBuilder()
                .setId(ID_POR_DEFECTO)
                .setNombre(NOMBRE_POR_DEFECTO)
                .setApellido(APELLIDO_POR_DEFECTO)
                .setDocumentoIdentidad(DOCUMENTO_IDENTIDAD_POR_DEFECTO)
                .setCelular(celular)
                .setFechaNacimiento(FECHA_NACIMIENTO_POR_DEFECTO)
                .setCorreo(CORREO_POR_DEFECTO)
                .setClave(CLAVE_POR_DEFECTO)
                .setRol(ROL_POR_DEFECTO)
                .build();
    }

    public static Usuario crearUsuarioConDocumentoIdentidad(String documento) {
        return new Usuario.UsuarioBuilder()
                .setId(ID_POR_DEFECTO)
                .setNombre(NOMBRE_POR_DEFECTO)
                .setApellido(APELLIDO_POR_DEFECTO)
                .setDocumentoIdentidad(documento)
                .setCelular(CELULAR_POR_DEFECTO)
                .setFechaNacimiento(FECHA_NACIMIENTO_POR_DEFECTO)
                .setCorreo(CORREO_POR_DEFECTO)
                .setClave(CLAVE_POR_DEFECTO)
                .setRol(ROL_POR_DEFECTO)
                .build();
    }

    public static Usuario crearUsuarioConFechaNacimiento(LocalDate fechaNacimiento) {
        return new Usuario.UsuarioBuilder()
                .setId(ID_POR_DEFECTO)
                .setNombre(NOMBRE_POR_DEFECTO)
                .setApellido(APELLIDO_POR_DEFECTO)
                .setDocumentoIdentidad(DOCUMENTO_IDENTIDAD_POR_DEFECTO)
                .setCelular(CELULAR_POR_DEFECTO)
                .setFechaNacimiento(fechaNacimiento)
                .setCorreo(CORREO_POR_DEFECTO)
                .setClave(CLAVE_POR_DEFECTO)
                .setRol(ROL_POR_DEFECTO)
                .build();
    }

    public static Usuario crearUsuarioConRol(Rol rol) {
        return new Usuario.UsuarioBuilder()
                .setId(ID_POR_DEFECTO)
                .setNombre(NOMBRE_POR_DEFECTO)
                .setApellido(APELLIDO_POR_DEFECTO)
                .setDocumentoIdentidad(DOCUMENTO_IDENTIDAD_POR_DEFECTO)
                .setCelular(CELULAR_POR_DEFECTO)
                .setFechaNacimiento(FECHA_NACIMIENTO_POR_DEFECTO)
                .setCorreo(CORREO_POR_DEFECTO)
                .setClave(CLAVE_POR_DEFECTO)
                .setRol(rol)
                .build();
    }

    public static Usuario crearUsuarioConClave(String clave) {
        return new Usuario.UsuarioBuilder()
                .setId(ID_POR_DEFECTO)
                .setNombre(NOMBRE_POR_DEFECTO)
                .setApellido(APELLIDO_POR_DEFECTO)
                .setDocumentoIdentidad(DOCUMENTO_IDENTIDAD_POR_DEFECTO)
                .setCelular(CELULAR_POR_DEFECTO)
                .setFechaNacimiento(FECHA_NACIMIENTO_POR_DEFECTO)
                .setCorreo(CORREO_POR_DEFECTO)
                .setClave(clave)
                .setRol(ROL_POR_DEFECTO)
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

    public static Usuario crearUsuarioConRol(Usuario usuario, Rol rol) {
        return new Usuario.UsuarioBuilder()
                .setId(usuario.getId())
                .setNombre(usuario.getNombre())
                .setApellido(usuario.getApellido())
                .setDocumentoIdentidad(usuario.getDocumentoIdentidad())
                .setCelular(usuario.getCelular())
                .setFechaNacimiento(usuario.getFechaNacimiento())
                .setCorreo(usuario.getCorreo())
                .setClave(usuario.getClave())
                .setRol(rol)
                .build();
    }
}

