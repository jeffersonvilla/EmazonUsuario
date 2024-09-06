package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.api.IUsuarioServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IEncriptadorClavePort;
import com.jeffersonvilla.emazon.usuario.dominio.util.UsuarioAuxBodegaFactory;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.dto.LoginRequestDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.dto.LoginResponseDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.excepciones.CredencialesLoginInvalidasException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CREDENCIALES_INVALIDAS;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutenticacionServiceTest {

    @Mock
    private IUsuarioServicePort usuarioApi;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private IEncriptadorClavePort encriptadorClave;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AutenticacionService autenticacionService;

    private final String correo = "test@ejemplo.com";
    private final String clave = "clave";
    private final String claveCifrada = "claveCifrada";

    @DisplayName("autenticar con credenciales correctas retorna token jwt")
    @Test
    void autenticarExito() {

        String tokenJwt = "token";
        LoginRequestDto request = new LoginRequestDto(correo, clave);

        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConClave(claveCifrada);

        when(usuarioApi.obtenerUsuarioPorCorreo(correo)).thenReturn(usuario);
        when(encriptadorClave.claveCorrecta(clave, claveCifrada)).thenReturn(true);

        when(jwtService.generarToken(usuario)).thenReturn(tokenJwt);

        LoginResponseDto respuesta = autenticacionService.autenticar(request);

        assertEquals(tokenJwt, respuesta.tokenJwt());
        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(correo, clave));
        verify(jwtService).generarToken(usuario);
    }

    @DisplayName("autenticar con clave incorrecta debe lanzar excepción")
    @Test
    void autenticarClaveIncorrecta() {

        LoginRequestDto request = new LoginRequestDto(correo, clave);


        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConClave(claveCifrada);

        when(usuarioApi.obtenerUsuarioPorCorreo(correo)).thenReturn(usuario);
        when(encriptadorClave.claveCorrecta(clave, claveCifrada)).thenReturn(false);


        Exception exception = assertThrows(CredencialesLoginInvalidasException.class,
                () -> autenticacionService.autenticar(request));

        assertEquals(CREDENCIALES_INVALIDAS, exception.getMessage());

        verify(authenticationManager, never())
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generarToken(any(Usuario.class));
    }

    @DisplayName("autenticar con error en AuthenticationManager debe lanzar excepción")
    @Test
    void autenticarErrorAutenticacionManager() {

        LoginRequestDto request = new LoginRequestDto(correo, clave);

        Usuario usuario = UsuarioAuxBodegaFactory.crearUsuarioConClave(claveCifrada);

        when(usuarioApi.obtenerUsuarioPorCorreo(correo)).thenReturn(usuario);
        when(encriptadorClave.claveCorrecta(clave, claveCifrada)).thenReturn(true);
        doThrow(RuntimeException.class).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));


        assertThrows(RuntimeException.class,
                () -> autenticacionService.autenticar(request));

        verify(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generarToken(any(Usuario.class));
    }
}