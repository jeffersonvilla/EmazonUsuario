package com.jeffersonvilla.emazon.usuario.dominio.api.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CelularInvalidoException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CorreoInvalidoException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CreacionAuxiliarBodegaRolNoEsCorrectoException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CreacionUsuarioException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.DocumentoInvalidoException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.UsuarioMenorEdadException;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IEncriptadorClavePort;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IRolPersistenciaPort;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IUsuarioPersistenciaPort;
import com.jeffersonvilla.emazon.usuario.dominio.util.UsuarioAuxBodegaFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.ROL_AUX_BODEGA;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.APELLIDO_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.AUX_BODEGA_ROL_INCORRECTO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CELULAR_INVALIDO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CELULAR_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CLAVE_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CORREO_INVALIDO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CORREO_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.DOCUMENTO_INVALIDO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.DOCUMENTO_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.FECHA_NACIMIENTO_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.NOMBRE_NULO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.USUARIO_MENOR_EDAD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioCasoUsoTest {

    @InjectMocks
    private UsuarioCasoUso usuarioCasoUso;

    @Mock
    private IUsuarioPersistenciaPort usuarioPersistenciaPort;

    @Mock
    private IRolPersistenciaPort rolPersistenciaPort;

    @Mock
    private IEncriptadorClavePort encriptadorClave;

    @DisplayName("Usuario con correo invalido debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConCorreoInvalido(){
        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConCorreo("correoinvalido");

        Exception exception = assertThrows(CorreoInvalidoException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(CORREO_INVALIDO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("Usuario con celular invalido debe lanzar excepción")
    @Test
    void testCrearAuxBodegaCelularInvalido(){
        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConCelular("12345678901234abc");

        Exception exception = assertThrows(CelularInvalidoException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(CELULAR_INVALIDO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("Usuario con documento de identidad invalido debe lanzar excepción")
    @Test
    void testCrearAuxBodegaDocumentoIdentidadInvalido(){
        Usuario usuario = UsuarioAuxBodegaFactory
                .crearUsuarioConDocumentoIdentidad("documentoNoValido");

        Exception exception = assertThrows(DocumentoInvalidoException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(DOCUMENTO_INVALIDO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("Usuario con menor de edad debe lanzar excepción")
    @Test
    void testCrearAuxBodegaUsuarioMenorDeEdad(){
        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConFechaNacimiento(
                LocalDate.of(2023, 9, 5));

        Exception exception = assertThrows(UsuarioMenorEdadException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(USUARIO_MENOR_EDAD, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("test crearAuxBodega con usuario con rol diferente a " + ROL_AUX_BODEGA
            +" debe lanzar excepción")
    @Test
    void testCrearAuxBodegaUsuarioConRolIncorrecto(){
        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConRol(
                new Rol(1L, "rolIncorrecto", "descripción del rol"));

        Exception exception = assertThrows(
                CreacionAuxiliarBodegaRolNoEsCorrectoException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(AUX_BODEGA_ROL_INCORRECTO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega con éxito")
    @Test
    void testCrearAuxBodegaClaveDebeEstarCifrada(){
        String claveCifrada = "claveCifrada";
        Usuario usuarioParaGuardar = UsuarioAuxBodegaFactory.crearUsuarioPorDefecto();
        Usuario usuarioGuardado = UsuarioAuxBodegaFactory.crearUsuarioConClave(claveCifrada);

        when(encriptadorClave.encriptarClave(anyString())).thenReturn(claveCifrada);
        when(usuarioPersistenciaPort.crearUsuario(any(Usuario.class))).
                thenReturn(usuarioGuardado);

        Usuario usuarioResultante = usuarioCasoUso.crearAuxBodega(usuarioParaGuardar);

        assertEquals(usuarioGuardado, usuarioResultante);

        verify(encriptadorClave).encriptarClave(usuarioParaGuardar.getClave());
        verify(usuarioPersistenciaPort).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega sin nombre debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConNombreNulo(){
        String nombre = null;
        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConNombre(nombre);

        Exception exception = assertThrows(CreacionUsuarioException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(NOMBRE_NULO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega sin apellido debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConApellidoNulo(){
        String apellido = null;
        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConApellido(apellido);

        Exception exception = assertThrows(CreacionUsuarioException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(APELLIDO_NULO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega sin documento de identidad debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConDocumentoIdentidadNulo(){
        String documento = null;
        Usuario usuario = UsuarioAuxBodegaFactory
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
        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConCelular(celular);

        Exception exception = assertThrows(CreacionUsuarioException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(CELULAR_NULO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega sin fecha de nacimiento debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConFechaNacimientoNulo(){
        LocalDate fechaNacimiento = null;
        Usuario usuario = UsuarioAuxBodegaFactory
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
        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConCorreo(correo);

        Exception exception = assertThrows(CreacionUsuarioException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(CORREO_NULO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }

    @DisplayName("creación auxiliar de bodega sin clave debe lanzar excepción")
    @Test
    void testCrearAuxBodegaConClaveNulo(){
        String clave = null;
        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConClave(clave);

        Exception exception = assertThrows(CreacionUsuarioException.class,
                () -> usuarioCasoUso.crearAuxBodega(usuario));

        assertEquals(CLAVE_NULO, exception.getMessage());

        verify(usuarioPersistenciaPort, never()).crearUsuario(any(Usuario.class));
    }
}