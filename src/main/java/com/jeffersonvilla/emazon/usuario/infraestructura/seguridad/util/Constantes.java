package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.util;

public class Constantes {

    private Constantes(){
        throw new IllegalStateException("Clase utilitaria");
    }

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final Integer TAMANO_HEADER = 7;

    public static final String JWT_TOKEN_EXPIRADO = "El token jwt ha expirado";
    public static final String JWT_TOKEN_NO_VALIDO = "El token jwt no es válido";

    public static final long TIEMPO_EXPIRACION = 86400000; // 1 día en milisegundos

    public static final String ID_USUARIO_CLAIM = "id_usuario";
    public static final String ROL_USUARIO_CLAIM = "rol";
}
