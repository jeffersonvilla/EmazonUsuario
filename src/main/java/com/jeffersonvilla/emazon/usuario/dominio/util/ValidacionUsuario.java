package com.jeffersonvilla.emazon.usuario.dominio.util;

import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CreacionUsuarioException;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.MAYORIA_EDAD;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.APELLIDO_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CELULAR_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CLAVE_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CORREO_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.DOCUMENTO_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.FECHA_NACIMIENTO_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.NOMBRE_NULO;

public class ValidacionUsuario {

    //Expresión Regular para validación de dirección de correo electrónico
    /*
     * Primera parte local (antes del '@'):
     *
     * [A-Z0-9]: La primera posición debe ser un carácter alfanumérico
     * (letras mayúsculas de la A a la Z y números del 0 al 9).
     *
     * [A-Z0-9._%+-]+: Después, puede haber uno o más caracteres que
     * pueden ser alfanuméricos, puntos (.), guiones bajos (_), signos
     * de porcentaje (%), signos más (+), o guiones (-).
     *
     * [A-Z0-9]: La última posición antes del '@' debe ser nuevamente
     * un carácter alfanumérico.
     *
     * Símbolo '@': Este simboliza la separación entre la parte local
     * (usuario) y el dominio del correo electrónico.
     *
     * Dominio (después del '@'):
     *
     * [A-Z0-9]: La primera posición del dominio debe ser un carácter
     * alfanumérico.
     *
     * [A-Z0-9.-]+: Luego, puede haber uno o más caracteres que pueden
     * ser alfanuméricos, puntos (.) o guiones (-).
     *
     * [A-Z0-9]: La última posición antes del punto (.) en el dominio
     * debe ser un carácter alfanumérico.
     *
     * TLD (Top-Level Domain):
     *
     * \\.[A-Z]{2,6}: Después del punto (.), el TLD debe contener de 2
     * a 6 caracteres alfabéticos (A-Z). Esto cubre la mayoría de los
     * dominios estándar (como .com, .net, .org, etc.).
     */
    private static final String CORREO_REGEX = "^[A-Z0-9][A-Z0-9._%+-]+[A-Z0-9]@[A-Z0-9][A-Z0-9.-]+[A-Z0-9]\\.[A-Z]{2,6}$";

    /*
     * Esta expresión regular valída números de teléfono en un formato específico:
     *
     * ^: Indica el inicio de la cadena.
     *
     * [+]?: Opcionalmente, puede comenzar con un signo más (+), que
     * indica que el número es internacional.
     *
     * \d{10,12}: Debe contener entre 10 y 12 dígitos (números del 0 al 9).
     *
     * $: Indica el final de la cadena.
     *
     * Esta regex valída números de teléfono que pueden tener un signo
     * más opcional al inicio, seguido de una secuencia de entre 10 y
     * 12 dígitos. Ejemplos válidos: "1234567890", "+12345678901".
     */

    private static final String CELULAR_REGEX = "^[+]?\\d{10,12}$";

    //Esta regex valida que el documento sea solo caracteres numéricos
    private static final String DOCUMENTO_REGEX = "^\\d+$";

    private ValidacionUsuario(){
        throw new IllegalStateException("Clase utilitaria");
    }

    /*
     * Validación: Utiliza la opción de insensibilidad a mayúsculas:Pattern.CASE_INSENSITIVE
     */
    public static boolean validarCorreo(String correo) {
        return Pattern.compile(CORREO_REGEX, Pattern.CASE_INSENSITIVE)
                .matcher(correo).matches();
    }

    public static boolean validarCelular(String celular) {
        return Pattern.compile(CELULAR_REGEX).matcher(celular).matches();
    }

    public static boolean validarDocumentoIdentidad(String documento){
        return Pattern.compile(DOCUMENTO_REGEX).matcher(documento).matches();
    }

    public static boolean validarMayoriaEdad(LocalDate fechaNacimiento){
        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(fechaNacimiento, fechaActual);
        return periodo.getYears() >= MAYORIA_EDAD;
    }

    public static void validarAtributosNoNulos(Usuario usuario){
        if(usuario.getNombre() == null) throw new CreacionUsuarioException(NOMBRE_NULO);
        if(usuario.getApellido() == null) throw new CreacionUsuarioException(APELLIDO_NULO);
        if(usuario.getDocumentoIdentidad() == null) throw new CreacionUsuarioException(DOCUMENTO_NULO);
        if(usuario.getCelular() == null) throw new CreacionUsuarioException(CELULAR_NULO);
        if(usuario.getFechaNacimiento() == null) throw new CreacionUsuarioException(FECHA_NACIMIENTO_NULO);
        if(usuario.getCorreo() == null) throw new CreacionUsuarioException(CORREO_NULO);
        if(usuario.getClave() == null) throw new CreacionUsuarioException(CLAVE_NULO);
    }
}
