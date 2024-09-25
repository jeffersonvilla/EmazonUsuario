package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.dominio.util.UsuarioFactory;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private final String claveSecreta = "a522a5d03de1e7364f4985067bba67f4a0d6e4e1ed482d07bb70b" +
            "94a39f1580a8ce82094888ff449bc23a9bfc6715dbdcd82347c94127536825f468d3e4a2186" +
            "c20ca826705d642bcf486e0cbebf1a7df0b68d37772c8ddce876d500ee895492289380ed404" +
            "9ccfc0a7f8dfe700ddb52e56b6c9d658e32a1e2134fed35870dc83ba4b47c3336153e779bab" +
            "f72cab925002fec514c31e14fa09d3471a8bb178fd06baef291ad65f7058a09b4499ba30ae9" +
            "23002d6c9b469b440970486eac0b0523ade7421e465fce4012bca6bd2b5580c52f3212e0ec6" +
            "4bc0f5c5afca71bf1e8f6040fd83c7fdbec33e615d29c6a52d40e3615cf3aa55c014b3a1c15" +
            "50f26008c";

    private String tokenGenerado;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "claveSecreta", this.claveSecreta);
        usuario = UsuarioFactory.crearUsuarioPorDefecto();
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
