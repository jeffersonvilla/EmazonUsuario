package com.jeffersonvilla.emazon.usuario.dominio.api.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.api.IUsuarioServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CredencialesLoginInvalidasException;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IEncriptadorClavePort;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IGeneradorTokenJwtPort;
import com.jeffersonvilla.emazon.usuario.dominio.util.UsuarioAuxBodegaFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CREDENCIALES_INVALIDAS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutenticacionCasoUsoTest {

    @Mock
    private IUsuarioServicePort usuarioApi;

    @Mock
    private IEncriptadorClavePort encriptadorClave;

    @Mock
    private IGeneradorTokenJwtPort generadorTokenJwt;

    @InjectMocks
    private AutenticacionCasoUso autenticacionService;

    private final String correo = "test@ejemplo.com";
    private final String clave = "clave";
    private final String claveCifrada = "claveCifrada";

    @DisplayName("autenticar con credenciales correctas retorna token jwt")
    @Test
    void autenticarExito() {

        String tokenJwt = "token";

        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConClave(claveCifrada);

        when(usuarioApi.obtenerUsuarioPorCorreo(correo)).thenReturn(usuario);
        when(encriptadorClave.claveCorrecta(clave, claveCifrada)).thenReturn(true);

        when(generadorTokenJwt.generarTokenJwt(usuario)).thenReturn(tokenJwt);

        String respuesta = autenticacionService.autenticarUsuario(correo, clave);

        assertEquals(tokenJwt, respuesta);
        verify(generadorTokenJwt).generarTokenJwt(usuario);
    }

    @DisplayName("autenticar con clave incorrecta debe lanzar excepción")
    @Test
    void autenticarClaveIncorrecta() {

        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConClave(claveCifrada);

        when(usuarioApi.obtenerUsuarioPorCorreo(correo)).thenReturn(usuario);
        when(encriptadorClave.claveCorrecta(clave, claveCifrada)).thenReturn(false);


        Exception exception = assertThrows(CredencialesLoginInvalidasException.class,
                () -> autenticacionService.autenticarUsuario(correo, clave));

        assertEquals(CREDENCIALES_INVALIDAS, exception.getMessage());

        verify(generadorTokenJwt, never()).generarTokenJwt(any(Usuario.class));
    }
}