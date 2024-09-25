package com.jeffersonvilla.emazon.usuario.dominio.util;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;

import java.time.LocalDate;

public class Constantes {

    private Constantes(){
        throw new IllegalStateException("Clase utilitaria");
    }

    public static final String ROL_CLIENTE = "cliente";

    public static final String ROL_ADMIN = "admin";

    public static final String ROL_AUX_BODEGA = "aux_bodega";

    public static final int TAMANO_MAXIMO_CELULAR = 13;

    public static final int MAYORIA_EDAD = 18;

    public static final int TAMANO_MAXIMO_NOMBRE_ROL = 45;

    public static final int TAMANO_MAXIMO_DESCRIPCION_ROL = 45;

    public static final int TAMANO_MAXIMO_NOMBRE_USUARIO = 45;

    public static final int TAMANO_MAXIMO_APELLIDO_USUARIO = 45;

    public static final int TAMANO_MAXIMO_DOCUMENTO_USUARIO = 15;

    public static final int TAMANO_MAXIMO_CELULAR_USUARIO = 13;

    public static final int TAMANO_MAXIMO_CORREO_USUARIO = 45;

    public static final int TAMANO_MAXIMO_CLAVE_USUARIO = 72;

    public static final Long ID_POR_DEFECTO = 1L;

    public static final String NOMBRE_POR_DEFECTO = "NombrePorDefecto";

    public static final String APELLIDO_POR_DEFECTO = "ApellidoPorDefecto";

    public static final String DOCUMENTO_IDENTIDAD_POR_DEFECTO = "123456789";

    public static final String CELULAR_POR_DEFECTO = "+573011234567";

    public static final LocalDate FECHA_NACIMIENTO_POR_DEFECTO = LocalDate.of(1990, 1, 1);

    public static final String CORREO_POR_DEFECTO = "usuario@ejemplo.com";

    public static final String CLAVE_POR_DEFECTO = "claveEnTextoPlano";

    public static final String DESCRIPCION_ROL_CLIENTE = "Rol de cliente";

    public static final Rol ROL_POR_DEFECTO = new Rol(ID_POR_DEFECTO, ROL_CLIENTE, DESCRIPCION_ROL_CLIENTE);

    public static final String RUTA_LOGIN = "/autenticacion/login";

    public static final String RUTA_CREAR_AUX_BODEGA = "/usuario/crear/auxiliar_bodega";

    public static final String RUTA_CREAR_CLIENTE = "/usuario/crear/cliente";
}
