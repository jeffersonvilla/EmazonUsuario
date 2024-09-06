package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.dominio.util.UsuarioAuxBodegaFactory;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private String tokenGenerado;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = UsuarioAuxBodegaFactory.crearUsuarioPorDefecto();
        tokenGenerado = jwtService.generarToken(usuario);
    }

    @Test
    void testExtraerUsername() {
        String username = jwtService.extraerUsername(tokenGenerado);
        assertEquals(usuario.getCorreo(), username);
    }

    @Test
    void testExtraerIdUsuario() {
        Long idUsuario = jwtService.extraerIdUsuario(tokenGenerado);
        assertEquals(1L, idUsuario);
    }

    @Test
    void testTokenValido() {
        when(userDetails.getUsername()).thenReturn(usuario.getCorreo());
        assertTrue(jwtService.tokenValido(tokenGenerado, userDetails));
    }

    @Test
    void testTokenInvalidoPorCorreo() {
        when(userDetails.getUsername()).thenReturn("otro@test.com");
        assertFalse(jwtService.tokenValido(tokenGenerado, userDetails));
    }

    @Test
    void testTokenInvalidoPorExpiracion() {

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJpZF91c3VhcmlvIjoxLCJyb2wiOiJhdXhfYm9kZWd" +
                "hIiwic3ViIjoidXN1YXJpb0BlamVtcGxvLmNvbSIsImlhdCI6MTcyNTU0ODY1MCwiZXhwIj" +
                "oxNzE1NjI1MDUwfQ.d57RIrJv6gjXj310gzK5FXrA7UQNj8X4h1sYApTjgkECEG-dJJM9Nl" +
                "rQ4xW8Ntrhu4GvpbSkmV_JxDtiBXJ5Cg";

        assertThrows(ExpiredJwtException.class,
                () -> jwtService.tokenValido(token, userDetails));
    }

    @Test
    void testExtraerExpiration() {
        Date expiration = jwtService.extractExpiration(tokenGenerado);
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }
}
