package com.jeffersonvilla.emazon.usuario.dominio.api.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.api.IRolServicePort;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.DESCRIPCION_ROL_CLIENTE;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.ROL_AUX_BODEGA;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.ROL_CLIENTE;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.APELLIDO_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CELULAR_INVALIDO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CELULAR_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CLAVE_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CORREO_EN_USO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CORREO_INVALIDO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CORREO_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.DOCUMENTO_INVALIDO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.DOCUMENTO_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.FECHA_NACIMIENTO_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.NOMBRE_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.USUARIO_MENOR_EDAD;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.USUARIO_NO_ENCONTRADO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioCasoUsoTest {

    @InjectMocks
    private UsuarioCasoUso usuarioCasoUso;

    @Mock
    private IUsuarioPersistenciaPort usuarioPersistenciaPort;

    @Mock
    private IRolServicePort rolServicePort;

    @Mock
    private IEncriptadorClavePort encriptadorClave;

    @DisplayName("Usuario con correo invalido debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConCorreoInvalido(){
        Usuario usuario = UsuarioFactory.crearUsuarioConCorreo("correoinvalido");

        Exception exception = assertThrows(CorreoInvalidoException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(CORREO_INVALIDO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("Usuario con celular invalido debe lanzar excepción")
    @Test
    void testCrearAuxBodegaCelularInvalido(){
        Usuario usuario = UsuarioFactory.crearUsuarioConCelular("12345678901234abc");

        Exception exception = assertThrows(CelularInvalidoException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(CELULAR_INVALIDO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("Usuario con documento de identidad invalido debe lanzar excepción")
    @Test
    void testCrearAuxBodegaDocumentoIdentidadInvalido(){
        Usuario usuario = UsuarioFactory
                .crearUsuarioConDocumentoIdentidad("documentoNoValido");

        Exception exception = assertThrows(DocumentoInvalidoException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(DOCUMENTO_INVALIDO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("Usuario con menor de edad debe lanzar excepción")
    @Test
    void testCrearAuxBodegaUsuarioMenorDeEdad(){
        Usuario usuario = UsuarioFactory.crearUsuarioConFechaNacimiento(
                LocalDate.of(2023, 9, 5));

        Exception exception = assertThrows(UsuarioMenorEdadException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(USUARIO_MENOR_EDAD, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega con éxito debe retornar usuario creado")
    @Test
    void testCrearAuxBodegaClaveDebeEstarCifrada(){
        String claveCifrada = "claveCifrada";
        Usuario usuarioParaGuardar = UsuarioFactory.crearUsuarioPorDefecto();
        Usuario usuarioGuardado = UsuarioFactory.crearUsuarioConClave(claveCifrada);
        Rol rolAuxBodega = new Rol(1L, ROL_AUX_BODEGA, "descrpción");

        when(encriptadorClave.encriptarClave(anyString())).thenReturn(claveCifrada);
        when(usuarioPersistenciaPort.crearUsuario(any(Usuario.class))).
                thenReturn(usuarioGuardado);
        when(rolServicePort.obtenerRolPorNombre(ROL_AUX_BODEGA)).thenReturn(rolAuxBodega);

        Usuario usuarioResultante = usuarioCasoUso.crearAuxBodega(usuarioParaGuardar);

        assertEquals(usuarioGuardado, usuarioResultante);

        verify(encriptadorClave).encriptarClave(usuarioParaGuardar.getClave());
        verify(usuarioPersistenciaPort).crearUsuario(any(Usuario.class));
        verify(rolServicePort).obtenerRolPorNombre(anyString());
    }

    @DisplayName("creación auxiliar de bodega sin nombre debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConNombreNulo(){
        String nombre = null;
        Usuario usuario = UsuarioFactory.crearUsuarioConNombre(nombre);

        Exception exception = assertThrows(CreacionUsuarioException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(NOMBRE_NULO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega sin apellido debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConApellidoNulo(){
        String apellido = null;
        Usuario usuario = UsuarioFactory.crearUsuarioConApellido(apellido);

        Exception exception = assertThrows(CreacionUsuarioException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(APELLIDO_NULO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega sin documento de identidad debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConDocumentoIdentidadNulo(){
        String documento = null;
        Usuario usuario = UsuarioFactory
                .crearUsuarioConDocumentoIdentidad(documento);

        Exception exception = assertThrows(CreacionUsuarioException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(DOCUMENTO_NULO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega sin celular debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConCelularNulo(){
        String celular = null;
        Usuario usuario = UsuarioFactory.crearUsuarioConCelular(celular);

        Exception exception = assertThrows(CreacionUsuarioException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(CELULAR_NULO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega sin fecha de nacimiento debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConFechaNacimientoNulo(){
        LocalDate fechaNacimiento = null;
        Usuario usuario = UsuarioFactory
                .crearUsuarioConFechaNacimiento(fechaNacimiento);

        Exception exception = assertThrows(CreacionUsuarioException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(FECHA_NACIMIENTO_NULO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega sin correo debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConCorreoNulo(){
        String correo = null;
        Usuario usuario = UsuarioFactory.crearUsuarioConCorreo(correo);

        Exception exception = assertThrows(CreacionUsuarioException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(CORREO_NULO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega sin clave debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConClaveNulo(){
        String clave = null;
        Usuario usuario = UsuarioFactory.crearUsuarioConClave(clave);

        Exception exception = assertThrows(CreacionUsuarioException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(CLAVE_NULO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega con correo ya existente debe lanzar excepción")
    @Test
    void testCrearAuxBodegaUsuarioConCorreoYaExiste(){
        String correo = "correoExistente@correo.com";
        Usuario usuario = UsuarioFactory.crearUsuarioConCorreo(correo);

        when(usuarioPersistenciaPort.obtenerUsuarioPorCorreo(correo))
                .thenReturn(Optional.of(usuario));

        Exception exception = assertThrows(CreacionUsuarioException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(CORREO_EN_USO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("obtener usuario por correo cuando no existe debe lanzar excepción")
    @Test
    void testObtenerUsuarioPorCorreoUsuarioNoExiste(){
        String correo = "correoNoExistente@correo.com";

        when(usuarioPersistenciaPort.obtenerUsuarioPorCorreo(correo))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(UsuarioNoEncontradoException.class,
                () -> usuarioCasoUso.obtenerUsuarioPorCorreo(correo));

        assertEquals(USUARIO_NO_ENCONTRADO, exception.getMessage());

        verify(usuarioPersistenciaPort).obtenerUsuarioPorCorreo(correo);
    }

    @DisplayName("obtener usuario por correo con éxito debe retornar usuario")
    @Test
    void testObtenerUsuarioPorCorreoConExito(){
        String correo = "ejemplo@correo.com";
        Usuario usuario = UsuarioFactory.crearUsuarioConCorreo(correo);

        when(usuarioPersistenciaPort.obtenerUsuarioPorCorreo(correo))
                .thenReturn(Optional.of(usuario));

        Usuario usuarioEncontrado = usuarioCasoUso.obtenerUsuarioPorCorreo(correo);

        assertEquals(usuario, usuarioEncontrado);

        verify(usuarioPersistenciaPort).obtenerUsuarioPorCorreo(correo);
    }

    @DisplayName("crear usuario con rol cliente con exito")
    @Test
    void testCrearClienteExito(){

        Usuario usuarioPorDefecto = UsuarioFactory.crearUsuarioPorDefecto();
        Usuario usuarioCreado = UsuarioFactory.crearUsuarioConRol(new Rol(1L, ROL_CLIENTE, DESCRIPCION_ROL_CLIENTE));

        when(usuarioPersistenciaPort.obtenerUsuarioPorCorreo(usuarioPorDefecto.getCorreo()))
                .thenReturn(Optional.empty());

        when(usuarioPersistenciaPort.crearUsuario(any(Usuario.class))).thenReturn(usuarioCreado);

        Usuario usuarioRespuesta = usuarioCasoUso.crearCliente(usuarioPorDefecto);

        assertEquals(ROL_CLIENTE, usuarioRespuesta.getRol().getNombre());

        verify(usuarioPersistenciaPort).obtenerUsuarioPorCorreo(usuarioPorDefecto.getCorreo());
        verify(usuarioPersistenciaPort).crearUsuario(any(Usuario.class));
    }
}