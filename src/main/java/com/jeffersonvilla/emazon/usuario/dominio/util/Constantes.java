package com.jeffersonvilla.emazon.usuario.dominio.util;

public class Constantes {

    private Constantes(){
        throw new IllegalStateException("Clase utilitaria");
    }

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

}
