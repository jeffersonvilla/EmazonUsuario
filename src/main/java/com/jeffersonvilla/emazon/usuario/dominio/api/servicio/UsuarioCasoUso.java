package com.jeffersonvilla.emazon.usuario.dominio.api.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.api.IUsuarioServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CelularInvalidoException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CorreoInvalidoException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CreacionAuxiliarBodegaRolNoEsCorrectoException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.DocumentoInvalidoException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.UsuarioMenorEdadException;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IEncriptadorClavePort;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IUsuarioPersistenciaPort;
import com.jeffersonvilla.emazon.usuario.dominio.util.UsuarioAuxBodegaFactory;

import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.AUX_BODEGA_ROL_INCORRECTO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CELULAR_INVALIDO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CORREO_INVALIDO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.DOCUMENTO_INVALIDO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.USUARIO_MENOR_EDAD;
import static com.jeffersonvilla.emazon.usuario.dominio.util.ValidacionUsuario.validarAtributosNoNulos;
import static com.jeffersonvilla.emazon.usuario.dominio.util.ValidacionUsuario.validarCelular;
import static com.jeffersonvilla.emazon.usuario.dominio.util.ValidacionUsuario.validarCorreo;
import static com.jeffersonvilla.emazon.usuario.dominio.util.ValidacionUsuario.validarDocumentoIdentidad;
import static com.jeffersonvilla.emazon.usuario.dominio.util.ValidacionUsuario.validarMayoriaEdad;
import static com.jeffersonvilla.emazon.usuario.dominio.util.ValidacionUsuario.validarRolAuxBodega;

public class UsuarioCasoUso implements IUsuarioServicePort {

    private final IUsuarioPersistenciaPort persistenciaUsuario;
    private final IEncriptadorClavePort encriptadorClave;

    public UsuarioCasoUso(
            IUsuarioPersistenciaPort persistenciaUsuario,
            IEncriptadorClavePort encriptadorClave
    ) {

        this.persistenciaUsuario = persistenciaUsuario;
        this.encriptadorClave = encriptadorClave;
    }

    @Override
    public Usuario crearAuxBodega(Usuario usuario) {

        validarAtributosNoNulos(usuario);

        if(!validarCorreo(usuario.getCorreo()))
            throw new CorreoInvalidoException(CORREO_INVALIDO);

        if(!validarCelular(usuario.getCelular()))
            throw new CelularInvalidoException(CELULAR_INVALIDO);

        if(!validarDocumentoIdentidad(usuario.getDocumentoIdentidad()))
            throw new DocumentoInvalidoException(DOCUMENTO_INVALIDO);

        if(!validarMayoriaEdad(usuario.getFechaNacimiento()))
            throw new UsuarioMenorEdadException(USUARIO_MENOR_EDAD);

        if(!validarRolAuxBodega(usuario.getRol()))
            throw new CreacionAuxiliarBodegaRolNoEsCorrectoException(
                    AUX_BODEGA_ROL_INCORRECTO);

        Usuario usuarioConClaveCifrada = UsuarioAuxBodegaFactory.crearUsuarioConClaveCifrada(
                    usuario, encriptadorClave.encriptarClave(usuario.getClave()));

        return persistenciaUsuario.crearUsuario(usuarioConClaveCifrada);
    }

}
