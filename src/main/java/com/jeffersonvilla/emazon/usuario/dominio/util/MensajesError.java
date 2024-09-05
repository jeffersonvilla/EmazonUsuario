package com.jeffersonvilla.emazon.usuario.dominio.util;

import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.ROL_AUX_BODEGA;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_CELULAR;

public class MensajesError {

    private MensajesError(){
        throw new IllegalStateException("Clase utilitaria");
    }

    public static final String CORREO_INVALIDO = "Correo electrónico con formato invalido";

    public static final String CELULAR_INVALIDO = "Celular invalido. Debe tener entre 10 y "
            + TAMANO_MAXIMO_CELULAR +" caracteres numéricos. Contando con el signo '+' inicial";

    public static final String DOCUMENTO_INVALIDO = "El documento solo puede tener "
            + "caracteres numéricos";

    public static final String USUARIO_MENOR_EDAD = "El usuario debe ser mayor de edad";

    public static final String AUX_BODEGA_ROL_INCORRECTO = "El rol debe ser: "+ ROL_AUX_BODEGA;

    public static final String NOMBRE_NULO = "El usuario debe tener nombre";

    public static final String APELLIDO_NULO = "El usuario debe tener apellido";

    public static final String DOCUMENTO_NULO = "El usuario debe tener un documento de identidad";

    public static final String CELULAR_NULO = "El usuario debe tener celular";

    public static final String FECHA_NACIMIENTO_NULO = "El usuario debe tener fecha de nacimiento";

    public static final String CORREO_NULO = "El usuario debe tener correo";

    public static final String CLAVE_NULO = "El usuario debe tener clave";

    public static final String ROL_NO_ENCONTRADO = "El rol que busca no existe";

    public static final String USUARIO_NO_ENCONTRADO = "El usuario no existe";

    public static final String CORREO_EN_USO = "El correo ya está en uso";
}
