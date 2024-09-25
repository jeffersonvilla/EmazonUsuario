package com.jeffersonvilla.emazon.usuario.dominio.api.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.api.IRolServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.api.IUsuarioServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CelularInvalidoException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CorreoInvalidoException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CreacionUsuarioException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.DocumentoInvalidoException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.UsuarioMenorEdadException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.UsuarioNoEncontradoException;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IEncriptadorClavePort;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IUsuarioPersistenciaPort;
import com.jeffersonvilla.emazon.usuario.dominio.util.UsuarioFactory;

import java.util.Optional;

import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.ROL_AUX_BODEGA;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.ROL_CLIENTE;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CELULAR_INVALIDO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CORREO_EN_USO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CORREO_INVALIDO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.DOCUMENTO_INVALIDO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.USUARIO_MENOR_EDAD;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.USUARIO_NO_ENCONTRADO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.ValidacionUsuario.validarAtributosNoNulos;
import static com.jeffersonvilla.emazon.usuario.dominio.util.ValidacionUsuario.validarCelular;
import static com.jeffersonvilla.emazon.usuario.dominio.util.ValidacionUsuario.validarCorreo;
import static com.jeffersonvilla.emazon.usuario.dominio.util.ValidacionUsuario.validarDocumentoIdentidad;
import static com.jeffersonvilla.emazon.usuario.dominio.util.ValidacionUsuario.validarMayoriaEdad;

public class UsuarioCasoUso implements IUsuarioServicePort {

    private final IUsuarioPersistenciaPort persistenciaUsuario;
    private final IEncriptadorClavePort encriptadorClave;
    private final IRolServicePort rolApi;

    public UsuarioCasoUso(
            IUsuarioPersistenciaPort persistenciaUsuario,
            IEncriptadorClavePort encriptadorClave,
            IRolServicePort rolApi
    ) {

        this.persistenciaUsuario = persistenciaUsuario;
        this.encriptadorClave = encriptadorClave;
        this.rolApi = rolApi;
    }

    @Override
    public Usuario crearAuxBodega(Usuario usuario) {
        return crearUsuario(usuario, ROL_AUX_BODEGA);
    }

    @Override
    public Usuario crearCliente(Usuario usuario) {
        return crearUsuario(usuario, ROL_CLIENTE);
    }

    private Usuario crearUsuario(Usuario usuario, String rol){

        procesarValidaciones(usuario);
        Usuario usuarioConRol = agregarRolAlUsuario(usuario, rol);
        Usuario usuarioConClaveCifrada = UsuarioFactory
                .crearUsuarioConClaveCifrada(usuarioConRol, encriptadorClave.encriptarClave(usuario.getClave()));

        return persistenciaUsuario.crearUsuario(usuarioConClaveCifrada);
    }

    private void procesarValidaciones(Usuario usuario) {
        validarAtributosNoNulos(usuario);
        validarCorreoDisponible(usuario);
        validarReglasNegocio(usuario);
    }

    private static void validarReglasNegocio(Usuario usuario) {
        if(!validarCorreo(usuario.getCorreo()))
            throw new CorreoInvalidoException(CORREO_INVALIDO);

        if(!validarCelular(usuario.getCelular()))
            throw new CelularInvalidoException(CELULAR_INVALIDO);

        if(!validarDocumentoIdentidad(usuario.getDocumentoIdentidad()))
            throw new DocumentoInvalidoException(DOCUMENTO_INVALIDO);

        if(!validarMayoriaEdad(usuario.getFechaNacimiento()))
            throw new UsuarioMenorEdadException(USUARIO_MENOR_EDAD);
    }

    private void validarCorreoDisponible(Usuario usuario) {
        if(persistenciaUsuario.obtenerUsuarioPorCorreo(usuario.getCorreo()).isPresent())
            throw new CreacionUsuarioException(CORREO_EN_USO);
    }

    private Usuario agregarRolAlUsuario(Usuario usuario, String rol) {
        Rol rolUsuario = rolApi.obtenerRolPorNombre(rol);
        return UsuarioFactory.crearUsuarioConRol(usuario, rolUsuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCorreo(String correo) {

        Optional<Usuario> usuarioEncontrado = persistenciaUsuario
                .obtenerUsuarioPorCorreo(correo);

        if(usuarioEncontrado.isEmpty())
            throw new UsuarioNoEncontradoException(USUARIO_NO_ENCONTRADO);

        return usuarioEncontrado.get();
    }
}
